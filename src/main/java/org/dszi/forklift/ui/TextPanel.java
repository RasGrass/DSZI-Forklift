package org.dszi.forklift.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author RasGrass
 */
public class TextPanel extends JPanel {

	private final JTextPane help = new JTextPane();

	public TextPanel() {
		super();

		help.setEditable(false);
		help.setContentType("text/html");
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.LIGHT_GRAY);
		help.setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout());

		help.setText(""
				+ "<h2>1. Polecenie \"znajdź\". </h2>"
				+ "Instrukcja wypisuje obiekty spełniające podane kryteria." + "<br>"
				+ "<b>Sposób użycia:" + "<br>"
				+ "znajdź [opis obiektu]</b>" + "<br>"
				+ "Opis obiektu to po prostu tekst zawierający informacje o obiekcie. Bot działa w taki sposób, że po prostu znajduje słowa kluczowe i na ich podstawie układa kryteria wyszukiwania. Zatem kolejność, w jakiej się wpisze informacje, i to, co się wstawi pomiędzy słowa kluczowe, nie ma znaczenia. Tylko w przypadku podania jakiejś liczby bot ocenia, czego ta liczba dotyczy na podstawie słów kluczowych które znajdują się w pobliżu i wtedy kolejność ma znaczenie." + "<br>"
				+ "<b>Przykładowe opisy obiektu:</b>" + "<br>"
				+ "<i>żółty słoik,</i>" + "<br>"
				+ "<i>kwadrat o kolorze czerwonym</i>" + "<br>"
				+ "<i>obiekt o nazwie Skrzynia</i>" + "<br>"
				+ "<i>obiekt ktorego id to 3, a jego nazwa to Opona</i>" + "<br>"
				+ "<i>kwadrat o wadze 15</i>" + "<br>"
				+ "itd." + "<br>"
				+ "</i>Zamiast \"znajdź\" można wpisać \"odszukaj\", \"poszukaj\", \"wypisz\"; miejsce gdzie wstawi się to słowo w tekście nie ma znaczenia." + "<br>"
				+ "<h2>2. Polecenie \"usuń\".</h2>"
				+ "Polecenie usuwa obiekt spełniający podane kryteria." + "<br>"
				+ "<b>usuń [opis obiektu]</b>" + "<br>"
				+ "Opis obiektu to to samo, co w przypadku polecenia \"znajdź\". W przypadku tego polecenia do opisu obiektu można też dodać słowo \"wszystkie\", \"każdy\" albo jakieś ich odmiany i wtedy zostaną usunięte wszystkie obiekty które spełniają kryteria. W przeciwnym przypadku bot dopytuje się, który obiekt ma zostać usunięty. Przykład:" + "<br>"
				+ "<i>\"Usuń wszystkie kwadraty\".</i>" + "<br>"
				+ "<h2>3. Polecenie \"zamień\".</h2>"
				+ "Polecenie zamienia ze sobą dwa obiekty spełniające podane obiekty." + "<br>"
				+ "<b>Sposób użycia:</b>" + "<br>"
				+ "<b>zamień [opis pierwszego obiektu] z [opis drugiego obiektu]</b>" + "<br>"
				+ "Opis obiektu to to samo, co w przypadku polecenia \"znajdź\".Zamiast słowa \"z\" można użyć słowa \"i\"." + "<br>"
				+ "<h2>4. Polecenie \"przenies\".</h2>"
				+ "<b>Sposób użycia:</b>" + "<br>"
				+ "<b>przenieś [opis obiektu] na [gdzie]</b>" + "<br>"
				+ "Opis obiektu to to samo, co w przypadku polecenia \"znajdź\".W miejsce [gdzie] należy wpisać numer półki albo numer magazynu albo oba numery, np. w taki sposób:" + "<br>"
				+ "<i>przenieś czerwony kwadrat na polke 3 magazynu 2.</i>" + "<br>"
				+ "<h2>5. Polecenie \"dodaj\". </h2>"
				+ "<b>dodaj [opis obiektu]</b>" + "<br>"
				+ "Opis obiektu to to samo, co w przypadku polecenia \"znajdź\".Dodatkowo do opisu obiektu można dopisać słowo \"losowo\" albo \"obojetnie\" i wtedy właściwości obiektu, które nie zostały podane zostaną wypełnione losowo." + "<br>"
				+ "Przykład:" + "<br>"
				+ "<i>dodaj obiekt o nazwie Damian i wadze 15</i>, reszta wlasciwosci moze byc losowo" + "<br>"
				+ "Powyższe polecenie doda obiekt o nazwie \"Damian\" i wadze 15, reszta właściwości będzie ustawiona losowo." + "<br>"
				+ "<h2>Ogólne uwagi:</h2>"
				+ "1. Lepiej wpisywać bez polskich znaków, wyjątkiem jest słowo \"koło\", które lepiej wpisywać z polskimi znakami, bojak się napisze \"kolo\", to bot traktuje je najczęściej jako słowo \"kolor\" (ze względu na podobieństwo tych wyrazów)." + "<br>"
				+ "");
		JScrollPane CommandLineScrollField = new JScrollPane(help);
		CommandLineScrollField.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(CommandLineScrollField);
		this.setVisible(true);
	}

}
