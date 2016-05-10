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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet qui gère les actions pour l'ajout d'un livre dans la base de donnée
 * @author jérôme
 */
public class AddBookServlet extends HttpServlet {

    @EJB
    BookEntityFacadeLocalItf tool;
        
    @Resource(mappedName = "jms/bookFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/book")
    private Queue queue;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws javax.jms.JMSException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JMSException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String year = request.getParameter("anneParution");
        
        BookEntity newBook = new BookEntity(title, author, year);
        try {
                Connection connection = connectionFactory.createConnection();
                Session conSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer messageProducer = conSession.createProducer(queue);

                ObjectMessage message = conSession.createObjectMessage();
                message.setStringProperty("type", "book");
                message.setObject(newBook);
                
                messageProducer.send(message);
                messageProducer.close();
                connection.close();
                        
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Livre ajouté</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Livre ajouté</h1>");
                out.println("<center>");
                out.println("<h2>récapitulatif : " + title + " " + author + " " + year + " a été ajouté</h2>");
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
        this.getServletContext().getRequestDispatcher( "/WEB-INF/AddBook.jsp" ).forward( request, response );
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
        try {
            processRequest(request, response);
        } catch (JMSException ex) {
            Logger.getLogger(AddBookServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
