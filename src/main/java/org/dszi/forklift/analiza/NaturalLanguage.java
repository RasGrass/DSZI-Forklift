/*
 * Na razie się nie sugerujcie tą klasą, bo będzie wyglądać inaczej.
 */
package org.dszi.forklift.analiza;

import java.util.*;

import java.io.*;
import org.dszi.forklift.Criteria;
import org.dszi.forklift.GameLogic;
import org.dszi.forklift.Storehouse;
import org.dszi.forklift.Task;

/**
 *
 * @author Damian
 */
public class NaturalLanguage {

    static private Map<String, String> answers;
    static private boolean answers_loaded = false;
    static private Keyword[] keywords; // zamienic na arrayliste
    static private boolean keywords_loaded = false;
    static private int keywords_count;
    static private String[] inputWords;
    static private int inputLength;
    static private Keyword nameKeyword;
    static private int process = 0;
    static private Criteria criteria;
    static private Criteria criteria2;
    static private boolean choiceFirst;
    static public final int DELETE_CHOICE = 1;
    static public final int MOVE_CHOICE = 2;
    static public final int REPLACE_CHOICE = 3;
    static public final int ADD_CHOICE = 4;

    /*
     * metoda, która przyjmuje jako argument tekst i zwraca odpowiedz bota
     * 
     */
    static public String interpret(String input) {
        String output = "xyz";

        NaturalLanguage.loadData();

        //input = input.toLowerCase();

        inputWords = input.split(" ");

        ArrayList<Keyword> found_keywords;

        // znajdowanie slow kluczowych
        inputLength = inputWords.length;
        found_keywords = findKeywords(inputWords);

        if (process != 0) {
            return executeProcess(found_keywords);
        }

        // znajdowanie polecenia
        Keyword instruction = null;
        for (int i = 0; i < found_keywords.size(); i++) {
            if (found_keywords.get(i).getType() == Keyword.INSTRUCTION) {
                instruction = found_keywords.get(i);
                break;
            }
        }

        if (instruction == null) {
            return ">> Nie zrozumialem polecenia.";
        }

        output = NaturalLanguage.executeInstruction(instruction, found_keywords);

        return " >> " + output;
    }

