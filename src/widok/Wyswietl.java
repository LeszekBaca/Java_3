package widok;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Temat: Baza danych pracownikow
 *
 * @author Leszek Baca
 * @version 1.0 
 * Klasa odpowiedzialna za wypisywanie tekstu
 */
public class Wyswietl {
    /**
     * Inicjalizacja obiektu out typu PrintWriter
     */
    PrintWriter out;

    /**
     * Konstruktor klasy Wyswietl
     *
     * @param out typu PrintWriter
     */
    public Wyswietl(PrintWriter out) {
        this.out = out;
    }

    /**
     * Konstruktor bezparametrowy klasy Wyswietl
     */
    public Wyswietl() {
    }

    /**
     * Metoda odpowiedzialna wypisywanie tekstu (używana przy obsłudze przycisku
     * wypisz)
     *
     * @param rs typu ResultSet
     * @throws SQLException w przypadku gdy jest blad polaczenia z baza
     */
    public void wyswietlanie(ResultSet rs) throws SQLException {
        out.printf("|%-3s|%-12s|%-10s|<br/>", "ID", "Nazwisko", "Wyplata");
        out.println("-----------------------------------");
        while (rs.next()) {
            out.printf("<br/>|%-3s", rs.getInt("id"));
            out.printf("|%-12s", rs.getString("nazwisko"));
            out.printf("|%-10s", rs.getDouble("pensja"));
        }
        out.println("<br/>-----------------------------------");
    }

    /**
     * Metoda do wypisywania tekstu
     *
     * @param s typy String
     */
    public void wyswietlanie(String s) {
        out.println(s + "<br/>");
    }

    /**
     * Metoda odpowiedzialna za wypisywanie tesktu dla: dodawania, usuwania,
     * aktualizacji
     *
     * @param i typu calkowitego
     * @return tekst; w przypadku blednego podania metoda zwraca null
     */
    public String wyswietlanieNowe(int i) {
        if (i == 0) {
            return "Nie dodano pracownika";
        }
        if (i == 1) {
            return "Dodano pracownika - ";
        }
        if (i == 2) {
            return " wierszy";
        }
        if (i == 3) {
            return "Nic nie usnieto";
        }
        if (i == 4) {
            return "Usunięto";
        }
        if (i == 5) {
            return "Nie aktualizowano pracownika";
        }
        if (i == 6) {
            return "Aktualizowano pracownika -  ";
        }
        return null;

    }

    /**
     * Medoda odpowiedzialna za wypisywanie tekstu informujacego o bledzie
     */
    public void blad() {
        out.println("Blad - SQL");
    }
}
