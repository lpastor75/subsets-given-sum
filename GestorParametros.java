import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase encargada de gestionar los argumentos recibidos a través
 * de la línea de comandos.
 * 
 * @author Luis Pastor Nuevo 
 * @version Noviembre 2018
 */
public class GestorParametros
{
    private static final String PATRON_CARACTERES_NO_VALIDOS = "[-\\?:\\*<>|]?";
    private boolean ayuda;
    private boolean traza;
    private String ficheroEntrada;
    private ArrayList<String> datosEntrada;
    private String ficheroSalida;
    private ArrayList<Integer>datosVector;
    int m;
    int c;
    private StringBuilder sb;

    public GestorParametros() {
        datosEntrada = new ArrayList<String>();
        datosVector = new ArrayList<Integer>();
    }

    /**
     * Atributo booleano para mostrar la ayuda de la aplicación.
     * @return True si se desea mostrar ayuda. Si no, false.
     */
    public boolean getAyuda() {
        return ayuda;
    }

    /**
     * Atributo booleano para mostrar la traza del proceso en ejecución.
     * @return  True si se desea mostrar la traza. Si no, false.
     */
    public boolean conTraza() {
        return traza;
    }
    
    /**
     * Ruta al fichero con los datos de entrada.
     * @return  Ruta relativa al fichero de entrada.
     */
    public String getEntrada() {
        return ficheroEntrada;
    }

    /**
     * Ruta al fichero con los datos de salida.
     * @return  Ruta relativa al fichero de salida.
     */
    public String getSalida() {
        return ficheroSalida;
    }

    public boolean hayErrores() {
        return sb != null;
    }

    public String getErrores() {
        return sb.toString();
    }

    private void addError(String err) {
        if(sb == null){
            sb = new StringBuilder();
        }
            sb.append(err);        
    }

    /**
     * Interpreta los argumentos recibidos y construye el objeto correspondiente.
     * @param args  Argumentos de la línea de comandos.
     * @return      Objeto con los argumentos interpretados.
     */
    public static GestorParametros leer(String[] args) {
        GestorParametros gp = new GestorParametros();

        if(args.length > 3) {         
            gp.ayuda = true;
           }
        else
        {
            for (String arg : args) {
                switch (arg) {
                    case Suma.TRAZA:
                        gp.traza = true;
                        break;
                        
                    case Suma.AYUDA:
                        gp.ayuda = true;
                        return gp;
                        
                    default:
                           
                        if(!arg.matches(".*["+PATRON_CARACTERES_NO_VALIDOS+"].*")) {
                            
                                if (gp.ficheroEntrada==null) {
                                    gp.ficheroEntrada = arg;
                                   } 
                                else {
                                  gp.ficheroSalida = arg;  
                                   }
                        } else {
                               gp.addError("El nombre del fichero contiene caracteres "
                                           +"no válidos.\n");
                            }                            
                }
            }
        }
        return gp;
    }
    
    /**
     * Recibe el argumento de fichero de entrada y extrae los datos correspondientes.
     * @param args  String con la ruta del fichero de entrada.
     */
    public void leerEntrada (String input) {
        
        FileReader auxEntrada = null;
        int contador = 0;
        int numLineas=3;         
        
        /* Se comprueba la existencia de ficheroEntrada */    
        File fichero = new File(input);
        
        if (!fichero.exists()||fichero.isDirectory()) {
            System.out.println("\n**ERROR 1**: Fichero de entrada incorrecto. "+
                 "No existe o es un directorio. Se introducirán los datos manualmente.\n");           
            iniciarEntradaConsola();   
            return;
            }
       
        /* Se leen los datos de ficheroEntrada */        
        try {
               auxEntrada = new FileReader(input);
               //Buffer de lectura.
               BufferedReader reader = new BufferedReader(auxEntrada); 
               // Lee línea a línea auxEntrada.
               String linea = reader.readLine(); 
               //Se lee hasta el final del fichero.
               while (linea != null) {
                    datosEntrada.add(linea); 
                    linea = reader.readLine(); 
                 }
               //Se cierra el buffer de lectura. 
               reader.close(); 
                
             } catch (Exception error) {
               System.out.println("\n**ERROR 2**: Fichero de entrada incorrecto. "+
                                  "Se introducirán los datos manualmente.\n");
               iniciarEntradaConsola();
               return;
               
            } finally {
                try {                 
                    if (auxEntrada != null) {
                        auxEntrada.close();
                     }
                    
                    } catch (IOException error) {
                        System.out.println("\n**ERROR 3**: Fichero de entrada incorrecto."+
                                           "Se introducirán los datos manualmente.\n");
                        iniciarEntradaConsola(); 
                        return;
                     }
              }
              
        validarDatosEntrada(); 
    }
    
