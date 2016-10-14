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


 public class EjemploSimple13 {


 public static  String identifyLanguage(String text) throws IOException {
        LanguageDetector identifier  = new  OptimaizeLangDetector().loadModels();
        LanguageResult idioma = identifier.detect("This is spanish");
        System.out.println("XXXXXX"+idioma.getLanguage());
        return idioma.getLanguage();
    }

 public static void process(File file) throws Exception {
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
      
        LinkContentHandler handler = new LinkContentHandler();

        InputStream stream = new FileInputStream(file);
        System.out.println("seleccionando links ");
        try {
            parser.parse(stream, handler, metadata,new ParseContext());

        }
        finally {
            stream.close();
        }
        List<Link> lista  = new LinkedList<Link>();

        System.out.println(metadata.toString());
        System.out.println(handler.toString());
        lista = handler.getLinks();
        for (Link enlace : lista) {
            System.out.println("link:"+enlace.toString());
          
        }
         
         

     }

  public static void main(String[] args) throws Exception {

  // Creaamos una instancia de Tika con la configuracion por defecto
  Tika tika = new Tika();
  System.out.println("_____________En file");
  System.out.println(args.toString());
  System.out.println("_____________");
  // Se parsean todos los ficheros pasados como argumento y se extrae el contenido
  for (String file : args) {
      
      File f = new File(file);
      System.out.println(f.getAbsolutePath());
      String type = tika.detect(f);
      System.out.println(file +":::"+type);
      InputStream is = new FileInputStream(file);
      AutoDetectReader detector = new AutoDetectReader(is);
      Charset charSet = detector.getCharset();
      System.out.println("Encoding "+ charSet.toString());
      Metadata met = new Metadata();
     
      String aux = tika.parseToString(is, met);
      System.out.println(met.toString());
	String docDate = met.get("Content-Type");
	System.out.println("Tipo::"+docDate);
        System.out.println("leguaje::"+identifyLanguage(aux));

        process(f); 
    
        String text = tika.parseToString(f);
        System.out.print(text);

     
     
  }
}
 }
 