    static private ArrayList<Keyword> findKeywords(String[] words) {
        ArrayList<Keyword> found_keywords = new ArrayList<Keyword>();

        int n = words.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < keywords_count; j++) {
                if (keywords[j].check(inputWords[i])) {
                    Keyword k = keywords[j];

                    // pobieranie liczby
                    if (k.getType() == Keyword.NUMBER) {
                        k = new Keyword("number", Keyword.NUMBER);
                        k.setNumber(inputWords[i]);
                    }

                    found_keywords.add(k);
                    break;

                    // zrobic zeby wybieralo najblizsze
                }
            }
        }

        return found_keywords;
    }

    static private String executeInstruction(Keyword instruction, ArrayList<Keyword> found_keywords) {
        String output;

        if (instruction.getKeyword().equals("znajdz")) {
            ArrayList<org.dszi.forklift.Object> found;
            Criteria c = NaturalLanguage.findCriteria(found_keywords);

            found = Storehouse.findByCriteria(c);

            if (found.isEmpty()) {
                return "Nie znalazłem takiego obiektu.";
            }

            output = "Oto znalezione obiekty:\n\n";
            for (int i = 0; i < found.size(); i++) {
                output = output + found.get(i);
            }

            return output;
        }

        if (instruction.getKeyword().equals("usun")) {
            Criteria c = NaturalLanguage.findCriteria(found_keywords);
            return deleteByCriteria(c, found_keywords);
        }

        if (instruction.getKeyword().equals("zamien")) {
            ArrayList<Keyword> part1 = new ArrayList<Keyword>();
            ArrayList<Keyword> part2 = new ArrayList<Keyword>();

            int i;
            for (i = 0; i < found_keywords.size(); i++) {
                if (found_keywords.get(i).getKeyword().equals("z")) {
                    break;
                }
            }

            if (i == found_keywords.size()) {
                return "Nie rozumiem, jakie obiekty mam zamienić.";
            }

            for (int j = 0; j < i; j++) {
                part1.add(found_keywords.get(j));
            }

            for (int j = i + 1; j < found_keywords.size(); j++) {
                part2.add(found_keywords.get(j));
            }

            Criteria c1 = NaturalLanguage.findCriteria(part1);
            Criteria c2 = NaturalLanguage.findCriteria(part2);

            return replaceByCriteria(c1, c2, found_keywords);
        }

        if (instruction.getKeyword().equals("przenies")) {
            ArrayList<Keyword> part1 = new ArrayList<Keyword>();
            ArrayList<Keyword> part2 = new ArrayList<Keyword>();

            int i;
            for (i = 0; i < found_keywords.size(); i++) {
                if (found_keywords.get(i).getKeyword().equals("na")) {
                    break;
                }
            }

            if (i == found_keywords.size()) {
                return "Nie rozumiem, jakie obiekty mam zamienić.";
            }

            for (int j = 0; j < i; j++) {
                part1.add(found_keywords.get(j));
            }

            for (int j = i + 1; j < found_keywords.size(); j++) {
                part2.add(found_keywords.get(j));
            }

            Criteria c1 = NaturalLanguage.findCriteria(part1);
            Criteria c2 = NaturalLanguage.findCriteria(part2);

            return moveByCriteria(c1, c2, found_keywords);
        }

        if (instruction.getKeyword().equals("stworz")) {
            Criteria c = NaturalLanguage.findCriteria(found_keywords);
            return addByCriteria(c, found_keywords);
        }

        return "Nie zrozumialem polecenia.";
    }

    static private String executeProcess(ArrayList<Keyword> found_keywords) {
        if (findKeyword("exit", found_keywords) != -1) {
            process = 0;
            return "Ok, zrezygnowałeś z wykonania tej operacji.";
        }

        if (process == DELETE_CHOICE) {
            Criteria c = criteria;
            c.addCritera(findCriteria(found_keywords));
            return deleteByCriteria(c, found_keywords);
        }

        if (process == MOVE_CHOICE) {
            Criteria c1 = criteria;
            Criteria c2 = criteria2;
            c1.addCritera(findCriteria(found_keywords));
            return moveByCriteria(c1, c2, found_keywords);
        }

        if (process == ADD_CHOICE) {
            Criteria c = criteria;
            c.addCritera(findCriteria(found_keywords));
            return addByCriteria(c, found_keywords);
        }

        if (process == REPLACE_CHOICE) {
            Criteria c1 = criteria;
            Criteria c2 = criteria2;

            if (choiceFirst) {
                c1.addCritera(findCriteria(found_keywords));
            } else {
                c2.addCritera(findCriteria(found_keywords));
            }

            return replaceByCriteria(c1, c2, found_keywords);
        }

        return "Wystąpił błąd w działaniu programu.";
    }

    static private String findName(ArrayList<Keyword> found_keywords) {
        int position = findKeyword("name", found_keywords);

        if (position != -1) {
            return NaturalLanguage.trim(inputWords[position + 1]);
        }

        return null;
    }

    static private int findKeyword(String keyword, ArrayList<Keyword> found_keywords) {
        Keyword k = null;

        for (int i = 0; i < keywords_count; i++) {
            if (keywords[i].getKeyword().equals(keyword)) {
                k = keywords[i];
                break;
            }
        }

        if (k == null) {
            return -1;
        }

        for (int i = 0; i < inputLength; i++) {
            if (k.check(inputWords[i])) {
                return i;
            }
        }

        return -1;
    }

    static private String replaceByCriteria(Criteria c1, Criteria c2, ArrayList<Keyword> found_keywords) {
        String output = "";

        ArrayList<org.dszi.forklift.Object> found1 = Storehouse.findByCriteria(c1);
        ArrayList<org.dszi.forklift.Object> found2 = Storehouse.findByCriteria(c2);

        if (found1.size() > 1 || found2.size() > 1) {
            ArrayList<org.dszi.forklift.Object> found;

            process = REPLACE_CHOICE;
            criteria = c1;
            criteria2 = c2;
            choiceFirst = (found1.size() > 1);
            found = (choiceFirst ? found1 : found2);

            System.out.println("rozmiar found: " + found.size());

            for (int i = 0; i < found.size(); i++) {
                output = output + found.get(i);
            }

            String which = (choiceFirst ? "pierwszego" : "drugiego");
            output = output + "Oto obiekty, które pasuja do " + which + " opisu. O który z nich Ci chodzi?";
            return output;
        }

        process = 0;

        if (found1.size() == 0 || found2.size() == 0) {
            return "Jeden z podanych opisów obiektów nie pasuje do żadnego obiektu.";
        }

        //Storehouse.replaceObjects(found1.get(0), found2.get(0));
        GameLogic.addTask(Task.ACTION_TYPE_REPLACE, found1.get(0), found2.get(0));

        output = "Oto obiekty, ktore zostaną zamienione: \n\n";
        output = output + found1.get(0);
        output = output + found2.get(0);
        return output;
    }

    static private String addByCriteria(Criteria c, ArrayList<Keyword> found_keywords) {
        // jak jest nazwa w pierwszym to jeszcze nie dziala - poprawic!!!

        String output = "";

        ArrayList<String> needed = new ArrayList<String>();
        int weight;
        String name, color, type;

        if (c.getName() == null) {
            c.setName(NaturalLanguage.findName(found_keywords));
        }

        if (null == c.getName()) {
            needed.add("nazwa");
        }

        if (null == c.getColor()) {
            needed.add("kolor");
        }

        if (null == c.getType()) {
            needed.add("typ");
        }

        if (-1 == c.getWeight()) {
            needed.add("waga");
        }

        boolean random = (findKeyword("random", found_keywords) != -1);

        if (needed.isEmpty() || random) {
            name = c.getName();
            color = c.getColor();
            type = c.getType();
            weight = c.getWeight();

            if (random) {
                if (null == c.getName()) {
                    name = randomName();
                }

                if (null == c.getColor()) {
                    color = randomColor();
                }

                if (null == c.getType()) {
                    type = randomType();
                }

                if (-1 == c.getWeight()) {
                    weight = randomWeight();
                }
            }

            org.dszi.forklift.Object o = new org.dszi.forklift.Object(name, weight, color, type);

            int rackNumber = c.getRackNumber();
            int shelfNumber = c.getShelfNumber();

            if (rackNumber != -1 && shelfNumber != -1) {
                // Forklift.getStorehouse().addObjectSpecifibercally(o, rackNumber, shelfNumber);
                GameLogic.addTask(Task.ACTION_TYPE_ADD, o, rackNumber, shelfNumber);
            } else //Forklift.getStorehouse().addObjectAnywhere(o);
            {
                GameLogic.addTask(Task.ACTION_TYPE_ADDANYWHERE, o);
            }

            process = 0;
            return "Obiekt zostal pomyslnie dodany.";
        }

        process = ADD_CHOICE;
        criteria = c;

        output = "Musisz podac jeszcze nastepujace informacje: ";
        output = output + needed.get(0);
        for (int i = 1; i < needed.size(); i++) {
            output = output + ", " + needed.get(i);
        }

        output = output + ". Jeżeli nie chcesz podawać jakichś danych, to użyj słowa 'losowo' przy dodawaniu obiektu.";

        return output;
    }

    static private String moveByCriteria(Criteria c1, Criteria c2, ArrayList<Keyword> found_keywords) {
        ArrayList<org.dszi.forklift.Object> found = Storehouse.findByCriteria(c1);
        String output = "";

        if (found.isEmpty()) {
            process = 0;
            return "Nie wiem, jaki obiekt mam przenieść ponieważ podany opis nie pasuje do żadnego obiektu.";
        }

        if (found.size() > 1) {
            process = MOVE_CHOICE;
            criteria = c1;
            criteria2 = c2;

            for (int i = 0; i < found.size(); i++) {
                output = output + found.get(i);
            }

            output = output + "\n\n" + "Powyżej znajdują się obiekty, które spełniają podane przez Ciebie kryteria. Który z nich mam przenieść?";

            return output;
        }

        process = 0;

        int rackNumber = c2.getRackNumber();
        int shelfNumber = c2.getShelfNumber();

        if (rackNumber == -1 && shelfNumber == -1) {
            return "Nie wiem na jaką półkę i jaki regał mam przenieść obiekt.";
        }

        if (rackNumber == -1) {
            rackNumber = found.get(0).getRackNumber();
        }

        if (shelfNumber == -1) {
            shelfNumber = found.get(0).getShelfNumber();
        }

        //Forklift.getStorehouse().replaceObject(rackNumber, shelfNumber, found.get(0));
        GameLogic.addTask(Task.ACTION_TYPE_MOVE, found.get(0), rackNumber, shelfNumber);

        output = "Poniższy obiekt został przeniesiony na półkę " + rackNumber + " regalu " + shelfNumber + "\n\n";
        output = output + found.toString();
        return output;
    }

    static private String deleteByCriteria(Criteria c, ArrayList<Keyword> found_keywords) {
        ArrayList<org.dszi.forklift.Object> found;
        String output = "";
        boolean all = findKeyword("all", found_keywords) != -1;

        if (!all && c.isEmpty()) {
            return "Nie podałeś żadnych kryteriów, więc nie wiem jakie obiekty chcesz usunąć.";
        }

        found = Storehouse.findByCriteria(c);

        if (found.isEmpty()) {
            return "Nie znalazlem obiektu, ktory spelnia okreslone kryteria";
        }

        if (found.size() == 1) {
            //Storehouse.deleteObject(found.get(0));
            GameLogic.addTask(Task.ACTION_TYPE_DELETE, found.get(0));
            process = 0;
            return "Obiekt zostanie usuniety.";
        }

        for (int i = 0; i < found.size(); i++) {
            output = output + found.get(i);
        }

        if (findKeyword("all", found_keywords) != -1) {
            for (int i = 0; i < found.size(); i++) //Storehouse.deleteObject(found.get(i));
            {
                GameLogic.addTask(Task.ACTION_TYPE_DELETE, found.get(i));
            }

            output = output + "\n\n" + "Powyższe obiekty zostały usunięte.";
            return output;
        }

        process = DELETE_CHOICE;
        criteria = c;

        output = output + "\n\n" + "Powyżej znajdują się obiekty, które spełniają podane przez Ciebie kryteria. Który z nich mam usunąć?";
        return output;
    }

    static private String trim(String str) {
        return str.trim().replaceFirst("^[?:!.,;]+", "").replaceAll("[?:!.,;]+$", "");
    }

    static private Criteria findCriteria(ArrayList<Keyword> found_keywords) {
        Criteria c = new Criteria();

        for (int i = 0; i < found_keywords.size(); i++) {
            Keyword k = found_keywords.get(i);
            Keyword previous, next;

            if (k.isValue()) {
                if (k.getType() == Keyword.OBJECT_NAME) {
                    c.setName(k.getKeyword());
                    continue;
                }

                if (k.getType() == Keyword.OBJECT_COLOR) {
                    c.setColor(k.getKeyword());
                    continue;
                }

                if (k.getType() == Keyword.OBJECT_TYPE) {
                    c.setType(k.getKeyword());
                    continue;
                }
            }

            System.out.println("found_keywords");
            for(int m = 0; m < found_keywords.size(); m++)
            {
                System.out.println(found_keywords.get(m));
            }
            
            if (k.getType() == Keyword.NUMBER) {
                int value = k.getNumber();

                previous = next = null;

                if (i > 0) {
                    previous = found_keywords.get(i - 1);
                }

                if (i + 1 < found_keywords.size()) {
                    next = found_keywords.get(i + 1);
                }

                if (null != previous && previous.getType() == Keyword.PARAMETER) {
                    if (previous.getKeyword().equals("id")) {
                        c.setId(value);
                    }

                    if (previous.getKeyword().equals("weight")) {
                        c.setWeight(value);
                    }

                    if (previous.getKeyword().equals("rack")) {
                        c.setRackNumber(value);
                    }

                    if (previous.getKeyword().equals("shelf")) {
                        c.setShelfNumber(value);
                    }
                }

                if (null != next && next.getType() == Keyword.PARAMETER) {
                    if (next.getKeyword().equals("id")) {
                        c.setId(value);
                    }

                    if (next.getKeyword().equals("weight")) {
                        c.setWeight(value);
                    }

                    if (next.getKeyword().equals("rack")) {
                        c.setRackNumber(value);
                    }

                    if (next.getKeyword().equals("shelf")) {
                        c.setShelfNumber(value);
                    }
                }
            }
        }

        return c;
    }

    static public void objectAdded(String name) {
        NaturalLanguage.loadData();
        keywords[keywords_count++] = new Keyword(name, Keyword.OBJECT_NAME);
    }

    static public ArrayList<org.dszi.forklift.Object> findObject(String description) {
        String words[] = description.split(" ");
        ArrayList<Keyword> found_keywords = findKeywords(words);
        Criteria c = NaturalLanguage.findCriteria(found_keywords);
        return Storehouse.findByCriteria(c);
    }

    static private String randomName() {
        Random r = new Random();
        int n = r.nextInt(100) + 1;
        return ("Obiekt" + Integer.toString(n));
    }

    static private String randomColor() {
        String[] colors = {"czerwony", "zielony", "niebieski", "czarny", "żółty"};

        Random r = new Random();
        int n = r.nextInt(4) + 1;
        return colors[n];
    }

    static private String randomType() {
        String[] types = {"Prostokąt", "Kwadrat", "Koło", "Trójkąt", "Gwiazda", "Hipopotam"};
        Random r = new Random();
        int n = r.nextInt(4) + 1;
        return types[n];
    }

    static private int randomWeight() {
        Random r = new Random();
        return (r.nextInt(100) + 1);
    }

    static private void loadData() {
        if (!answers_loaded) {
            answers = new HashMap<String, String>();

            answers.put("bot", "jestem botem");
            answers_loaded = true;
        }

        if (!keywords_loaded) {
            try {
                NaturalLanguage.loadKeywords();
                keywords_loaded = true;
            } catch (IOException e) {
                System.out.println("Nie znaleziono pliku tekstowego");
            }
        }
    }

    static private void loadKeywords() throws IOException {
        
        FileReader fileReader = new FileReader("./res/dictionary.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int type = -1;
        int i = 0;
        keywords = new Keyword[2000];

        String textLine = bufferedReader.readLine();
        do {

            if (!textLine.toUpperCase().equals(textLine)) {
                String[] words = textLine.split(",");
                keywords[i] = new Keyword(words[0].trim(), type);

                // dodawanie synonimów
                int n = words.length;
                for (int k = 0; k < n; k++) {
                    keywords[i].addSynonym(words[k].trim());
                }

                if (keywords[i].getKeyword().equals("name")) {
                    nameKeyword = keywords[i];
                }

                i++;
            } else {
                type++;
            }

            textLine = bufferedReader.readLine();
        } while (textLine != null);

        bufferedReader.close();

        keywords_count = i;

        keywords[keywords_count++] = new Keyword("number", Keyword.NUMBER);
    }
}
