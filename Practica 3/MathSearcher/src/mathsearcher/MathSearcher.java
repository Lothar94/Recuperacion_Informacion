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
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Lothar Soto Palma
 * @author Daniel López García
 * @author Iván Calle Gil
 * @author José Carlos Entrena Jiménez
 */
public class MathSearcher{

    private String indexPath;
    private String taxoPath;
    ArrayList<FacetResult> facetResults;
    
    public MathSearcher(String indexPath, String taxoPath) {
        this.indexPath = indexPath;
        this.taxoPath = taxoPath;
        facetResults = new ArrayList<>();
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
    
    private Query BuilderPhraseQuery(int distance, String field, String value){
        
        StringTokenizer tokens = new StringTokenizer(value);
        PhraseQuery.Builder phraseConstructor = new PhraseQuery.Builder();
        
        while(tokens.hasMoreTokens()){
            String word = tokens.nextToken();
            phraseConstructor.add(new Term(field, word));
        }
        
        phraseConstructor.setSlop(distance);
        Query query = phraseConstructor.build();

        return query;
    }

    private Query BuilderBooleanQuery(String field, String value) throws FileNotFoundException, IOException, ParseException {
        BooleanQuery.Builder boolConstructor = new BooleanQuery.Builder();
        Boolean first = false;
            StringTokenizer tokens = new StringTokenizer(value);

            String word = tokens.nextToken();
            Query termquery = null;
                     
            if (!word.equals("not")) {
                termquery = new TermQuery(new Term(field, word));
                if(tokens.hasMoreTokens()){
                    word = tokens.nextToken();
                    if(word.equals("or"))
                        boolConstructor.add(termquery, BooleanClause.Occur.SHOULD);
                    else
                        boolConstructor.add(termquery, BooleanClause.Occur.MUST);
                } else
                    boolConstructor.add(termquery, BooleanClause.Occur.MUST);
            } else {
                word = tokens.nextToken();
                termquery = new TermQuery(new Term(field, word));
                boolConstructor.add(termquery, BooleanClause.Occur.MUST_NOT);
            }

            BooleanClause.Occur clause = BooleanClause.Occur.MUST;
            while (tokens.hasMoreTokens()) {
                if(first){
                    word = tokens.nextToken();
                }
                switch (word) {
                    case "and":
                        clause = BooleanClause.Occur.MUST;
                        break;
                    case "or":
                        clause = BooleanClause.Occur.SHOULD;
                        break;
                    case "not":
                        clause = BooleanClause.Occur.MUST_NOT;
                        break;
                    default:
                        termquery = new TermQuery(new Term(field, word));
                        boolConstructor.add(termquery, clause);
                        clause = BooleanClause.Occur.MUST;
                        break;
                }
                first = true;
            }
        Query query = boolConstructor.build();

        return query;
    }
    
    private Query BuilderAllFieldsQuery(String value) throws FileNotFoundException, IOException, ParseException {
        BooleanQuery.Builder boolConstructor = new BooleanQuery.Builder();
        boolConstructor.add(this.BuilderBooleanQuery("Autor",value), BooleanClause.Occur.SHOULD);
        boolConstructor.add(this.BuilderBooleanQuery("Titulo",value), BooleanClause.Occur.SHOULD);
        boolConstructor.add(this.BuilderBooleanQuery("Fuente",value), BooleanClause.Occur.SHOULD);
        boolConstructor.add(this.BuilderBooleanQuery("Abstract",value), BooleanClause.Occur.SHOULD);
        boolConstructor.add(this.BuilderBooleanQuery("Palabras clave autor",value), BooleanClause.Occur.SHOULD);
        int valueInt;
        try {  
            valueInt=Integer.parseInt(value);
            boolConstructor.add(this.BuilderIntExactQuery("Año",valueInt), BooleanClause.Occur.SHOULD);
            boolConstructor.add(this.BuilderIntExactQuery("Página inicio",valueInt), BooleanClause.Occur.SHOULD);
            boolConstructor.add(this.BuilderIntExactQuery("Página fin",valueInt), BooleanClause.Occur.SHOULD);
        }catch(Exception e){}
        finally{
            Query query = boolConstructor.build();
            return query;
        }
        
    }
    
    public ArrayList<Document> search(int distance, ArrayList<String> field, ArrayList<String> value, String[] facetas, String[] values,String fieldRange,ArrayList<String> range, int nDocuments) throws FileNotFoundException, IOException, ParseException {

        Path path = FileSystems.getDefault().getPath(indexPath);
        Path taxo = FileSystems.getDefault().getPath(taxoPath);
        Directory dir = FSDirectory.open(path);
        Directory taxo_dir = FSDirectory.open(taxo);
        
        IndexReader iReader = DirectoryReader.open(dir);
        TaxonomyReader taxoReader = new DirectoryTaxonomyReader(taxo_dir);
        IndexSearcher isearcher = new IndexSearcher(iReader);
        
        for(int i = 0; i < value.size() ; i++)
            value.set(i, value.get(i).toLowerCase());
        
        Query baseQuery = null;
        
        // Distinguimos casos. Primero, vemos que la consulta no es vacía
        if(!value.get(0).equals("")){
            BooleanQuery.Builder boolConstructor = new BooleanQuery.Builder();
            Boolean first=true;
            for(int i = 0; i < value.size() ; i++){
                // Si la distancia es mayor o igual que cero, tenemos una consulta por proximidad o exacta (distancia == 0) y solo al primer campo
                if (distance >= 0 && first){
                    baseQuery = this.BuilderPhraseQuery(distance, field.get(i), value.get(i));
                    first=false;
                }
                // En otro caso, distinguimos entre búsqueda numérica o booleana dependiendo del campo
                else{   
                    // Numérica
                    if (field.get(i) == "Año" || field.get(i) == "Página inicio" || field.get(i) == "Página fin"){
                        int intireValue = Integer.parseInt(value.get(i));
                        baseQuery = this.BuilderIntExactQuery(field.get(i), intireValue);

                    }
                    // Todos los campos
                    else if(field.get(i) == "Todos"){
                        baseQuery = this.BuilderAllFieldsQuery(value.get(i));
                    }
                    // Booleana
                    else
                        baseQuery = this.BuilderBooleanQuery(field.get(i),value.get(i));
                   
                }
                boolConstructor.add(baseQuery, BooleanClause.Occur.MUST);
            }        
            if(!range.get(0).equals("") || !range.get(1).equals("")){
                boolConstructor.add(BuilderIntRangeQuery(fieldRange, range.get(0), range.get(1)), BooleanClause.Occur.MUST);
            }
            baseQuery = boolConstructor.build();
        }
        else{
            if(!range.get(0).equals("") || !range.get(1).equals("")){
                baseQuery = BuilderIntRangeQuery(fieldRange, range.get(0), range.get(1));
            }
        }
        
        DrillDownQuery query = new DrillDownQuery(new FacetsConfig(), baseQuery);
        
        for(int i =0 ; i<facetas.length;i++){
            if(!values[i].equals("todos"))
                query.add(facetas[i], values[i]);
        }
        // Obtenemos aciertos y facetas
        FacetsCollector fc = new FacetsCollector();
        ScoreDoc[] hits = FacetsCollector.search(isearcher, query, nDocuments, fc).scoreDocs;
        Facets facets = new FastTaxonomyFacetCounts(taxoReader, new FacetsConfig(), fc);
                
        ArrayList<Document> result = new ArrayList<>();
        facetResults.clear();
        
        // Añadimos resultados
        for (int i = 0; i < hits.length; i++) 
            result.add(isearcher.doc(hits[i].doc));
        
        // Añadimos facetas
        for (int i = 0; i < facetas.length ; i++)
            facetResults.add(facets.getTopChildren(nDocuments,facetas[i]));
        
        iReader.close();
        taxoReader.close();
        dir.close();
        return result;
    }
    
    
    public ArrayList<String> getFields() throws IOException{
        Path path = FileSystems.getDefault().getPath(indexPath);
        Directory dir = FSDirectory.open(path);
        ArrayList<String> fields = new ArrayList();
        
        IndexReader iReader = DirectoryReader.open(dir);
                
        Fields f = MultiFields.getFields(iReader);
        
        Iterator itr = f.iterator();
        itr.next();
        fields.add("Todos");
        while(itr.hasNext())
            fields.add((String) itr.next());
        
        fields.add("Año");
        fields.add("Página inicio");
        fields.add("Página fin");
        
        return fields;
    }
    
    public ArrayList<FacetResult> getFacets(){
        return facetResults;
    }
}
