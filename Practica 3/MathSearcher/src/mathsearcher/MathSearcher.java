/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathsearcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author lot94
 */
public class MathSearcher {
    private String indexPath;
    public MathSearcher(String indexPath){
        this.indexPath=indexPath;
    }
    
    public ArrayList<Document> searchString(String field,String value,int nDocuments) throws FileNotFoundException, IOException, ParseException{
        Path path = FileSystems.getDefault().getPath(indexPath);
        Directory dir = FSDirectory.open(path);
        IndexReader ireader = DirectoryReader.open(dir);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser(field, analyzer);
        Query query = parser.parse(value);
        ScoreDoc[] hits = isearcher.search(query, nDocuments).scoreDocs;
        ArrayList<Document> resultado=new ArrayList();
        for (int i = 0; i < hits.length; i++) {
           resultado.add(isearcher.doc(hits[i].doc));
        }
        ireader.close();
        dir.close();
        return resultado;
    }
    public ArrayList<Document> searchIntExact(String field,int value,int nDocuments) throws FileNotFoundException, IOException, ParseException{
        Path path = FileSystems.getDefault().getPath(indexPath);
        Directory dir = FSDirectory.open(path);
        IndexReader ireader = DirectoryReader.open(dir);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        Query query2=IntPoint.newExactQuery(field, value);
        ScoreDoc[] hits = isearcher.search(query2, nDocuments).scoreDocs;
         ArrayList<Document> resultado=new ArrayList();
        for (int i = 0; i < hits.length; i++) {
           resultado.add(isearcher.doc(hits[i].doc));
        }
        ireader.close();
        dir.close();
        return resultado;
    }
    public ArrayList<Document> searchIntRange(String field,int lowerValue,int upperValue,int nDocuments) throws FileNotFoundException, IOException, ParseException{
        Path path = FileSystems.getDefault().getPath(indexPath);
        Directory dir = FSDirectory.open(path);
        IndexReader ireader = DirectoryReader.open(dir);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        Query query2=IntPoint.newRangeQuery(field,lowerValue,upperValue);
        ScoreDoc[] hits = isearcher.search(query2, nDocuments).scoreDocs;
        ArrayList<Document> resultado=new ArrayList();
        for (int i = 0; i < hits.length; i++) {
           resultado.add(isearcher.doc(hits[i].doc));
        }
        ireader.close();
        dir.close();
        return resultado;
    }
    
    public ArrayList<Document> searchBoolean(String field, String value,int nDocuments) throws FileNotFoundException, IOException, ParseException{
        Path path = FileSystems.getDefault().getPath(indexPath);
        
        Directory dir = FSDirectory.open(path);
        
        IndexReader ireader = DirectoryReader.open(dir);
        IndexSearcher isearcher = new IndexSearcher(ireader);
                
        StringTokenizer tokens = new StringTokenizer(value);
                
        BooleanQuery.Builder boolConstructor = new BooleanQuery.Builder();
                
        String word = tokens.nextToken();
        Query termquery = null;
        if(!word.equals("not")){
            termquery = new TermQuery(new Term(field, word));
            boolConstructor.add(termquery, BooleanClause.Occur.MUST);
        }
        else{
            word = tokens.nextToken();
            termquery = new TermQuery(new Term(field, word));
            boolConstructor.add(termquery, BooleanClause.Occur.MUST_NOT);   
        }
        
        BooleanClause.Occur clause = null;
        while(tokens.hasMoreTokens()){
            word = tokens.nextToken();
            if(word.equals("and")){
                clause = BooleanClause.Occur.MUST;
            }
            else if(word.equals("or")){
                clause = BooleanClause.Occur.SHOULD;
            }
            else if(word.equals("not")){
                clause = BooleanClause.Occur.MUST_NOT;
            }
            else{
                termquery = new TermQuery(new Term(field, word));
                boolConstructor.add(termquery, clause); 
            }
        }
        
        Query query = boolConstructor.build();
        ScoreDoc[] hits = isearcher.search(query, nDocuments).scoreDocs;
        ArrayList<Document> resultado=new ArrayList();
        for (int i = 0; i < hits.length; i++) {
           resultado.add(isearcher.doc(hits[i].doc));
        }
        ireader.close();
        dir.close();
        
        return resultado;
    }
    
    public ArrayList<Document> searchTerm(String field, String value,int nDocuments) throws FileNotFoundException, IOException, ParseException{
        Path path = FileSystems.getDefault().getPath(indexPath);
        
        Directory dir = FSDirectory.open(path);
        
        IndexReader ireader = DirectoryReader.open(dir);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        
        Query query = new TermQuery(new Term(field, value));
        
        ScoreDoc[] hits = isearcher.search(query, nDocuments).scoreDocs;
        ArrayList<Document> resultado=new ArrayList();
        for (int i = 0; i < hits.length; i++) {
           resultado.add(isearcher.doc(hits[i].doc));
        }
        ireader.close();
        dir.close();
        
        return resultado;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        MathSearcher searcher=new MathSearcher("../Index");
        /*ArrayList<Document> hits=searcher.searchIntExact("Año", 2016, 20);
        for (int i = 0; i < hits.size(); i++) {
            Document hitDoc = hits.get(i);
            System.out.println("salida "+hitDoc.get("Autor").toString());
            System.out.println("salida "+hitDoc.toString());
         }*/
        // TODO code application logic here
        
        ArrayList<Document> hits = searcher.searchBoolean("Titulo", "estimation and spectral", 20);
        for (int i = 0; i < hits.size(); i++) {
            Document hitDoc = hits.get(i);
            System.out.println("salida "+hitDoc.get("Autor").toString());
            System.out.println("salida "+hitDoc.toString());
         }
    }
    
}
