/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textindexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author lot94
 */

public class TextReader {
    public String read(String filepath) throws IOException{
        String text = new String();
        try{
            File file = new File(filepath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
               text = text.concat(" "+line);
            }
            fr.close(); 
        }catch(Exception e){
            System.out.println("Archivo no encontrado.");
        }        
   
         return text;
    }
    
    public Hashtable<String,Boolean> readEmptyWords(String filepath) throws IOException{
        Hashtable<String,Boolean> emptyWords = new Hashtable();
        try{
            File file = new File(filepath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            for(int i = 0; (line = br.readLine()) != null; i++){
                emptyWords.put(line, true);
            }
            fr.close(); 
        }catch(Exception e){
            System.out.println("Archivo no encontrado.");
        }                
        
        return emptyWords;
    }
    
}
