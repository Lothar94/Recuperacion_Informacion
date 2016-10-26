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
        TextIndexer indexer = new TextIndexer("./palabras_vacias.txt"); 
        FileIO textReader = new FileIO();
        TextParser parser = new TextParser(); 
        HashMap<String, Integer> resultado = new HashMap();
        
        //Leemos los documentos.
        resultado = indexer.indexText("index.html", resultado);
        resultado.forEach((k, v) -> System.out.println("Key: " + k + ": Value: " + v));
        
        //Pasamos los resultados a un fichero de texto
        indexer.generarResultados(resultado);
        
        // Escribimos datos. 
        textReader.writeFileTable(indexer.filePaths);
        
        // Escribimos los links. 
        parser.writeLinks(indexer.filePaths);
        
    }
    
    
    
}
