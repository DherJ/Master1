/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import ejb.BookEntity;
import ejb.BookEntityFacadeLocalItf;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet qui affiche les résultats d'une requête de recherche de livre demandée par un client
 * @author jérôme
 */
public class SearchBookServlet extends HttpServlet {

    @EJB
    BookEntityFacadeLocalItf tool;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int date = 0;
        String title = "";
        String author = "";
        try {
                title = request.getParameter("titre");
                author = request.getParameter("auteur");
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet SearchBook</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<section>");
                out.println("<article>");
                List<BookEntity> books = tool.findAll();
                for (BookEntity book : books) {
                    if (book.getAuthor().toLowerCase().contains(author.toLowerCase()) && book.getTitle().toLowerCase().contains(title.toLowerCase()) && (book.getDate() == date || date == 0)) {
                        out.println("<table>");
                        out.println("<tr><td><b>Title: </b></td><td><b>" + book.getTitle() + "</b></td></b></tr>");
                        out.println("<tr><td>Author: </td><td>" + book.getAuthor() + "</td></tr>");
                        out.println("<tr><td>Date: </td><td>" + book.getDate() + "</td></tr>");
                        out.println("</table>");
                        out.println("<br><br>");
                    }
                }
                out.println("<button type=\"button\" style=\"background:cadetblue; border:solid 1px black;\n" +
"                        cursor:pointer; border:solid 1px black;\"\n" +
"                        onclick=\"window.location='index.jsp';\">\n" +
"                    <img src=\"icon3.png\" alt=\"Acceuil\" />\n" +
"                </button>");
                out.println("</body>");
                out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
