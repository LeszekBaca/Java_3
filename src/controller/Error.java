package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import widok.Wyswietl;

/**
 * Temat: Baza danych pracownikow
 *
 * @author Leszek Baca
 * @version 1.0 
 * Klasa odpowiedzialna za obługę błędu 
 * 
 */
public class Error extends HttpServlet {

    /**
     * Utworzenie obiektu wyswietl typu Wyswietl
     */
    Wyswietl wyswietl = new Wyswietl();

    /**
     * Konstruktor bezparametrowy klasy Error
     */
    public Error() {
    }

    /**
     * Metoda odpowiedzialna za obsługę rządania http typu Get. Metoda wyświetla
     * tekst o bledzie
     *
     * @param req typu HttpServletRequest
     * @param resp typu HttpServletResponse
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain; charset=ISO-8859-2");
        PrintWriter out = resp.getWriter();
        wyswietl.blad();
    }

    /**
     * Metoda odpowiedzialna za obsługę rządania http typu Post
     *
     * @param req typu HttpServletRequest
     * @param resp typu HttpServletResponse
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
