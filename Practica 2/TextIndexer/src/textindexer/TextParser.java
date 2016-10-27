package textindexer; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ContentHandler;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.apache.tika.Tika;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.metadata.Metadata;

import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.sax.Link;
import java.util.StringTokenizer;
import java.util.LinkedList;
import java.util.List;

import org.apache.tika.detect.AutoDetectReader;
import org.apache.tika.exception.TikaException;

import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.xml.sax.SAXException;

/**
 *
 * @author Lothar Soto
 * @author Iván Calle
 * @author Daniel López
 * @author José Carlos Entrena
 */

public class TextParser {
/**
 * Función para detectar el idioma de un texto.
 * Utiliza LanguageIdentifier y LanguageDetector.
 */
    
    public String identifyLanguage(String text) throws IOException {
        LanguageDetector identifier = new OptimaizeLangDetector().loadModels();
        LanguageResult idioma = identifier.detect(text);
        return idioma.getLanguage();
    }
    
    /**
     * Procesa un archivo y extrae sus datos.: 
     * 
     * @param file archivo a procesar. 
     * @return string 
     * @throws Exception 
     */
    public String process(String filename) throws Exception {
        
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        BodyContentHandler handler = new BodyContentHandler();
        InputStream stream = new FileInputStream(filename);
        ParseContext context = new ParseContext();
        //extraer contenido
        try {
            parser.parse(stream, handler, metadata,context);

        } finally {
            stream.close();
        }
        return handler.toString();
        
    }
    
    public String getText(String filename) throws FileNotFoundException, IOException, SAXException, TikaException{
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        BodyContentHandler handler = new BodyContentHandler(-1);
        InputStream stream = new FileInputStream(filename);
        ParseContext context = new ParseContext();
        
        try {
            parser.parse(stream, handler, metadata, context);

        }catch(Exception e){
            System.out.println("Error en el parser");
        }
        
        stream.close();
        return handler.toString();
    }
    
    /**
     * Devuelve una lista de los links que aparecen en un archivo. 
     * @param filename nombre del archivo
     * @return Lista de links, objeto de la clase Link. 
     * @throws Exception 
     */
    public List<Link> getLinks(String filename) throws Exception {
        
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        LinkContentHandler handler = new LinkContentHandler();
        InputStream stream = new FileInputStream(filename);
        ParseContext context = new ParseContext();
        
        try {
            parser.parse(stream, handler, metadata, context);

        } finally {
            stream.close();
     }
      return handler.getLinks();
     
}
    /**
     * Escribe los links en el archivo Links.txt. 
     * @param filenames Lista de archivos. 
     * @throws Exception 
     */
    public void writeLinks(List<String> filenames) throws Exception{
        // Abrimos el archivo de links. 
        PrintWriter writer = null; 
        List<Link> links; 
        try {
            File file = new File("data/Links.txt"); 
            // Sobrescribe el archivo si ya existe.
            FileWriter wr = new FileWriter(file); 
            writer = new PrintWriter(wr);
            // Extraemos los links de cada archivo. 
            for (int i = 0; i < filenames.size(); i++){
                links = getLinks(filenames.get(i));

                // Escribimos todos los links del archivo en el fichero, poniendo antes 
                // el fichero del que provienen. 
                writer.println("Archivo: " + filenames.get(i)); 
                writer.println("Links: "); 
                for (int j = 0; j < links.size(); j++){
                    writer.println(links.get(j).toString());
                }
            }
        }
        // Exception.
        catch (IOException e){
            e.printStackTrace(); 
        }
        // Closing anyways. 
        finally{
            if (writer != null){
                writer.close(); 
            }
        }
    }
    
    public Metadata getMetadata(String filename) throws Exception{
        
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        BodyContentHandler handler = new BodyContentHandler();
        InputStream stream = new FileInputStream(filename);
        ParseContext context = new ParseContext();
        
        try {
            parser.parse(stream, handler, metadata,context);

        } finally {
            stream.close();
        }
        return metadata;
               
    }
    
}