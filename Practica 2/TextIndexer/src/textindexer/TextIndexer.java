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

/**
 *
 * @author lot94
 */
        
public class TextIndexer {
    
    Hashtable<Integer,String> emptyWords;
    
    public TextIndexer(){
        emptyWords = new Hashtable<>();
    }
    
    /* Función para eliminar los signos de puntuación con una expresión regular
    * hace falta ajustar el tipo de codificación para que elimine correctamente
    * los acentos y no quite algunas letras del castellano como la Q, q o ñ.
    */
    public String removePunctuation(String t){
        String newText = new String();
        Pattern p = Pattern.compile("[^a-zA-Z0-9áéíóúñ]");
        Matcher m = p.matcher(t);
        newText = m.replaceAll(" ");
        return newText;
    }
    
    public void indexText(String filePath, String emptyWordsPath) throws IOException{
        String text = new String();
        TextReader rd = new TextReader();
        
        //Leemos el documento
        text = rd.read(filePath);
        System.out.println(text);
        
        //Eliminamos los signos de puntuación
        text = removePunctuation(text);
        System.out.println(text);

        //Creamos los tokens con el tokenizer
        StringTokenizer tokens = new StringTokenizer(text);
        
        String nw;
        while(tokens.hasMoreTokens()){
            nw = tokens.nextToken();
            System.out.println(nw);
        }
        
        //Leemos las palabras vacias
        emptyWords = rd.readEmptyWords(emptyWordsPath);
        //Stemming
        //Indexación en tabla hash
    }
    
    
    public static void main(String[] args) throws IOException {
        String text = new String();
        TextIndexer t = new TextIndexer(); 

        //Leemos el documento
        t.indexText("./Quijote/Cap1.txt", "./palabras_vacias.txt");
        
    }
    
}
