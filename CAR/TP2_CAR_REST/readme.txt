/*----------------------------------------------------------*/
/*                    TP 2 CAR                       */
/*                 Dhersin Jérôme                  */
/*              Knapik Christopher                */
/*---------------------------------------------------------*/

Ce projet fournit un framework simple [1] pour l'execution de programmes
accessibles en tant que ressources REST.

Les ressources se programment comment des classes annotees avec l'API JAX-RS.
Voir un example avec la classe car.HelloWorldResource.

Pour pouvoir etre prises en compte par le framework, les ressources doivent etre
declarees dans la classe com.example.config.AppConfig, methode jaxRsServer().
La declaration se fait en utilisant l'instruction suivante :

	serviceBeans.add(new _classe_de_la_ressource());
	par exemple serviceBeans.add(new HelloWorldResource());
	
Autant de ressources que necessaire peuvent etre declarees.

/-----------------------------------------------------------------------------------------------/

Le lancement du framework se fait en invoquant la methode Main de la classe
main.Starter

Une fois lancees, les ressources sont accessibles, par exemple via un
navigateur, en chargeant l' URL :

	http://localhost:8080/rest/api/
	

Pour pouvoir accéder aux ressources REST :
lancer le server ftp FTP.jar

Sur la page d'acceuil saisir les champs du formulaire de connexion avec les valeurs suivantes :
	login : test
	pass  : test

Un login pour un user anonymous est également implémentée :
	login : anonymous
	pass  : anonymous
	
Le programme permet de naviguer entre dossier, de download, supprimer des fichiers.

Architecture de l'application :

	package com.conf
		|	
			Classe App_Conf
	package com.html
		|
		    Classe HTMLGenerator
	package com.model
		|   
			Classe User
	package com.rs
		|	
			Classe JaxRsApiApplication
		|	
			Classe PasserelleRest
	package main
		|
			Classe Starter
			
