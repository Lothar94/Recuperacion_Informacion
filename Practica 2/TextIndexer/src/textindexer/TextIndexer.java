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
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.*;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.danishStemmer;
import org.tartarus.snowball.ext.dutchStemmer;
import org.tartarus.snowball.ext.finnishStemmer;
import org.tartarus.snowball.ext.germanStemmer;
import org.tartarus.snowball.ext.hungarianStemmer;
import org.tartarus.snowball.ext.italianStemmer;
import org.tartarus.snowball.ext.norwegianStemmer;
import org.tartarus.snowball.ext.portugueseStemmer;
import org.tartarus.snowball.ext.russianStemmer;
import org.tartarus.snowball.ext.spanishStemmer;
import org.tartarus.snowball.ext.swedishStemmer;

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
    List<String> filePaths;
    String rootDirectory;

    public TextIndexer(String emptyWordsPath) throws IOException {
        emptyWords = new Hashtable<>();
        rd = new TextReader();
        emptyWords = rd.readEmptyWords(emptyWordsPath);
        filePaths = new ArrayList<String>();
        rootDirectory=null;
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
        Pattern p = Pattern.compile("[^A-Za-z0-9áéíóúñ]");
        Matcher m = p.matcher(t);
        // Reemplaza lo que coincida con la expresión regular por vacío. 
        newText = m.replaceAll(" ");
        // Eliminamos mayúsculas. 
        newText = newText.toLowerCase();
        return newText;
    }

    /**
     * Método para la lectura y lexificación de los datos obtenidos.
     */
    public HashMap<String,Integer> indexText(String filePath,HashMap<String,Integer> numberOfOcurrences) 
           throws IOException{
        
        //System.out.println(filePath);
        String text = new String();
        
        // Si es un directorio, leemos sus archivos y los indexamos de forma recursiva. 
        if(rd.isDirectory(filePath)){
            if(rootDirectory == null){
                // Reemplazamos \ por / ('\\' escapa solo una barra \). 
                rootDirectory = filePath.replace('\\','/');
            }
            ArrayList<String> paths;
            paths = rd.readDirectory(filePath);
            // Llamada recursiva a la función de indexación
            for (String file: paths) {
                numberOfOcurrences = indexText(file,numberOfOcurrences);
            }
        }
        else{
            //Leemos el documento
            text = rd.read(filePath);
            
            //Identificar lenguaje
            TextParser parser = new TextParser();
            String lang = parser.identifyLanguage(text);
            
            //Guardamos las rutas de los ficheros leidos
            filePaths.add(filePath);
            
            
            //Eliminamos los signos de puntuación
            text = removePunctuation(text);

            //Creamos los tokens con el tokenizer
            tokens = new StringTokenizer(text);
            SnowballStemmer stemmer;
            // Stemming
            Boolean langDetected = true;
            switch(lang){
                case "da":
                    stemmer = (SnowballStemmer) new danishStemmer();
                    break;
                case "de":
                    stemmer = (SnowballStemmer) new germanStemmer();
                    break;
                case "fi":
                    stemmer = (SnowballStemmer) new finnishStemmer();
                    break;
                case "hu":
                    stemmer = (SnowballStemmer) new hungarianStemmer();
                    break;
                case "it":
                    stemmer = (SnowballStemmer) new italianStemmer();
                    break;
                case "nl":
                    stemmer = (SnowballStemmer) new dutchStemmer();
                    break;
                case "no":
                    stemmer = (SnowballStemmer) new norwegianStemmer();
                    break;
                case "pt":
                    stemmer = (SnowballStemmer) new portugueseStemmer();
                    break;
                case "ru":
                    stemmer = (SnowballStemmer) new russianStemmer();
                    break;
                case "sv":
                    stemmer = (SnowballStemmer) new swedishStemmer();
                    break;
                case "es":
                    stemmer = (SnowballStemmer) new spanishStemmer();
                    break;
                default:
                    stemmer = null;
                    langDetected = false;
                    break;     
            } 
            // Si se ha detectado el lenguaje, lexificamos. 
            if(langDetected){
                String nextToken;
                StringBuilder stemText = new StringBuilder();
                
                while(tokens.hasMoreTokens()){
                    nextToken = tokens.nextToken();
                    // Si el token no está en las palabras vacías: 
                    if(!emptyWords.containsKey(nextToken)){ 
                        stemmer.setCurrent(nextToken);
                        // Lexificamos. 
                        if(stemmer.stem()){
                            String stemmerWord = stemmer.getCurrent();
                            stemText.append(stemmerWord);
                            stemText.append(" ");
                            // Añadimos a la tabla hash. 
                            if(numberOfOcurrences.containsKey(stemmerWord)){
                               int n = numberOfOcurrences.get(stemmerWord);
                               numberOfOcurrences.put(stemmerWord,n+1);
                            }
                            else
                               numberOfOcurrences.put(stemmerWord,1);                       
                        }
                    }
                }

                int ini = filePath.indexOf(rootDirectory.replace('\\','/' ));
                int fin = ini + rootDirectory.length();
                crearArchivo("./stems/"+filePath.substring(fin),stemText.toString());
            }
        }
        
       return numberOfOcurrences;
}

 
    /**
     * Método para la generación de resultados en un fichero externo. 
     * Volcamos los resultados en una estructura de datos ordenada para 
     * poder tratarlos en dicho orden. 
     */ 
    public void generarResultados(HashMap<String, Integer> resultado) throws IOException {

        String sFichero = "resultados.csv";
        File fichero = new File(sFichero);

        if (!(fichero.exists())) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));

            //Obtenemos los valores de la tabla hash y los ordenamos en una lista
            TreeSet values = new TreeSet(resultado.values());
            ArrayList listaOrdenada=  new ArrayList(values);
            Collections.reverse(listaOrdenada);
            
            Integer tmp;
            Integer ranking =1;
            for (int i = 0; i < listaOrdenada.size(); i++) {
                tmp = (Integer) listaOrdenada.get(i);
                for (Map.Entry k : resultado.entrySet()) {
                    if (tmp == k.getValue()) {
                        bw.write("\""+ k.getKey() + "\"; " + k.getValue() + "; " + ranking+"\n");
                        ranking++;
                    }
                }

            }
            bw.close();
        } else {
            System.out.println("El fichero ya existe");
        }

    }
    public void crearArchivo(String path,String text) throws IOException {
        
        String sFichero = path;
        File fichero = new File(sFichero);
        File dir = new File(path.substring(0, path.lastIndexOf("/")));
        dir.mkdirs();
        if (!(fichero.exists())) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));
            bw.write(text);
            bw.close();
        } else {
            System.out.println("El fichero ya existe");
        }

    }
    
}
