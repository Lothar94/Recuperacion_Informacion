/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathindexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
/**
 *
 * @author lot94
 */
public class MathIndexer {
   
    private Version version;
    
    public MathIndexer() throws IOException{
        this.version = Version.LUCENE_6_2_1;
    }
    
    /**
     * Método para eliminar una expresión regular de un String
     * @param text Texto a tratar
     * @param regexp Expresión regular en forma de String
     * @return Texto modificado
     */
    public String remove(String text, String regexp) {
        // Creamos el nuevo String.
        String newText = new String();
        // Tomamos la expresión regular y buscamos coincidencias.
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(text);
        // Reemplaza lo que coincida con la expresión regular por vacío. 
        newText = m.replaceAll(" ");
        // Eliminamos mayúsculas. 
        newText = newText.toLowerCase();
        return newText;
    }
    

    /**
     * Método para leer un archivo CSV con datos y crear un índice. 
     * El tipo de archivo debe ser el específico para esta práctica, con las columnas de la forma adecuada.
     * @param fileName
     * @param indexPath
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void readAndIndex(String fileName,String indexPath,String taxoPath) throws FileNotFoundException, IOException{
        // Variables iniciales
        Path path = FileSystems.getDefault().getPath(indexPath);
        Path taxo = FileSystems.getDefault().getPath(taxoPath);
        Directory dir = FSDirectory.open(path);
        Directory taxoDir = FSDirectory.open(taxo);
        
        FacetsConfig config = new FacetsConfig();
        
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig writerConf = new IndexWriterConfig(analyzer);
        writerConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(dir, writerConf);
        
        DirectoryTaxonomyWriter taxoWriter = new DirectoryTaxonomyWriter(taxoDir);
        
        File file = new File(fileName);
        Scanner inputStream = new Scanner(file);
        // Saltamos la primera línea. 
        inputStream.nextLine(); 
        
        // Documento que iremos añadiendo en el bucle, uno para cada línea que leamos
        Document doc;
        
        // Continuamos mientras haya líneas en el CSV
        while(inputStream.hasNext()){
            String data = inputStream.nextLine();
            // Separamos las columnas del CSV mediante una expresión regular
            String[] parts = data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); 
            // Creamos nuevo documento vacío
            doc = new Document(); 
            
            // Iteramos para asignar a cada columna su atributo y tipo de campo de Lucene correspondiente
            // Hecho de forma manual, conociendo la estructura del CSV
            for(int i = 0; i < parts.length; i++){
                
                // Eliminamos comillas para aquellos campos que no se tokenicen. 
                parts[i] = parts[i].replace("\"", ""); 
                      
                // Switch - case que distingue los campos almacenados en el índice. 
                switch (i) {
                    case 0: // Autor
                        doc.add(new StringField("Autor", parts[i].toLowerCase(), Field.Store.YES)); 
                        break; 
                    case 1:  // Título
                        doc.add(new TextField("Titulo", parts[i], Field.Store.YES)); 
                        break;
                    case 2:  // Año
                        // ParseInt no funciona con el string vacío, así que distinguimos casos. 
                        int year; 
                            if (!"".equals(parts[i]))
                                year = Integer.parseInt(parts[i]); 
                            else
                                year = 0; 
                        doc.add(new IntPoint("Año", year)); 
                        
                        break;
                    case 3: // Fuente
                        doc.add(new StringField("Fuente", parts[i].toLowerCase(), Field.Store.YES)); 
                        break; 
                    case 4: // Página inicio
                        int pageS; 
                        parts[i] = remove(parts[i],"[^0-9]");
                        if(parts[i].contains(" "))
                            parts[i] = parts[i].substring(0, parts[i].lastIndexOf(" "));
                        parts[i] = parts[i].replaceAll("\\s", "");                        
                        // Si no tenemos el string vacío, convertimos a int. Si no, tomamos 0. 
                        if (!"".equals(parts[i])){
                            pageS = Integer.parseInt(parts[i]); 
                        }
                        else
                            pageS = 0; 
                        doc.add(new IntPoint("Página inicio", pageS)); 
                        break; 
                    case 5: // Página Fin
                        int pageE; 
                        parts[i] = remove(parts[i],"[^0-9]");
                        if(parts[i].contains(" "))
                            parts[i] = parts[i].substring(0, parts[i].lastIndexOf(" "));
                        parts[i] = parts[i].replaceAll("\\s", "");                         
                        if (!"".equals(parts[i])){
                            pageE = Integer.parseInt(parts[i]); 
                        }
                        else
                            pageE = 0; 
                        doc.add(new IntPoint("Página fin", pageE)); 
                        break; 
                    case 6: // Enlace
                        doc.add(new StringField("Link", parts[i], Field.Store.YES)); 
                        break; 
                    case 7: // Abstract
                        doc.add(new TextField("Abstract", parts[i], Field.Store.YES)); 
                        break; 
                    case 8: // Author Keywords
                        doc.add(new TextField("Palabras clave autor", parts[i], Field.Store.YES));
                        break;
                    case 9: // Index Keywords
                        doc.add(new TextField("Palabras clave índice", parts[i], Field.Store.YES));
                        break;
                    case 10: // Referencias
                        String str=parts[i].toLowerCase();
                        if(str.length()>32000){
                             str=str.substring(0, 32000);
                        }
                        doc.add(new StringField("Referencias",str, Field.Store.YES)); 
                        break; 
                    case 11: // Idioma 
                        doc.add(new StringField("Idioma", parts[i].toLowerCase(), Field.Store.YES));
                        doc.add(new FacetField("Idioma",parts[i].toLowerCase()));
                        break; 
                    case 12: // Tipo de documento
                        System.out.println(parts[i]);
                        doc.add(new StringField("Tipo de documento", parts[i].toLowerCase(), Field.Store.YES));
                        doc.add(new FacetField("Tipo de documento", parts[i].toLowerCase()));
                        break;
                    default: 
                        break;                                                                
                } 
            }
            //writer.addDocument(doc);
            writer.addDocument(config.build(taxoWriter,doc));
        }
        
        writer.close();
        taxoWriter.close();
        dir.close();
        taxoDir.close();
        inputStream.close();   
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        MathIndexer test = new MathIndexer();
        test.readAndIndex("../Data/FourierCortado.csv","../Index","../Index/taxo");
    }
    
}
