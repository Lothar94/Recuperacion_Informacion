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
 * @author Lothar Soto Palma
 * @author Daniel López García
 * @author Iván Calle Gil
 * @author José Carlos Entrena Jiménez
 */
public class MathIndexer {

    private Version version;

    public MathIndexer() throws IOException {
        this.version = Version.LUCENE_6_2_1;
    }

    /**
     * Método para eliminar una expresión regular de un String
     *
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
     * Método para leer un archivo CSV con datos y crear un índice. El tipo de
     * archivo debe ser el específico para esta práctica, con las columnas de la
     * forma adecuada.
     *
     * @param fileName
     * @param indexPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void readAndIndex(String fileName, String indexPath, String taxoPath) throws FileNotFoundException, IOException {
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
        while (inputStream.hasNext()) {
            String data = inputStream.nextLine();
            // Separamos las columnas del CSV mediante una expresión regular
            String[] parts = data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            
            if (parts.length != 13)
                continue; 
            // Creamos nuevo documento vacío
            doc = new Document();

            for (int i = 0; i < parts.length; i++) {

                // Eliminamos comillas para aquellos campos que no se tokenicen. 
                parts[i] = parts[i].replace("\"", "");
            }
            // Añadimos los campos, en el orden que están en el archivo

            // Autor
            doc.add(new StringField("Autor", parts[0].toLowerCase(), Field.Store.YES));

            // Título
            doc.add(new TextField("Titulo", parts[1], Field.Store.YES));

            // Año
            int year;
            if (!"".equals(parts[2])) {
                year = Integer.parseInt(parts[2]);
            } else {
                year = 0;
            }
            doc.add(new IntPoint("Año", year));

            // Fuente
            doc.add(new StringField("Fuente", parts[3].toLowerCase(), Field.Store.YES));

            // Página inicio
            int pageS;
            parts[4] = remove(parts[4], "[^0-9]");
            if (parts[4].contains(" ")) {
                parts[4] = parts[4].substring(0, parts[4].lastIndexOf(" "));
            }
            parts[4] = parts[4].replaceAll("\\s", "");
            // Si no tenemos el string vacío, convertimos a int. Si no, tomamos 0. 
            if (!"".equals(parts[4])) 
                pageS = Integer.parseInt(parts[4]);
            else 
                pageS = 0;
            

            doc.add(new IntPoint("Página inicio", pageS));

            // Página fin
            int pageE;
            parts[5] = remove(parts[5], "[^0-9]");
            if (parts[5].contains(" ")) 
                parts[5] = parts[5].substring(0, parts[5].lastIndexOf(" "));
            
            parts[5] = parts[5].replaceAll("\\s", "");
            if (!"".equals(parts[5])) 
                pageE = Integer.parseInt(parts[5]);
            else 
                pageE = 0;
            doc.add(new IntPoint("Página fin", pageE));

            // Link
            doc.add(new StringField("Link", parts[6], Field.Store.YES));

            // Abstract
            doc.add(new TextField("Abstract", parts[7], Field.Store.YES));

            // Palabras clave autor
            doc.add(new TextField("Palabras clave autor", parts[8], Field.Store.YES));

            // Palabras clave índice
            doc.add(new TextField("Palabras clave índice", parts[9], Field.Store.YES));

            // Referencias
            String str = parts[10].toLowerCase();
            // Cortamos por si son demasiado largas
            if (str.length() > 32000) 
                str = str.substring(0, 32000);
            

            doc.add(new StringField("Referencias", str, Field.Store.YES));

            // Idioma (también como faceta)
            doc.add(new StringField("Idioma", parts[11].toLowerCase(), Field.Store.YES));
            doc.add(new FacetField("Idioma", parts[11].toLowerCase()));

            // Tipo de documento (también como faceta)
            doc.add(new StringField("Tipo de documento", parts[12].toLowerCase(), Field.Store.YES));
            doc.add(new FacetField("Tipo de documento", parts[12].toLowerCase()));

            //writer.addDocument(doc);
            writer.addDocument(config.build(taxoWriter, doc));

        }
        
        // Cerramos writers, directorios y demás
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
        test.readAndIndex("../Data/Data.csv", "../Index", "../Index/taxo");
    }

}
