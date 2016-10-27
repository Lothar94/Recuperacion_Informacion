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

public class FileIO {
    
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
        TextParser textParser = new TextParser();
        try{
            text = textParser.getText(filepath);
        }catch(Exception e){
            System.out.println("Archivo no encontrado.");
        }        
   
        return text;
    }
    
    /**
     * Procesa una serie de archivos y crea una tabla con su tipo 
     * y codificación. 
     * 
     * @param filePaths Lista de archivos.  
     * @throws Exception, IOException 
     */
    public void writeFileTable(List<String> filePaths) throws IOException, Exception{
        //Creamos el archivo que tendrá la tabla
        String fichero = "data/Tabla_archivos.csv";
        TextParser t = new TextParser();
        Metadata metadata = new Metadata();
        
        FileWriter bw = new FileWriter(fichero);
        bw.write("\"Nombre\"; \"Tipo\"; \"Codificación\" \n");

        //Recorremos los archivos obteniendo sus metadatos
        for(String file : filePaths){
            metadata = t.getMetadata(file);
            String typeEnconding = metadata.get(Metadata.CONTENT_TYPE);
            String type = typeEnconding.substring(0,typeEnconding.lastIndexOf(";"));
            String encoding = typeEnconding.substring(typeEnconding.lastIndexOf(";")+1,typeEnconding.length());
            
            //Escribimos su nombre codificación y tipo
            bw.write("\""+file+"\"; \""+type+"\"; \""+encoding+"\"\n");
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
    
    
    public void crearArchivo(String path, String text) throws IOException {
        
        File fichero = new File(path);
        File dir = new File(path.substring(0, path.lastIndexOf("/")));
        dir.mkdirs();
        
        FileWriter fw = new FileWriter(fichero);
        fw.write(text);
        fw.close();

    }
    
}
