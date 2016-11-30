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

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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
    private Version ver;
    private Analyzer analizer;
    private IndexWriterConfig writerConf;
    private IndexWriter writer;
    
    public MathIndexer(String file) throws IOException{
        Path path = FileSystems.getDefault().getPath("testIndex");

        this.ver = Version.LUCENE_6_2_1;
        this.dir = FSDirectory.open(path);
        this.analizer = new StandardAnalyzer();
        this.writerConf = new IndexWriterConfig(analizer);
        writerConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        this.writer = new IndexWriter(dir, writerConf);
    }

    public void indexDocument(){
        
    }
    
    public void readAndIndex(){
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
    }
    
}
