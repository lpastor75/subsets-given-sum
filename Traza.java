import java.util.ArrayList;

/**
 * Clase responsable de crear la traza que se mostrará por pantalla
 * 
 * @author Luis Pastor Nuevo 
 * @version Noviembre 2018
 */
public class Traza {
    
    private static Traza t;
    private StringBuilder sb;
    private static final int LIMITE_BUFFER = 0x000FFFFF;

    /**
     * Construtor de la clase Traza.
     */
    public Traza() {
        sb = new StringBuilder();
    }
    
    /**
     * Método que inicializa una instancia de traza.
     * @param Parámetro booleano que indica si existirá traza en esta ejecución.
     */
    public static void init (boolean requerida) {
        
        if(t == null && requerida) {
            t = new Traza();
            t.write("\r\n ------- TRAZA DE LA EJECUCIÓN -------\r\n");
        }
    }
        
    /**
     * Método que informa si la traza está vacía o si contiene datos.
     * @return  True, si existieran datos.
     */
    public static boolean noVacia() {        
        return t != null;
    }
    
    /**
     * Método para escribir una nueva línea en la traza.
     */
    public static void escribirLinea(String texto) {
        
        if(t != null) {
            t.write(texto);           
        } 
    }
    
    /**
     * Método que escribe el estado actual del vector de booleanos.
     * @param vector de booleanos.
     */
    public static void escribirVector(ArrayList<Boolean>v) {
        
        if(t != null) { 
            
             if(v != null && v.size() > 0) {         
                t.write("[");
                for(int i = 0; i < v.size(); i++)
                {                 
                    t.write(String.valueOf(v.get(i)));
                    t.write(i == v.size() - 1? "]" : ",");
                }
                t.write("\r\n");
             }                        
        }        
    }
    
    /**
     * Método que escribe en la traza los elementos seleccionados del vector de enteros
     * @param vector de booleanos.
     * @param vector de enteros.
     * @param nivel k.
     * @param número de sumandos. 
     */
    public static void escribirSeleccion(ArrayList<Integer>datos,ArrayList<Boolean>v,int k, int m) {
        
        if(t != null) { 
            
            int contador=0;
            
             if(v != null && v.size() > 0) {   
                 
                t.write("[");
                
                for(int i = 0; i < v.size(); i++) {   
                    
                    if(v.get(i) && i<=k && contador<m){
                        
                        t.write(String.valueOf(datos.get(i)));
                        contador++;
                    } else {
                        
                        t.write("_");
                    }
                    t.write(i == v.size() - 1? "]" : ",");                    
                 }
                t.write("\r\n");
             }                        
        }        
    }
    
    /**
     * Se escribe en la traza el texto.
     * @param  String con el texto a escribir.
     */
    private void write(String texto) {
        
        if(sb.length() > LIMITE_BUFFER){
            
            System.out.print(sb.toString());
            sb.delete(0, sb.length());
         }
        sb.append(texto);       
    }

    /**
     * Obtiene el contenido registrado en la traza.
     * @return  Cadena de texto con lo escrito en la traza.
     */
    public static String leer() {
        
        if(t == null) {
            
            return "";
        } 
            else  {
                
                return t.sb.toString();
            }
    }
}
