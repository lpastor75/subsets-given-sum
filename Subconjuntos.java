import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase encargada de implementar el algoritmo de vuelta atrás que claculará los
 * subconjuntos de suma dada.
 * 
 * @author Luis Pastor Nuevo 
 * @version Noviembre 2018
 */
public class Subconjuntos{
       
    private ArrayList<Integer> datos; //vector de numeros en el que se buscarán subconjuntos
    private ArrayList<Boolean> v; //vector de booleanos para que indica los numeros seleccionados
    int m; //sumandos requeridos
    int c; //suma a buscar    
    boolean solucion = false; 
    int numSoluciones;
    private StringBuilder sb;
    
    public Subconjuntos(ArrayList<Integer> datos, int m, int c) {  
      
        this.datos = datos;
        this.m = m;
        this.c = c;
        sb = new StringBuilder();
        v = new ArrayList<Boolean>();
        //se inicializa el vector de booleanos
        for (int i=0; i<datos.size(); i++) {
              v.add(true);
          } 
    }
    
    /**
     * Método que implementa el algoritmo de vuelta atrás para resolver el ejercicio
     * 
     * @param  k entero que representa el nivel. 
     * @param  sumandos número de sumandos necesarios en cada solución.
     * @param  suma parcial que se va alcanzando.
     */
    public void calculaSubconjuntos(int k, int sumandos, int sum) {
                                                                                                             
        if (sumandos == m) {
           
            if (sum == c) {   
                
                 solucion = true;
                 
                        Traza.escribirLinea("\n##### Solución encontrada ##### : ");
                        Traza.escribirSeleccion(datos, v, k, m);                       
                        
                 imprimirSolucion(sumandos, datos, v);
                 
                        if (k >= datos.size())
                        Traza.escribirLinea("\nFin de la rama, se vuelve hacia atrás\n");
                 
            } else { 
                
                        Traza.escribirLinea("\nSubconjunto no válido. Se descarta\n");
                        if (k >= datos.size())
                        Traza.escribirLinea("\nFin de la rama, se vuelve hacia atrás\n");
                }
                
        } else {
             
             if (k<datos.size()) {
                 
                   v.set(k,false);
                   
                        Traza.escribirLinea("\nSe pone a false: "+datos.get(k));
                        Traza.escribirLinea("\nVector v: ");
                        Traza.escribirVector(v);
                  
                   calculaSubconjuntos(k+1, sumandos, sum);
                   
                   v.set(k,true); 
                   sum+=datos.get(k);
                   sumandos++;
                   
                        Traza.escribirLinea("\nNúmero seleccionado: "+datos.get(k));
                        Traza.escribirLinea("\nSelección actual: ");
                        Traza.escribirSeleccion(datos, v, k, m);                                 
                        Traza.escribirLinea("Sumandos: "+sumandos+"\n");
                        Traza.escribirLinea("Suma parcial: "+sumaParcial(datos,v, k)+"\n"); 
                       
                   calculaSubconjuntos(k+1, sumandos, sum);                 
              
            } else {            
                        Traza.escribirLinea("\nFin de la rama, se vuelve hacia atrás\n");
            }
        }       
    }
    
    /**
     * Imprime el resultado de la ejecución del algoritmo.
     */
    public void imprimirSolucion(int sumandos,ArrayList<Integer> datos, ArrayList<Boolean> v) {
                                        
        Salida salida = Salida.getSalida();
        StringBuilder sb = new StringBuilder();
        int contador=0;
                 
        sb.append("Solucion "+ ++numSoluciones + ": ");
        sb.append("[");
                 
        for (int i=0; i<datos.size(); i++) {
                     
             if ((v.get(i)) && contador < sumandos) {                            
                 sb.append(datos.get(i));        
                 contador++;
                 sb.append(contador == sumandos? "]\r\n" : ", ");
             }
        }
        
        try {
            salida.escribir(sb.toString());
        }
        catch (IOException ex) {
            throw new InternalError("Error al escribir el resultado en el fichero. " + ex.getMessage());
        }
    }
    
    /**
     * Calcula la suma parcial en cada vuelta.
     */
    private int sumaParcial(ArrayList<Integer>datos, ArrayList<Boolean>v, int k) {
        
        int sumaParcial = 0;
        
        for (int i=0; i<datos.size();i++) {
            
            if(v.get(i) && (i<=k)) {
                
                sumaParcial+=datos.get(i);                
            }            
        }
        return sumaParcial;
    } 
    //getters        
    public boolean getSolucion() {        
        return solucion;        
    }
    
    public int getNumSoluciones() {       
        return numSoluciones;
    }   
    
    public ArrayList<Integer> getDatos() {        
        return datos;
    }
    
    public ArrayList<Boolean> getV() {        
        return v;
    }
    
    public int getM() {        
        return m;
    }
    
    public int getC() {       
        return c;
    }    
}