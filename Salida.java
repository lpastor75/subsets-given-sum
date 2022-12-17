import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Clase que tiene la responsabilidad de imprimir por pantalla o a fichero, la información
 * que se le indique.
 * @author Luis Pastor Nuevo
 * @version  Noviembre 2018
 */
public class Salida {
    
    private static final String ERROR_FICHERO_SALIDA =
            "No se ha podido crear el fichero de salida.";
    private static final String ERROR_FICHERO_ESCRITURA =
            "No se puede escribiren el fichero. Verifique que está " +
                    "permitida la escritura\n";
    private static final String ERROR_FICHERO_DUPLICADO =
            "Ya existe un fichero con el mismo nombre " +
                    "en esa ruta\n";                
    private static Salida salida; 
    private String fichero;

    /**
     * La instancia que se cree de la clase, se hará desde el método estático init()
     * @param fichero   Fichero en el que se guardará la información, 
     *                  o null si la salida es por pantalla.
     *      
     */
    private Salida(String fichero) {
        this.fichero = fichero;
    }

    /**
     * Inicialización de la salida.
     * @param fichero   Fichero en el que se guardará la información o null si es por pantalla.
     * @param grafica   si la salida va a ser con esquemas gráficos, false si son coordenadas.
     * @throws IOException  Si no puede crear/borrar el fichero o no puede escribir en él.
     */
    public static void init(String fichero) throws IOException {
        
        if(fichero != null) {         
            boolean creado = true;
            File file = new File(fichero);
            
            if(file.exists() && !file.isDirectory()) {               
                throw new IOException(ERROR_FICHERO_DUPLICADO);                
            }
            
            if(!file.exists()) {
                creado = file.createNewFile();
            }

            if(!creado) {
                throw new IOException(ERROR_FICHERO_SALIDA);
            }
            
            if(!file.canWrite()) {
                throw new IOException(ERROR_FICHERO_ESCRITURA);
            }
        }

        if(salida == null) {            
           salida = new Salida(fichero);          
        }
    }

    /**
     * Escribe el texto de salida en un fichero si este ha sido creado correctamente.
     * De lo contrario, muestra el texto por pantalla
     * @param texto Texto que se quiere escribir en el fichero.
     * @throws IOException  Si no se puede escribir en el fichero.
     */
    public void escribir(String texto) throws IOException {
        
        if(fichero == null) {
            System.out.print(texto);
        }
        else {
            Files.write(Paths.get(fichero), texto.getBytes(), StandardOpenOption.APPEND);
        }
    }
            
    public static Salida getSalida() {
        return salida;
    }

}
