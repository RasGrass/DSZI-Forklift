/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dszi.forklift.analiza;

/**
 * To jest klasa, która służy mi tylko do testowania innych klas, które piszę
 * 
 * @author Damian
 */
public class NLTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String input[] = new String[99];
        int n = 9;
        
        input[0] = "Znajdź obiekt w kolorze pomaranczowym";
        input[1] = "znajdz";
        
        NaturalLanguage.objectAdded("skrzynia");
        input[2] = "Znajdź skrzynie";
        
        input[3] = "Usuń obiekt, którego id jest równe 4 .";
        input[4] = "Zamień obiekt o id = 5 z czerwoną skrzynią.";
        input[5] = "Odszukaj pomaranczowy obiekt.";
        input[6] = "Znajdz kwadrat.";
        input[7] = "Znajdz obiekt o wadze 5 kg";
        
        NaturalLanguage.objectAdded("Opona");
        input[8] = "Znajdź opone";
        
        for(int i = 0; i < n; i++)
            System.out.println(NaturalLanguage.interpret(input[i]));
    }
}
