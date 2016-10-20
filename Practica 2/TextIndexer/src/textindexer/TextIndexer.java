/**
 * Este archivo contiene el tratamiento de los datos leídos,
 * para realizar un proceso de lexificación. 
 */
package textindexer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.*;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.spanishStemmer;

/**
 *
 * @author Lothar Soto
 * @author Iván Calle
 * @author Daniel López
 * @author José Carlos Entrena
 */
public class TextIndexer {

    TextReader rd;
    Hashtable<String, Boolean> emptyWords;
    StringTokenizer tokens;

    public TextIndexer(String emptyWordsPath) throws IOException {
        emptyWords = new Hashtable<>();
        rd = new TextReader();
        emptyWords = rd.readEmptyWords(emptyWordsPath);
    }

    /** 
     * Función para eliminar los signos de puntuación con una expresión regular.
     * Hace falta ajustar el tipo de codificación para que elimine correctamente
     * los acentos y no quite la letra ñ. 
     * Además, pasamos todas las palabras a minúscula para evitar que se separen 
     * palabras por la capitalización. 
     */
    public String removePunctuation(String t) {
        String newText = new String();
        newText = newText.toLowerCase();
        Pattern p = Pattern.compile("[^a-z0-9áéíóúñ]");
        Matcher m = p.matcher(t);
        newText = m.replaceAll(" ");
        return newText;
    }

    
    /**
     * Método para la lectura y lexificación de los datos obtenidos.
     */
 public HashMap<String,Integer> indexText(String filePath,HashMap<String,Integer> numberOfOcurrences) throws IOException{
        System.out.println(filePath);
        String text = new String();
        if(rd.isDirectory(filePath)){
            ArrayList<String> paths;
            paths = rd.readDirectory(filePath);
            for (String file: paths) {
                numberOfOcurrences=indexText(file,numberOfOcurrences);
            }
        }else{
            //Leemos el documento
            text = rd.read(filePath);

            //Eliminamos los signos de puntuación
            text = removePunctuation(text);

            //Creamos los tokens con el tokenizer
            tokens = new StringTokenizer(text);

            // Stemming

            SnowballStemmer stemmer = (SnowballStemmer) new spanishStemmer(); 
            String nw;
            
            while(tokens.hasMoreTokens()){
                nw = tokens.nextToken();
                
                if(!emptyWords.containsKey(nw)){ 
                    stemmer.setCurrent(nw);
                    
                    if(stemmer.stem()){
                        String stemmerWord = stemmer.getCurrent();
                        
                       if(numberOfOcurrences.containsKey(stemmerWord)){
                           int n = numberOfOcurrences.get(stemmerWord);
                           numberOfOcurrences.put(stemmerWord,n+1);
                       }
                       else{
                           numberOfOcurrences.put(stemmerWord,1);
                       }
                    }
                }
            }
        }
       return numberOfOcurrences;
    //numberOfOcurrences.forEach((k,v) -> System.out.println("Key: " + k + ": Value: " + v));
}

 
    /**
     * Método para la generación de resultados en un fichero externo. 
     * Volcamos los resultados en una estructura de datos ordenada para 
     * poder tratarlos en dicho orden. 
     */ 
    public void generarResultados(HashMap<String, Integer> resultado) throws IOException {

        String sFichero = "resultados.dat";
        File fichero = new File(sFichero);

        List keys = new ArrayList(resultado.keySet());
        List values = new ArrayList(resultado.values());
        TreeSet valuesOrded = new TreeSet(values);
        Object[] arrayOrdenado = valuesOrded.toArray();

        if (!(fichero.exists())) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));

            for (int i = arrayOrdenado.length - 1; i >= 0; i--) {
                bw.write(keys.get(values.indexOf(arrayOrdenado[i])) + " " + arrayOrdenado[i] + "\n");
            }
            bw.close();
        }

    }

    // Main. 
    public static void main(String[] args) throws IOException {
        String text = new String();
        TextIndexer t = new TextIndexer("./palabras_vacias.txt");
        HashMap<String, Integer> resultado = new HashMap();
        //Leemos el documento
        resultado = t.indexText("./quijote", resultado);

        resultado.forEach((k, v) -> System.out.println("Key: " + k + ": Value: " + v));

        t.generarResultados(resultado);
    }
}
