/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textindexer;

import java.io.IOException;
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
    
    Hashtable<String,Boolean> emptyWords;
    StringTokenizer tokens; 
    
    public TextIndexer(){
        emptyWords = new Hashtable<>();
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
    
    public void indexText(String filePath, String emptyWordsPath) throws IOException{
        String text = new String();
        TextReader rd = new TextReader();
        
        //Leemos el documento
        text = rd.read(filePath);
        
        //Eliminamos los signos de puntuación
        text = removePunctuation(text);

        //Creamos los tokens con el tokenizer
        tokens = new StringTokenizer(text);
        
            
        //Leemos las palabras vacias
        emptyWords = rd.readEmptyWords(emptyWordsPath);
        
        // Stemming
        
        SnowballStemmer stemmer = (SnowballStemmer) new spanishStemmer(); 
        
        String nw;
        while(tokens.hasMoreTokens()){
            nw = tokens.nextToken();
            if(!emptyWords.containsKey(nw)){ 
                stemmer.setCurrent(nw);
                if(stemmer.stem()){
                    System.out.println(stemmer.getCurrent());
                }
            }
        }
    
       
        //Indexación en tabla hash
    }
    
    
    public static void main(String[] args) throws IOException {
        String text = new String();
        TextIndexer t = new TextIndexer(); 

        //Leemos el documento
        t.indexText("./quijote/cap1.txt", "./palabras_vacias.txt");
        
    }
    
}
