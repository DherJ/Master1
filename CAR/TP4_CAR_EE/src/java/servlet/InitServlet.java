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
 * Servlet qui initialise la base de donnée en y enregistrant quelques livres par défaut
 * @author jérôme
 */
public class InitServlet extends HttpServlet {
    
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println(out.toString());
        try {
            List<BookEntity> books = tool.findAll();
            if (books.isEmpty()) {
                tool.create(new BookEntity("L'instant présent", "Guillaume Musso", "2015"));
                tool.create(new BookEntity("After - After", "Anna Todd", "2015"));
                tool.create(new BookEntity("Temps glaciaires", "Fred Vargas", "2015"));
                tool.create(new BookEntity("Six ans déjà", "Harlan Coben", "2015"));
                tool.create(new BookEntity("Les nuits de Reykjavik", "Arnaldur Indridason", "2015"));
                tool.create(new BookEntity("N'éteins pas la lumière", "Bernard Minier", "2015"));
                System.out.println("Default books added");
            } else {
                for(BookEntity book : books) {
                    tool.remove(book);
                }
            }
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet d'initialisation des données</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            try{
                out.println("<h1>la Biliotheque a été initialisée</h1>");
            }catch(Exception e){
                out.println("<h1>Erreur!!!!!!</h1>");
                //this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            }
            out.println("<button type=\"button\" style=\"background:cadetblue; border:solid 1px black;\n" +
"                        cursor:pointer; border:solid 1px black;\"\n" +
"                        onclick=\"window.location='index.jsp';\">\n" +
"                    <img src=\"icon3.png\" alt=\"Acceuil\" />\n" +
"                </button>");
            out.println("</center>");
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
