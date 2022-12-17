import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

/**
 * Clase que contiene el método main que inicializa el programa.
 * 
 * @author Luis Pastor Nuevo 
 * @version Noviembre 2018
 */
public class Suma
{
    public static final String TRAZA = "-t";
    public static final String AYUDA = "-h";
    private static final String INFO_AYUDA =
            "\n\n#####   AYUDA   #####\n\n" +
            "SINTAXIS:\n" +
            "suma [-t][-h][fichero_entrada] [fichero_salida]\n" +
            " -t\t\tTraza la selección de subconjuntos\n" +
            " -h\t\tMuestra esta ayuda\n" +
            "fichero_entrada\t\tNombre del fichero de entrada\n" +
            "fichero_salida\t\tNombre del fichero de salida\n";
   
    /**
     * Método principal que recibe los parámetros introducidos por consola, los procesa e
     * inicia las operaciones.
     * @param args Parámetros de la línea de comandos.
     */
    public static void main(String[] args) {   
        
        try {
            GestorParametros misParametros = GestorParametros.leer(args);

            if(misParametros.getAyuda() || misParametros.hayErrores()) {
                if(misParametros.hayErrores()) {
                    System.out.println("\n#### ERROR: " + misParametros.getErrores());
                }
                System.out.println(INFO_AYUDA);
                System.exit(0);
            }  
            
            Traza.init(misParametros.conTraza());
            Salida.init(misParametros.getSalida());
                           
            if (misParametros.getEntrada() == null) {
                System.out.println("\nSin parámetro de fichero de entrada. Se introducirán los datos"+
                                   " de forma manual");
                misParametros.iniciarEntradaConsola();
               }
            else {                
                misParametros.leerEntrada(misParametros.getEntrada());
               }
                                  
            for (int num: misParametros.getDatosVector()) {
                
                 Salida.getSalida().escribir(num+" ");
               }
            
            Salida.getSalida().escribir("\n"+misParametros.getM());
            Salida.getSalida().escribir("\n"+misParametros.getC()+"\n\n");
            
            Subconjuntos sub = new Subconjuntos(misParametros.getDatosVector(),misParametros.getM(),
                                               misParametros.getC());   
                       
            procesarResultados(sub);                            
    }
        catch(Exception ex) {
            System.out.println("#### ERROR: ####\n");
            System.out.println(ex.getMessage());
            System.out.println();
            System.out.print(INFO_AYUDA);
            System.exit(-1);
        }
    }  
    
    /**
     * Método encargado de escribir los resultados o de mostrarlos por pantalla.
     */
    private static void procesarResultados(Subconjuntos sub) throws IOException {
                
        sub.calculaSubconjuntos(0,0,0);
        
        if(Traza.noVacia()) {
           Salida.getSalida().escribir(Traza.leer());
           Salida.getSalida().escribir("-------------------------------\n");
        }
                
        if(!sub.getSolucion()) {
           Salida.getSalida().escribir("\nNo existen soluciones para este vector\n\n"); 
        }
         else {
           Salida.getSalida().escribir("\nNumero de soluciones: "+sub.getNumSoluciones() +"\n\n");
        }  
    }  
}