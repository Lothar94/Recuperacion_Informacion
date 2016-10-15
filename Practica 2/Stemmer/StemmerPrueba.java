/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stemmerprueba;

import org.tartarus.snowball.ext.spanishStemmer;
import java.util.StringTokenizer;

/**
 *
 * @author jhg
 */
public class StemmerPrueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        spanishStemmer stemmer = new spanishStemmer();
        StringTokenizer st = new StringTokenizer("mio; cade3na, punto,.;", ";,.",true);
        
        System.out.println("Hay un total de: "+st.countTokens()+" tokens.");

        while (st.hasMoreTokens()) {  System.out.println(st.nextToken());   }
        String cadena_larga = "término es una cadena larga para procesar, representa el texo que quiero leer";
        
stemmer.setCurrent("término");
if (stemmer.stem()){
    System.out.println(stemmer.getCurrent());
}
    }
    
}
 
