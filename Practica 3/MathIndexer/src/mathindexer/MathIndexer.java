/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathindexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author lot94
 */
public class MathIndexer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        BodyContentHandler handler = new BodyContentHandler(-1);
        InputStream stream = new FileInputStream("../Data/A-new-direct-time-integration-method-for-the-semi-discrete-parabolic-equations_2016_Engineering-Analysis-with-Boundary-Elements.pdf");
        ParseContext context = new ParseContext();
        
        try {
            parser.parse(stream, handler, metadata, context);

        }catch(Exception e){
            System.out.println("Error en el parser");
        }
        
        stream.close();
        
        File f = new File("output3.txt");
        FileWriter bw = new FileWriter(f);
        bw.write(handler.toString());
        
        bw.close();

        File f2 = new File("metadata.txt");
        FileWriter bw2 = new FileWriter(f2);
        bw2.write(metadata.toString());
        
        bw2.close();
    }
    
}
