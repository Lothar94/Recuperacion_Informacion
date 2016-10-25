/**
 * Este archivo implementa los métodos de lectura de ficheros, 
 * así como la comprobación de directorios.
 */
package textindexer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.apache.tika.metadata.Metadata;

/**
 *
 * @author Lothar Soto
 * @author Iván Calle
 * @author Daniel López
 * @author José Carlos Entrena
 */

public class TextReader {
    
    // Comprueba si el path que pasamos es un directorio. 
    public Boolean isDirectory(String filepath) throws IOException{
        Boolean isDirectory = false;
        try{
            File file = new File(filepath);
            isDirectory = file.isDirectory();
        }catch(Exception e){
            System.out.println("Archivo no encontrado.");
        }        
   
         return isDirectory;
    }
    
    // Lectura del texto del archivo encontrado bajo un path dado. 
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
    
    /**
     * Procesa una serie de archivos y crea una tabla con su tipo 
     * y codificación.: 
     * 
     * @param filePaths Lista de archivos.  
     * @throws Exception, IOException 
     */
    public void writeFileTable(List<String> filePaths) throws IOException, Exception{
        //Creamos el archivo que tendrá la tabla
        String sFichero = "Tabla_archivos.csv";
        File fichero = new File(sFichero);
        TextParser t = new TextParser();
        Metadata metadata = new Metadata();
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));
        bw.write("\"Nombre\"; \"Tipo\"; \"Codificación\" \n");

        //Recorremos los archivos obteniendo sus metadatos
        for(String file : filePaths){
            metadata = t.getMetadata(file);
            //Escribimos su nombre codificación y tipo
            bw.write("\""+file+"\"; \""+metadata.get(Metadata.CONTENT_TYPE)+"\"; \""+metadata.get(Metadata.CONTENT_ENCODING)+"\"\n");
        }
        bw.close();
    }
    
    // Lectura de los archivos en un directorio. 
    public ArrayList<String> readDirectory(String filepath) throws IOException{
        ArrayList<String> paths= new ArrayList();
        try{
            File folder = new File(filepath);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                paths.add(listOfFiles[i].getPath());
            }
        }catch(Exception e){
            System.out.println("Directorio no encontrado.");
        }        
   
         return paths;
    }
    
    // Lectura del archivo de palabras vacías. 
    // Usamos una tabla hash con valores booleanos, pues no contamos el número de ocurrencias. 
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
