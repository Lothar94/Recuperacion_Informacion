/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textindexer;

import java.io.IOException;

/**
 *
 * @author lot94
 */
public class TextIndexer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String text = new String();
        TextReader rd = new TextReader();
        text = rd.read("./Quijote/Cap1.txt");
        System.out.println(text);
    }
    
}
