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
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

/**
 *
 * @author lot94
 */
public class MathIndexer {
   
    private Directory dir;
    private Version version;
    private Analyzer analyzer;
    private IndexWriterConfig writerConf;
    private IndexWriter writer;
    private String collectionPath;
    
    public MathIndexer(String collectionPath) throws IOException{
        Path path = FileSystems.getDefault().getPath("testIndex");

        this.version = Version.LUCENE_6_2_1;
        this.collectionPath = collectionPath;
        this.dir = FSDirectory.open(path);
        this.analyzer = new StandardAnalyzer();
        this.writerConf = new IndexWriterConfig(analyzer);
        writerConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        this.writer = new IndexWriter(dir, writerConf);
    }

    public void indexDocument(){
        
    }
    
    public void readAndIndex(String fileName) throws FileNotFoundException, IOException{
        File file = new File(fileName);
        Scanner inputStream = new Scanner(file);
        // Saltamos la primera línea. 
        inputStream.nextLine(); 
        
        Document doc = new Document(); 
        
        while(inputStream.hasNext()){
            String data = inputStream.nextLine();
            String[] parts = data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); 
            for(int i = 0; i < parts.length; i++){
                // Eliminamos comillas para aquellos que no se tokenicen. 
                parts[i] = parts[i].replace("\"", ""); 
                
                // Switch - case que distingue los campos almacenados en el índice. 
                switch (i) {
                    case 0: 
                        doc.add(new StringField("Autor", parts[i], Field.Store.YES)); 
                        break; 
                    case 1:  
                        doc.add(new TextField("Título", parts[i], Field.Store.YES)); 
                        break;
                    case 2:  
                        // ParseInt no funciona con el string vacío, así que distinguimos casos. 
                        int year; 
                            if (!"".equals(parts[i]))
                                year = Integer.parseInt(parts[i]); 
                            else
                                year = 0; 
                        doc.add(new IntPoint("Año", year)); 
                        
                        break;
                    case 3: 
                        doc.add(new StringField("Fuente", parts[i], Field.Store.YES)); 
                        break; 
                    case 4: 
                        int pageS; 
                            if (!"".equals(parts[i]))
                                pageS = Integer.parseInt(parts[i]); 
                            else
                                pageS = 0; 
                        doc.add(new IntPoint("Página inicio", pageS)); 
                        break; 
                    case 5: 
                        int pageE; 
                            if (!"".equals(parts[i]))
                                pageE = Integer.parseInt(parts[i]); 
                            else
                                pageE = 0; 
                        doc.add(new IntPoint("Página fin", pageE)); 
                        break; 
                    case 6: 
                        doc.add(new StringField("Link", parts[i], Field.Store.YES)); 
                        break; 
                    case 7: 
                        doc.add(new TextField("Abstract", parts[i], Field.Store.NO)); 
                        break; 
                    case 8: 
                        doc.add(new TextField("Palabras clave autor", parts[i], Field.Store.YES));
                        break;
                    case 9: 
                        doc.add(new TextField("Palabras clave índice", parts[i], Field.Store.YES));
                        break;
                    case 10: 
                        doc.add(new StringField("Referencias", parts[i], Field.Store.YES)); 
                        break; 
                    case 11: 
                        doc.add(new StringField("Idioma", parts[i], Field.Store.YES)); 
                        break; 
                    case 12: 
                        doc.add(new StringField("Tipo de documento", parts[i], Field.Store.YES));
                        break; 
                    default: 
                        break;                                                                
                } 
               
            }
        }
        writer.addDocument(doc); 
        writer.close(); 
        inputStream.close();   
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        MathIndexer test = new MathIndexer("../Data");
        test.readAndIndex("../Data/Cortado.csv");
    }
    
}
