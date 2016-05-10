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
        <title>Add a Book</title>
    </head>
    <body>  
        <h1> Book added </h1>
        <%
          String titre = request.getParameter("title");
          String auteur = request.getParameter("author");
          String anneParution = request.getParameter("anneParution");
        %>
        
        <center>
            <p>Titre  : <%=titre%> </p> <BR>
            <p>Auteur : <%=auteur%> </p> <BR>
            <p>Annee  : <%=anneParution%> </p> <BR>
            <BR>
            <BR>
            <BR>
            <button type="button" style="background:cadetblue; border:solid 1px black;
                        cursor:pointer; border:solid 1px black;"
                        onclick="window.location='index.jsp';">
                    <img src="icon3.png" alt="Accueil" />
            </button>
        </center>
    </body>
</html>