    /**
     * Se introducen los datos de forma manual.
     */
    public  void iniciarEntradaConsola() {
         // Se instancia un objeto de la clase Scanner.
         Scanner sc = new Scanner(System.in);
         datosVector.clear();
         int numElem=0;         
        
            do{
                System.out.println("Introduzca el número de elementos del vector: "); 
                
                try {
                    numElem = Integer.parseInt(sc.nextLine()); 
                } catch (NumberFormatException e) {   
                    System.out.println("\nFormato incorrecto");                  
                  }
                
                if (numElem<2){                    
                    System.out.println("\nParámetro no válido. Vuelva a repetir la operación.");
                    System.out.println("Introduzca un número positivo mayor o igual que 2.\n");                                      
                  }               
            } while (numElem<2);

            do{                
                System.out.println("\nIntroduzca numero: "); 
                
                try {                    
                    int elem = Integer.parseInt(sc.nextLine());
                    
                    if (!datosVector.contains(elem)) {
                    datosVector.add(elem);
                    } 
                    else {
                        System.out.println("No se permiten números repetidos");
                    }
                } catch (NumberFormatException e) {   
                    System.out.println("Formato incorrecto, repita la operación.");
                  }                                    
            } while (datosVector.size()<numElem);
            
         System.out.println("\nVector de enteros: " +datosVector);
     
            do{
                System.out.println("\nIntroduzca numero de sumandos: "); 
                
                try {
                    m = Integer.parseInt(sc.nextLine()); 
                } catch (NumberFormatException e) {   
                    System.out.println("Formato incorrecto, vuelva a intentarlo");
                  } 
                
                if (m<2 || m>datosVector.size()){
                   
                    System.out.println("\nEl número de sumandos debe ser mayor o igual a 2"+ 
                                       " y menor o igual que el tamaño del vector");
                  }               
            } while (m< 2 || m>datosVector.size());
  
            do {
                c = Integer.MAX_VALUE +1;
                System.out.println("\nQué suma desea encontrar?: ");
                
                try {
                    c = Integer.parseInt(sc.nextLine());                     
                    System.out.print("\n");
                } catch (NumberFormatException e) {   
                    System.out.println("Formato incorrecto, vuelva a intentarlo");
                  }                               
            } while (c > Integer.MAX_VALUE || c < Integer.MIN_VALUE);                          
    }
    
    /**
     * Validación de los datos de entrada extraidos de los argumentos de la ejecución del programa.
     * Se comprueba que los datos cumplen con las pautas marcadas en el enunciado.
     */
    private void validarDatosEntrada() {
        
        if (datosEntrada.size()==3) {
        
            try {                     
                String[] arrayLinea1 = datosEntrada.get(0).split(" ");
                                         
                m = Integer.parseInt(datosEntrada.get(1));
                
              if (m<1 || m>arrayLinea1.length) {
                    
                    throw new ExcepcionPropia("\n####  WARNING  ####\n\nParametro m incorrecto.\n"
                                              +"m tiene que ser mayor que 0 y menor o igual que n.\n"
                                              +"Se introducirán los datos manualmente.\n");                    
              } else {
                                                   
                c= Integer.parseInt(datosEntrada.get(2));
                
                for (int i = 0; i<arrayLinea1.length; i++){                
                    
                    int aux = Integer.parseInt(arrayLinea1[i]);                    
                     
                    if (!datosVector.contains(aux)) {
                    datosVector.add(aux);
                    } 
                    else {
                        throw new ExcepcionPropia("\n#### WARNING ####\nEl número "+aux+ " aparece duplicado en el vector.\n"
                                           + "No se permiten números repetidos.\n"
                                           + "Se introducirán los números manualmente.\n\n");
                                        }
                }
              }
 
            } catch (NumberFormatException error){ //Control de integridad de los datos
                System.out.println("\nERROR: Fichero de entrada incorrecto. "+
                                      "Se introducirán los datos manualmente.");
                iniciarEntradaConsola();
            } catch (ExcepcionPropia e) {
                
                System.out.println(e.getMessage());
                iniciarEntradaConsola();                
            }
                                
        } else {        
            System.out.println("\nERROR: Fichero de entrada incorrecto. "+
                                        "Se introducirán los datos manualmente.");
            iniciarEntradaConsola();
                                    }        
    }
    //getters
    public ArrayList<Integer> getDatosVector() {        
        
        return datosVector;        
    }
    
    public int getM() {
        
        return m;
    }
    
    public int getC() {
        
        return c;
    }  
    /**
     * Clase privada para crear una excepción personalizada.
     */
    private class ExcepcionPropia extends Exception {
        
       public ExcepcionPropia(String mensaje) {
           super(mensaje);
       }
    }
}
