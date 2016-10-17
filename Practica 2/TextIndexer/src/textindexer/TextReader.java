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
         // Lectura del fichero
   
         return text;
    }
}
