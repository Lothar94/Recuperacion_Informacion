/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package luceneej1;


import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.util.ArrayList;

/**
 * This terminal application creates an Apache Lucene index in a folder and adds files into this index
 * based on the input of the user.
 */

/**
 *
 * @author jhg
 */
public class IndexarFichero {

  private static SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43);

  private IndexWriter writer;
  private ArrayList<File> queue = new ArrayList<File>();


  public static void main(String[] args) throws IOException {
    System.out.println("Direccion de directorio de indexacion: (e.g. /tmp/index or c:\\temp\\index)");

    String indexLocation = null;
    BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    String s = br.readLine();
    IndexarFichero indexer = null;
    try {
      indexLocation = s;
      indexer = new IndexarFichero(s);
    } catch (Exception ex) {
      System.out.println("No puedo crear indice..." + ex.getMessage());
      System.exit(-1);
    }

    //===================================================
    //read input from user until he enters q for quit
    //===================================================
    while (!s.equalsIgnoreCase("q")) {
      try {
        System.out.println("Fichero/Direcorio a incluir en el indice (q=quit): (e.g. /home/ron/mydir or c:\\Users\\ron\\mydir)");
        System.out.println("Tipo de ficheros aceptados: .xml, .html, .html, .txt]");
        s = br.readLine();
        if (s.equalsIgnoreCase("q")) {
          break;
        }

        //try to add file into the index
        indexer.indexFileOrDirectory(s);
      } catch (Exception e) {
        System.out.println("Error indexando " + s + " : " + e.getMessage());
      }
    }

    //===================================================
    //after adding, we always have to call the
    //closeIndex, otherwise the index is not created
    //===================================================
    indexer.closeIndex();
    }

  /**
   * Constructor
   * @param indexDir the name of the folder in which the index should be created
   * @throws java.io.IOException when exception creating index.
   */
   IndexarFichero(String indexDir) throws IOException {

    FSDirectory dir = FSDirectory.open(new File(indexDir));
    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43, analyzer);
    config.setOpenMode(IndexWriterConfig.OpenMode.CREATE); //Lo abrimos para crear
    writer = new IndexWriter(dir, config);

  }

  /**
   * Indexes a file or directory
   * @param fileName the name of a text file or a folder we wish to add to the index
   * @throws java.io.IOException when exception
   */
  public void indexFileOrDirectory(String fileName) throws IOException {
    //===================================================
    //gets the list of files in a folder (if user has submitted
    //the name of a folder) or gets a single file name (is user
    //has submitted only the file name)
    //===================================================
    addFiles(new File(fileName));

    int originalNumDocs = writer.numDocs();
    for (File f : queue) {
      FileReader fr = null;
      try {
        Document doc = new Document();


        //===================================================
        // add contents of file
        //===================================================
        fr = new FileReader(f);
        doc.add(new TextField("contents", fr));
        doc.add(new StringField("path", f.getPath(), Field.Store.YES));
        doc.add(new StringField("filename", f.getName(), Field.Store.YES));
        doc.add(new LongField("size",f.length(),Field.Store.YES));
        doc.add(new LongField("created",f.lastModified(),Field.Store.NO));

        writer.addDocument(doc);
        System.out.println("Added: " + f);
      } catch (Exception e) {
        System.out.println("Could not add: " + f);
      } finally {
        fr.close();
      }
    }

    int newNumDocs = writer.numDocs();
    System.out.println("");
    System.out.println("************************");
    System.out.println((newNumDocs - originalNumDocs) + " documents added.");
    System.out.println("************************");

    queue.clear();
  }

  private void addFiles(File file) {

    if (!file.exists()) {
      System.out.println(file + " does not exist.");
    }
    if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        addFiles(f);
      }
    } else {
      String filename = file.getName().toLowerCase();
      //===================================================
      // Only index text files
      //===================================================
      if (filename.endsWith(".htm") || filename.endsWith(".html") ||
              filename.endsWith(".xml") || filename.endsWith(".txt")) {
        queue.add(file);
      } else {
        System.out.println("Skipped " + filename);
      }
    }
  }

  /**
   * Close the index.
   * @throws java.io.IOException when exception closing
   */
  public void closeIndex() throws IOException {
    writer.close();
  }
}
 
