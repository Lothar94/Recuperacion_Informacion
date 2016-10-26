package textindexer;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Lothar Soto
 * @author Iván Calle
 * @author Daniel López
 * @author José Carlos Entrena
 */
public class Practica {
    
    // Main. 
    public static void main(String[] args) throws IOException, Exception {
        
        String text = new String();
        TextIndexer t = new TextIndexer("./palabras_vacias.txt"); 
        TextReader tr = new TextReader();
        HashMap<String, Integer> resultado = new HashMap();
        
        //Leemos los documentos.
        resultado = t.indexText("./quijote", resultado);
        resultado.forEach((k, v) -> System.out.println("Key: " + k + ": Value: " + v));
        
        //Pasamos los resultados a un fichero de texto
        t.generarResultados(resultado);
        
        // Escribimos datos. 
        tr.writeFileTable(t.filePaths);
    }
    
    
    
}
