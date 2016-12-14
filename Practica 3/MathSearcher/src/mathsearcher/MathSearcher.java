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
import java.util.Iterator;
import java.util.StringTokenizer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.facet.DrillDownQuery;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.taxonomy.FastTaxonomyFacetCounts;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
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
    private String taxoPath;

    public MathSearcher(String indexPath, String taxoPath) {
        this.indexPath = indexPath;
        this.taxoPath = taxoPath;
    }
    private Query BuilderStringQuery(String field, String value) throws FileNotFoundException, IOException, ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser(field, analyzer);
        Query query = parser.parse(value);
        return query;
    }

    private Query BuilderIntExactQuery(String field, int value) throws FileNotFoundException, IOException, ParseException {
        Query query2 = IntPoint.newExactQuery(field, value);
        return query2;
    }

    private Query BuilderIntRangeQuery(String field, String lowerValue, String upperValue) throws FileNotFoundException, IOException, ParseException {  
        Query query =null;
        if(!lowerValue.equals("")){
            if(!upperValue.equals("")){
               query = IntPoint.newRangeQuery(field, Integer.parseInt(lowerValue), Integer.parseInt(upperValue)); 
            }else{
                query = IntPoint.newRangeQuery(field, Integer.parseInt(lowerValue), Integer.MAX_VALUE);
            }
        }else{
            if(!upperValue.equals("")){
               query = IntPoint.newRangeQuery(field, Integer.MIN_VALUE, Integer.parseInt(upperValue)); 
            }
        }
        
        return query;
    }

    private Query BuilderBooleanQuery(String field, String value) throws FileNotFoundException, IOException, ParseException {

        StringTokenizer tokens = new StringTokenizer(value);

        BooleanQuery.Builder boolConstructor = new BooleanQuery.Builder();

        String word = tokens.nextToken();
        Query termquery = null;
        if (!word.equals("not")) {
            termquery = new TermQuery(new Term(field, word));
            boolConstructor.add(termquery, BooleanClause.Occur.MUST);
        } else {
            word = tokens.nextToken();
            termquery = new TermQuery(new Term(field, word));
            boolConstructor.add(termquery, BooleanClause.Occur.MUST_NOT);
        }

        BooleanClause.Occur clause = null;
        while (tokens.hasMoreTokens()) {
            word = tokens.nextToken();
            if (word.equals("and")) {
                clause = BooleanClause.Occur.MUST;
            } else if (word.equals("or")) {
                clause = BooleanClause.Occur.SHOULD;
            } else if (word.equals("not")) {
                clause = BooleanClause.Occur.MUST_NOT;
            } else {
                termquery = new TermQuery(new Term(field, word));
                boolConstructor.add(termquery, clause);
            }
        }

        Query query = boolConstructor.build();

        return query;
    }

    private Query BuilderTermQuery(String field, String value) throws FileNotFoundException, IOException, ParseException {
        Query query = new TermQuery(new Term(field, value));
        return query;
    }
    
    public ArrayList<Document> search(String field, String value,String[] facetas, String[] values,String fieldRange,ArrayList<String> range, int nDocuments) throws FileNotFoundException, IOException, ParseException {
        Path path = FileSystems.getDefault().getPath(indexPath);
        Path taxo = FileSystems.getDefault().getPath(taxoPath);
        Directory dir = FSDirectory.open(path);
        Directory taxo_dir = FSDirectory.open(taxo);
        
        IndexReader iReader = DirectoryReader.open(dir);
        TaxonomyReader taxoReader = new DirectoryTaxonomyReader(taxo_dir);
        IndexSearcher isearcher = new IndexSearcher(iReader);
        
        Query baseQuery=null;
        
        if(!value.equals("")){
            if (field == "Año" || field == "Página inicio" || field == "Página fin"){
                int intireValue = Integer.parseInt(value);
                baseQuery = this.BuilderIntExactQuery(field, intireValue);
            }
            else 
                baseQuery = this.BuilderBooleanQuery(field,value);

            if(!range.get(0).equals("") || !range.get(1).equals("")){
                BooleanQuery.Builder boolConstructor = new BooleanQuery.Builder();
                boolConstructor.add(BuilderIntRangeQuery(fieldRange, range.get(0), range.get(1)), BooleanClause.Occur.MUST);
                boolConstructor.add(baseQuery, BooleanClause.Occur.MUST);
                baseQuery = boolConstructor.build();
            }
        }
        else{
            if(!range.get(0).equals("") || !range.get(1).equals("")){
                baseQuery = BuilderIntRangeQuery(fieldRange, range.get(0), range.get(1));
            }
        }
        
        

        DrillDownQuery query = new DrillDownQuery(new FacetsConfig(), baseQuery);
        for(int i =0 ; i<facetas.length;i++){
            if(!values[i].equals("todos")){
                System.out.println(facetas[i]+": "+values[i]);
                query.add(facetas[i], values[i]);
            }
        }
        FacetsCollector fc = new FacetsCollector();
        ScoreDoc[] hits = FacetsCollector.search(isearcher, query, nDocuments, fc).scoreDocs;

        ArrayList<Document> result = new ArrayList<>();

        for (int i = 0; i < hits.length; i++) {
            result.add(isearcher.doc(hits[i].doc));
        }

        iReader.close();
        taxoReader.close();
        dir.close();
        return result;
    }
    
    public ArrayList<FacetResult> searchFacets(Query q, int ndocs) throws IOException {
        Path path = FileSystems.getDefault().getPath(indexPath);
        Path taxo = FileSystems.getDefault().getPath(taxoPath);
        Directory dir = FSDirectory.open(path);
        Directory taxo_dir = FSDirectory.open(taxo);

        IndexReader iReader = DirectoryReader.open(dir);
        IndexSearcher isearcher = new IndexSearcher(iReader);

        TaxonomyReader taxoReader = new DirectoryTaxonomyReader(taxo_dir);

        FacetsCollector fc = new FacetsCollector();
        FacetsCollector.search(isearcher, q, ndocs, fc);
        ArrayList<FacetResult> results = new ArrayList<>();

        Facets facets = new FastTaxonomyFacetCounts(taxoReader, new FacetsConfig(), fc);
        results.add(facets.getTopChildren(ndocs, "Idioma"));
        results.add(facets.getTopChildren(ndocs, "Tipo de documento"));

        iReader.close();
        taxoReader.close();
        return results;
    }
    
    public ArrayList<String> getFields() throws IOException{
        Path path = FileSystems.getDefault().getPath(indexPath);
        Directory dir = FSDirectory.open(path);
        ArrayList<String> fields = new ArrayList();
        
        IndexReader iReader = DirectoryReader.open(dir);
                
        Fields f = MultiFields.getFields(iReader);
        
        Iterator itr = f.iterator();
        itr.next();
        
        while(itr.hasNext())
            fields.add((String) itr.next());
        
        return fields;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
    }

}
