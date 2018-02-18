package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Operacje;
import widok.Wyswietl;

/**
 * Temat: Baza danych pracownikow
 *
 * @author Leszek Baca
 * @version 1.0 
 * Klasa odpowiedzialna za utworzenie serwletu oraz operacjach CRUD na bazie
 */
public class NewServlet extends HttpServlet {

    /**
     * Inicjalizacja obiektu con typu Connection
     */
    Connection con;
    /**
     * Inicjalizacja obiektu operacje typu Operacje
     */
    Operacje operacje;
    /**
     * Uwtorzenie zmiennej blad typu calkowitego i przypisanie jej wartosci -1
     */
    int blad = -1;

    /**
     * Konstruktor bezparametrowy klasy NewServlet
     */
    public NewServlet() {
    }

    /**
     * Metoda odpowiedzialna za polaczenie  serwera oraz utworzenie tabeli Pracownicy
     *
     * @param config typu ServletConfig
     * @throws ServletException if a servlet-specific error occurs
     */
    public void init(ServletConfig config) throws ServletException {
        try {
            super.init(config);
            Class.forName(config.getInitParameter("sterownik"));

            con = DriverManager.getConnection(config.getInitParameter("polaczenie"),
                    config.getInitParameter("user"), config.getInitParameter("password"));


            operacje = new Operacje(con);
            Statement statement = con.createStatement();

            ResultSet rs = con.getMetaData().getTables(null, null, "PRACOWNICY", null);
            if (!rs.next()) {
                statement.executeUpdate("CREATE TABLE Pracownicy "
                        + "(id INTEGER, nazwisko VARCHAR(50), "
                        + "pensja DOUBLE)");
                System.out.println("Table created");
            }
        } catch (SQLException ex) {
            blad = 7;

        } catch (ClassNotFoundException ex) {
            blad = 6;

        } catch (Exception e) {
            blad = 5;

        }
    }

    /**
     * Metoda odpowiedzialna za zamkniecie polaczenia z baza danych
     */
    public void destroy() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    /**
     * Processes requests for both HTTP GET and POST methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Wyswietl w = new Wyswietl(response.getWriter());
        if (blad == 7) {
            w.wyswietlanie("SQL exception");
        } else if (blad == 6) {
            w.wyswietlanie("ClassNotFound exception");
        } else if (blad == 5) {
            w.wyswietlanie("Another exception");
        } else {
            if ((request.getParameter("dodaj")) != null) {
                try {
                    w.wyswietlanie(operacje.dodawanie(request, response));
                } catch (SQLException ex) {
                    getServletContext().getRequestDispatcher("/Error").include(request, response);
                }
            }
            if ((request.getParameter("usun")) != null) {
                try {
                    w.wyswietlanie(operacje.usuwanie(request, response));
                } catch (SQLException ex) {
                    getServletContext().getRequestDispatcher("/Error").include(request, response);
                }
            }
            if ((request.getParameter("wypisz")) != null) {
                try {
                    w.wyswietlanie(operacje.odczyt(request, response));
                } catch (SQLException ex) {
                    getServletContext().getRequestDispatcher("/Error").include(request, response);
                }
            }
            if ((request.getParameter("aktualizuj")) != null) {
                try {

                    w.wyswietlanie(operacje.aktualizowanie(request, response));
                } catch (SQLException ex) {
                    getServletContext().getRequestDispatcher("/Error").include(request, response);

                }
            }
        }
    }

    /**
     * Handles the HTTP GET method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
