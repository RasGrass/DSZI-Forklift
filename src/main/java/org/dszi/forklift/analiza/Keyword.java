/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dszi.forklift.analiza;

/**
 * Ta klasa reprezentuje jakies słowo kluczowe, np. "zawieź".
 * 
 * @author Damian
 */
public class Keyword {
    
    private String keyword;
    private int type;
    private String[] synonyms;
    private int number;
    
    private int synonyms_count;
    
    //typy
    static public final int INSTRUCTION = 0;
    static public final int PARAMETER = 1;
    static public final int UNIQUE = 2;
    static public final int OBJECT_COLOR = 3;
    static public final int OBJECT_TYPE = 4;
    static public final int OBJECT_NAME = 5;
    static public final int NUMBER = 6;
    
    public Keyword(String keyword, int type)
    {
        this.keyword = keyword;
        this.type = type;
        this.synonyms = new String[10];
        synonyms_count = 0;
    }
    
    public String toString()
    {
        return (this.type != Keyword.NUMBER ? this.keyword : Integer.toString(this.getNumber()));
    }
    
    public boolean check(String word)
    {
        word = this.trim(word).toLowerCase();
        
        if(this.type == Keyword.NUMBER)
            return this.isNumeric(word);
        
        String[] keywords = this.synonyms;
        keywords[synonyms_count] = this.keyword;
        
        int n = synonyms_count + 1;
        
        if(keyword.toLowerCase().startsWith("obiekt"))
            return word.toLowerCase().equals(keyword.toLowerCase());
        
        for(int i = 0; i < n; i++)
        {
            int permissible = (Math.min(word.length(), this.keyword.length()) - 1) / 3 + 1; // dopuszczalny błąd
            if(this.Levenshtein(keywords[i], word) < permissible)
                return true;
        }
        
        return false;
    }
    // porpawic tak, żeby "Opone" przechodziło
    
    private boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?"); // pozniej zrobić: może być jeszcze kg
    }
    
    public void addSynonym(String synonym)
    {
        this.synonyms[synonyms_count++] = synonym;
    }
    
    public int getType()
    {
        return this.type;
    }
    
    public String getKeyword()
    {
        return this.keyword;
    }
    
    public boolean isValue()
    {
        return (this.getType() > Keyword.UNIQUE);
    }
    
    public int getNumber()
    {
        return this.number;
    }
    
    public void setNumber(int number)
    {
        this.number = number;
    }
    
    public void setNumber(String number)
    {
        this.number = Integer.parseInt(this.trim(number));
    }
    
    private String trim(String str)
    {
        return str.trim().replaceFirst("^[?:!.,;]+", "").replaceAll("[?:!.,;]+$", "");
    }
    
    /*
     * Metoda mierzy podobienstwo dwoch ciagow znakow.
     * Czym mniejsza zwracana liczba tym większe podobieństwo.
     */
    private int Levenshtein(String str1, String str2)
    {
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];
 
        for(int i = 0; i <= str1.length(); i++)
            distance[i][0] = i;
        
        for(int j = 1; j <= str2.length(); j++)
            distance[0][j] = j;
 
        for(int i = 1; i <= str1.length(); i++)
            for(int j = 1; j <= str2.length(); j++)
                distance[i][j] = this.min(
                    distance[i - 1][j] + 1,
                    distance[i][j - 1] + 1,
                    distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1)
                );
        
        return distance[str1.length()][str2.length()]; 
    }
    
    private int min(int a, int b, int c)
    {
        return Math.min(a, Math.min(b, c));
    }
    
}
