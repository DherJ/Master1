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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet qui affiche l'ensemble des auteurs présents dans la base de donnée
 * @author jérôme
 */
public class AllAuthorServlet extends HttpServlet {

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
                out.println("<title>Author List</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<center>");
                List<BookEntity> books = tool.findAll();
                List<String> authors = new ArrayList<String>();
                for (BookEntity book : books) {
                    if (!authors.contains(book.getAuthor())) {
                        authors.add(book.getAuthor());
                    }
                }
                for (String author : authors) {
                    out.println("<br>" + author + "<br>");
                }
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
