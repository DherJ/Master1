package servlet;

import ejb.BookEntityFacade;
import ejb.Panier;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet qui affiche le contenu du panier du client
 * @author jerome
 */
public class ViewPanier extends HttpServlet {
    
        
    @EJB
    BookEntityFacade tool;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession s= request.getSession();
        if(s.getAttribute("panier")==null)
            s.setAttribute("panier", new Panier());
        Panier p=(Panier) s.getAttribute("panier");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Panier</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println(p.getHtmlCode());
            out.println("<form method=post action=\"ViewPanier\"><INPUT type=\"submit\" name=\"reset\" value=\"Reset le Panier\"></form>");
            out.println("<form method=post action=\"ViewPanier\"><INPUT type=\"submit\" name=\"commit\" value=\"Valider le Panier\"></form>");
            out.println("<br><h2>Historique des paniers</h2>");
            List<Panier> l=tool.getAllPanier();
            out.print("<ul>");
            for(Panier ptmp:l) {
                out.print("<li>"+ptmp.getHtmlCode()+"</li>");
                out.print("</ul>");
            }
            out.println("<button type=\"button\" style=\"background:cadetblue; border:solid 1px black;\n" +
"                 cursor:pointer; border:solid 1px black;\"\n" +
"                 onclick=\"window.location='index.jsp';\">\n" +
"                 <img src=\"icon3.png\" alt=\"Acceuil\" />\n" +
"                 </button>");
            out.println();
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession s= request.getSession();
        if(s.getAttribute("panier")==null)
            s.setAttribute("panier", new Panier());
        Panier p=(Panier) s.getAttribute("panier");
        if(request.getParameter("reset")!=null)
            p.reset();
        if(request.getParameter("commit")!=null){
            tool.ajouter(p);
            s.setAttribute("panier", new Panier());
        }
        processRequest(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
