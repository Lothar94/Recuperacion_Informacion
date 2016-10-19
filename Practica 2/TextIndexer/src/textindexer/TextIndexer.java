/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textindexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.regex.*;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.spanishStemmer;

/**
 *
 * @author lot94
 */
        
public class TextIndexer {
    TextReader rd;
    Hashtable<String,Boolean> emptyWords;
    StringTokenizer tokens; 
    
    
    
    public TextIndexer(String emptyWordsPath) throws IOException{
        emptyWords = new Hashtable<>();
        rd = new TextReader();
        emptyWords = rd.readEmptyWords(emptyWordsPath);
    }
    
    
    
    /* Función para eliminar los signos de puntuación con una expresión regular
    * hace falta ajustar el tipo de codificación para que elimine correctamente
    * los acentos y no quite algunas letras del castellano como la Q, q o ñ.
    */
    public String removePunctuation(String t){
        String newText = new String();
        newText = newText.toLowerCase(); 
        Pattern p = Pattern.compile("[^a-z0-9áéíóúñ]");
        Matcher m = p.matcher(t);
        newText = m.replaceAll(" ");
        return newText;
    }
    
    public HashMap<String,Integer> indexText(String filePath,HashMap<String,Integer> numberOfOcurrences) throws IOException{
        System.out.println(filePath);
        String text = new String();
        if(rd.isDirectory(filePath)){
            ArrayList<String> paths;
            paths=rd.readDirectory(filePath);
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
                       }else{
                           numberOfOcurrences.put(stemmerWord,1);
                       }
                    }
                }
            }
        }
       return numberOfOcurrences;
    //numberOfOcurrences.forEach((k,v) -> System.out.println("Key: " + k + ": Value: " + v));
    }
    
    
    public static void main(String[] args) throws IOException {
        String text = new String();
        TextIndexer t = new TextIndexer("./palabras_vacias.txt"); 
        HashMap<String,Integer> resultado= new HashMap();
        //Leemos el documento
        resultado=t.indexText("./quijote",resultado);
        resultado.forEach((k,v) -> System.out.println("Key: " + k + ": Value: " + v));
    }
    
}
