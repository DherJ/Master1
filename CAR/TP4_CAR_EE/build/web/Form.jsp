<%--
    Document   : Form
    Created on : 6 avr. 2015, 21:06:08
    Author     : jérôme
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formulaire recherche Book</title>
    </head>
    <body>  
       
        <%
          String titre = request.getParameter("titre");
          String auteur = request.getParameter("auteur");
          String anneParution = request.getParameter("anneParution");
        %>
  
        <center>
            <p>Titre  : <%=titre%> </p>
            <p>Auteur : <%=auteur%> </p>
            <p>Annee  : <%=anneParution%> </p>
        </center>
            
    </body>
</html>