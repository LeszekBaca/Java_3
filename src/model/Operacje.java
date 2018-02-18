package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import widok.Wyswietl;

/**
 * Temat: Baza danych pracownikow
 * @author Leszek Baca
 * @version 1.0 
 * Klasa odpowiedzialna za wykonywanie operacji CRUD na bazie
 */
public class Operacje {
    /**
     * Inicjalizacja obiektu con typu Connection
     */
    Connection con;
    /**
     * Utworzenie obiektu wyswietl typu Wyswietl
     */
    Wyswietl wyswietl = new Wyswietl();

    /**
     * Konstruktor klasy Operacje
     *
     * @param con typu Connection
     */
    public Operacje(Connection con) {
        this.con = con;
    }

    /**
     *
     * Metoda odpowiedzialna za dodawnie rekordow do bazy
     * @param req typu HttpServletRequest
     * @param resp typu HttpServletResponse
     * @return tekst typy String
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException w przypadku bledu z baza
     */
    public String dodawanie(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException, SQLException {

        try {

            String identyfikator = req.getParameter("id");
            int id = Integer.parseInt(identyfikator);


            String nazwisko = req.getParameter("nazwisko");

            String pensja = req.getParameter("pensja");
            double wyplata = Double.parseDouble(pensja);


            if (!(identyfikator.length() == 0 || nazwisko.length() == 0 || pensja.length() == 0)) {

                Statement statement = con.createStatement();

                int rowCount = statement.executeUpdate("INSERT INTO Pracownicy VALUES (" + id + ", '" + nazwisko + "', " + wyplata + ")");

                if (rowCount == 0) {
                    return wyswietl.wyswietlanieNowe(0);
                } else {
                    return wyswietl.wyswietlanieNowe(1) + rowCount + wyswietl.wyswietlanieNowe(2);
                }

            }
        } catch (NumberFormatException e) {
            resp.sendError(resp.SC_BAD_REQUEST, "Poprawnie gdy 3 pola są uzupelnione."
                    + " id - liczby calkowite"
                    + "nazwisko - tekst, pensja - liczby calkowite lub zmiennoprzecinkowe");
        }
        return "";
    }

    /**
     * Metoda odpowiedzialna za usuwanie elementow z bazy
     * @param req typu HttpServletRequest
     * @param resp typu HttpServletResponse
     * @return tekst typy String
     * @throws SQLException w przypadku bledu z baza
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs

     */
    public String usuwanie(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException, SQLException {
        try {
            String identyfikator = req.getParameter("id");
            int id = Integer.parseInt(identyfikator);

            if (!(identyfikator.length() == 0)) {

                Statement statement = con.createStatement();

                int rowCount = statement.executeUpdate("DELETE FROM Pracownicy WHERE id = " + id + "");

                if (rowCount == 0) {
                    return wyswietl.wyswietlanieNowe(3);
                } else {
                    return wyswietl.wyswietlanieNowe(4) + rowCount + wyswietl.wyswietlanieNowe(2);
                }

            }
        } catch (NumberFormatException e) {
            resp.sendError(resp.SC_BAD_REQUEST, "Uzupełnij pole id.");

        }
        return "";
    }

    /**
     * Metoda odpowiedzialna za aktualizowanie rekordu w bazie
     * @param req typu HttpServletRequest
     * @param resp typu HttpServletResponse
     * @return tekst typy String.
     * @throws SQLException w przypadku bledu z baza
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs

     */
    public String aktualizowanie(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException, SQLException {

        int i = 0;
        try {

            String identyfikator = req.getParameter("id");
            int id = Integer.parseInt(identyfikator);

            String nazwisko = req.getParameter("nazwisko");

            String pensja = req.getParameter("pensja");
            double wyplata = Double.parseDouble(pensja);

            if (!(identyfikator.length() == 0 || nazwisko.length() == 0 || pensja.length() == 0)) {

                Statement statement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

                ResultSet rs = statement.executeQuery("SELECT * FROM Pracownicy WHERE id = " + id);

                while (rs.next()) {
                    i++;
                    rs.updateInt("id", id);
                    rs.updateString("nazwisko", nazwisko);
                    rs.updateDouble("pensja", wyplata);
                    rs.updateRow();
                }

                if (i == 0) {
                    return wyswietl.wyswietlanieNowe(5);
                } else {
                    return wyswietl.wyswietlanieNowe(6) + i + wyswietl.wyswietlanieNowe(2);
                }

            }
        } catch (NumberFormatException e) {

            resp.sendError(resp.SC_BAD_REQUEST, "Poprawnie gdy 3 pola są uzupelnione."
                    + " id - liczby calkowite"
                    + "nazwisko - tekst, pensja - liczby calkowite lub zmiennoprzecinkowe");
        }
        return "";
    }

    /**
     * Metoda odpowiedzialna za odczytywanie rekordow z bazy
     * @param req typu HttpServletRequest
     * @param resp typu HttpServletResponse
     * @return tekst typy String
     * @throws SQLException w przypadku bledu z baza
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs

     */
    public ResultSet odczyt(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException, SQLException {
        Statement statement = con.createStatement();
        return statement.executeQuery("SELECT * FROM Pracownicy");

    }
}
