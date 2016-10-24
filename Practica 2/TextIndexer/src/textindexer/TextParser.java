package textindexer; 

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
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

import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageResult;

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
    public static String identifyLanguage(String text) throws IOException {
        LanguageDetector identifier = new OptimaizeLangDetector().loadModels();
        LanguageResult idioma = identifier.detect(text);
        return idioma.getLanguage();
    }
    
    /**
     * Procesa un archivo y extrae sus datos.: 
     * 
     * 
     * @param file archivo a procesar. 
     * @return string 
     * @throws Exception 
     */
    public static String process(File file) throws Exception {
        
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        BodyContentHandler handler = new BodyContentHandler();
        InputStream stream = new FileInputStream(file);
        ParseContext context = new ParseContext();
        //extraer contenido
        try {
            parser.parse(stream, handler, metadata,context);

        } finally {
            stream.close();
        }
        return handler.toString();
        
    }
    
    public static List<Link> getLinks(File file) throws Exception {
        
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        LinkContentHandler handler = new LinkContentHandler();
        InputStream stream = new FileInputStream(file);
        ParseContext context = new ParseContext();
        
        try {
            parser.parse(stream, handler, metadata,context);

        } finally {
            stream.close();
        }
        
        List<Link> links  = new LinkedList<Link>();
        links = handler.getLinks();
        return  links;
    }

}
