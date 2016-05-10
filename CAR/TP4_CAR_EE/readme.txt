/*----------------------------------------------------------*/
/*                    TP 4 CAR                              */
/*                 Dhersin Jérôme                           */
/*              Knapik Christopher                          */
/*----------------------------------------------------------*/

Cette application est une e-lybrary qui propose au client de gérer une base de donnée local correspondant à un ensemble de livres.

Cette API se décompose en 2 sections :

	- Une section traitement
		qui correspond à la gestion de la base de donnée par Bean, donc qui permet l'ajout de livres ou la recherche d'un livre dans cette base de donnée.
	- Une section vue prise en charge par les servlets 
		qui affichent les résultats des requêtes envoyées par les clients HTTP
	

/-----------------------------------------------------------------------------------------------/

URL d'accueil de l'api : http://localhost:8080/HelloWorld_rest/AccueilServlet
	

L'application permet :

	- d'initialiser la base de donnée
	- d'ajouter un livre
	- de lister l'ensemble des livres de la base de donnée
	- de lister l'ensemble des auteurs
	- de rechercher un livre
	

/-----------------------------------------------------------------------------------------------/

Architecture de l'application :

	package ejb Contient la classe entité pour la base de données ainsi que les services Bean de l'application (recherche d'auteur, de livres, ect)
		|	
		|	Classe AbstractFacade           -> contient les méthodes appelables (les services de l'api web) par le client
		|	Classe BookEntity               -> correspond a l'objet stocké dans la base de donnée
		|	Class  BookEntityFacade         -> correspond à notre session Bean
		|	Classe BookEntityFacadeLocalItf -> interface contenant la définition des méthodes apelables à distance
		|	Classe NewMessageBean           -> classe de contrôle pour les transfert d'objets dans la base de donnée. (utilisée pour un ajout dans la base)

	package servlet 
		|	
		|	Classe AddBookServlet -> servlet pour ajouter un livre dans la base de donnée
		|	Classe AllAuthorServlet -> servlet pour lister tous les auteurs de la base de donnée
		|	Classe AllLivreServlet -> servlet pour lister tous les livres de la base de donnée
		|	Classe InitServlet -> servlet pour initialiser la base de donnée avec des livres par défaut
		|	Classe SearchBookServlet -> servlet pour rechercher un livre dans la base de donnée
		|	Classe AccueilServlet -> servlet pour afficher la page d'accueil de l'application (charge index.jsp)
			

/-----------------------------------------------------------------------------------------------/

Exceptions : 

ServletException pour les erreurs encourues par le lancement d'une servlet
IOException     pour les appels à des fichiers du projet
JMSException  pour les erreurs liées au Message Bean Driven pour l'ajout des livres dans la base de donnée



/-----------------------------------------------------------------------------------------------/

Code sample : 

Méthode onMessage du gestionnaire Message Bean Driven :

    public void onMessage(Message message) {
        ObjectMessage objectMessageRecu;
        try {
            if (message instanceof ObjectMessage) {
                objectMessageRecu = (ObjectMessage) message;
                if (objectMessageRecu.propertyExists("type")) {
                    String type = objectMessageRecu.getStringProperty("type");

                    if (type != null) {
                        System.out.println(type);
                        if (type.equals("book")) {
                            BookEntity book = (BookEntity) objectMessageRecu.getObject();
                            save(book);
                            System.out.println("Book has been added");
                        }
                    }
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }
	
	
Méthode sendMessage du gestionnaire Message Bean Driven :

private void sendJMSMessageToBook(Object messageData) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = bookFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(book);
            messageProducer.send(createJMSMessageForjmsBook(session, messageData));
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
	
	
Requête pour obtenir tous les éléments de la base de donnée : 

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
	

Servlet d'initialisation des données dans la base : 

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
	