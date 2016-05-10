<!DOCTYPE html>

<html>
    <head>
        <title>Acceuil</title>
        <style type="text/css">
            body {
                color: purple;
                background-color: #7FA3A3;
                background-image:url(images/background.png);
                background-repeat: no-repeat;
                background-position:top;
                margin: 53px;
                width: 100%;
                margin: 0; padding: 0;
            }
            footer {
                position: absolute;
                margin: 700px 0px 0px 254px;
                width: 770px;
                height: 50px;
                border: 0px #000000 solid;
                clear:both; /*Place le pied en element flottant*/ 
                display : table-row;
            }
        </style>
    </head>
    <body>
        <div id="body">
            <center>
                <br>
                <br>
                <h1 align="center" style="color:#ff3333; font-style:italic; font-family:cursive; font-size:60px"> <b> E-Library </b> </h1>
                <br>
                <br>
                <br>
                <br>
                <button type="button" style="background:cadetblue; border:solid 1px black;
                        cursor:pointer; border:solid 1px black;"
                        onclick="window.location='http://localhost:8080/HelloWorld_rest/InitServlet';">
                    <img src="icon3.png" alt="Initialiser les donn&eacute;es" />
                </button> 
                <br>
                <br>
                <button type="button" style="background:cadetblue; border:solid 1px black;
                        cursor:pointer; border:solid 1px black;"
                        onclick="window.location='http://localhost:8080/HelloWorld_rest/AddBookForm.html';">
                    <img src="icon3.png" alt="Ajouter un livre" />
                </button> 
                <br>
                <br>
                <button type="button" style="background:cadetblue; border:solid 1px black;
                        cursor:pointer; border:solid 1px black;"
                        onclick="window.location='http://localhost:8080/HelloWorld_rest/SearchBookForm.html';">
                    <img src="icon3.png" alt="Rechercher un livre" />
                </button> 
                <br>
                <br>
                <button type="button" style="background:cadetblue; border:solid 1px black;
                        cursor:pointer; border:solid 1px black;"
                        onclick="window.location='http://localhost:8080/HelloWorld_rest/AllLivreServlet';">
                    <img src="icon3.png" alt="Liste des livres" />
                </button> 
                <br>
                <br>
                <button type="button" style="background:cadetblue; border:solid 1px black;
                        cursor:pointer; border:solid 1px black;"
                        onclick="window.location='http://localhost:8080/HelloWorld_rest/AllAuthorServlet';">
                    <img src="icon3.png" alt="Liste des auteurs" />
                </button> 
                <br>
                <br>
                <button type="button" style="background:cadetblue; border:solid 1px black;
                        cursor:pointer; border:solid 1px black;"
                        onclick="window.location='http://localhost:8080/HelloWorld_rest/ViewPanier';">
                    <img src="icon3.png" alt="Voir le panier" />
                </button> 
                <br>
                <br>
                <br>
                <br>
            </center>
            <div id="footer">
                <p style="color:black; font-style:oblique; font-family:fantasy; font-size:20px"> <b> Authors : Dhersin J&eacute;r&ocirc;me & Knapik Christopher </b> </p>
                <p style="color:black; font-style:oblique; font-family:fantasy; font-size:20px"> <b> Master 1 Informatique Lille </b> </p>
            </div> 
        </div>
    </body>
</html>