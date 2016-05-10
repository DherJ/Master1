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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet qui affiche l'ensemble des livres enregistrés dans la base de donnée
 * @author jérôme
 */
public class AllLivreServlet extends HttpServlet {

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
            try {
                response.setContentType("text/html;charset=UTF-8");
                ServletContext sc = getServletContext();
                PrintWriter out = response.getWriter();
            try  {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Book List</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<center>");
                out.println("<section>\n");
                out.println("\n<article>");
                List<BookEntity> books = tool.findAll();
                if(!books.isEmpty()) {
                    for (BookEntity book : books) {
                        out.println("<table border=1px solid black>");
                        out.println("<tr>");
                        out.println("<td><b>Title: </b></td><td><b>" + book.getTitle() + "</b></td></b>");
                        out.println("</tr>");
                        out.println("<tr><td>Author: </td><td>" + book.getAuthor() + "</td></tr>");
                        out.println("<tr><td>Year: </td><td>" + book.getDate() + "</td></tr>");
                        out.println("<form method=post action=\"AllLivreServlet\"><INPUT type=\"hidden\" name=\"titre\" value=\"" + book.getTitle()+"\"><INPUT type=\"submit\" name=\"panier\" value=\"Ajouter au  Panier\"></form>");
                        out.println("</table>");
                        out.println("<br><br>");
                    }
                } else {
                     out.println("Aucuns livres enregistrés!");
                }
                out.println("</article>\n");
                out.println("<button type=\"button\" style=\"background:cadetblue; border:solid 1px black;\n" +
"                        cursor:pointer; border:solid 1px black;\"\n" +
"                        onclick=\"window.location='index.jsp';\">\n" +
"                    <img src=\"icon3.png\" alt=\"Acceuil\" />\n" +
"                </button>");
                out.println("</center>");
                out.println("</body>");
                out.println("</html>");
                out.close();
            } catch (Exception e) {
                 this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (Exception e) {
                 this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
        /*
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Liste des livres</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Liste des Livres</h1>");
            List<BookEntity> liste = tool.findAll();
            System.out.println("nb ook : " + liste.size());
            out.println("<center>");
            out.print("<ul>");
            for(BookEntity l:liste)
                out.println("<li>" + l.toString() + "</li>");
            out.print("</ul>");
            out.println("<button type=\"button\" style=\"background:cadetblue; border:solid 1px black;\n" +
"                        cursor:pointer; border:solid 1px black;\"\n" +
"                        onclick=\"window.location='../index.jsp';\">\n" +
"                    <img src=\"icon3.png\" alt=\"Acceuil\" />\n" +
"                </button>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
            
        } finally {
            out.close();
        }
    }
        */
        
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
