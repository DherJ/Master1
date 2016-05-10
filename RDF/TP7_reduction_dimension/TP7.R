library("MASS")
library("lattice")

# Q1
rdfPlotDonnees <- function ()
{
	# Assignation des couleurs pour classe App
	couleur <- rep('red', 3)
	couleur[classe_app == 1] = 'red'
	couleur[classe_app == 2] = 'blue'
	couleur[classe_app == 3] = 'green'

	plot (x_app, col = couleur)

	dev.new()

	# Assignation des couleurs pour classe Test
	couleur2 <- rep('red', 3)
	couleur2[classe_test== 1] = 'red'
	couleur2[classe_test == 2] = 'blue'
	couleur2[classe_test == 3] = 'green'

	plot (x_test, col = couleur2)
}


# Q2
rdfCalculVecteurApp <- function ()
{
	# Calcul de co-variance
	S <- cov (x_app)
	# Inverse de la matrice
	Vp <- eigen (S)
	x_app_ACP <- x_app

	# Produits scalaires
	ScalarProduct_app = x_app %*% Vp$vectors[,1]
	# Normalisation
	ScalarProduct = ScalarProduct_app / (sqrt(sum(Vp$vectors[,1] * Vp$vectors[,1])))

	
	x_app_ACP[,1] <- ScalarProduct * Vp$vectors[1,1]
	x_app_ACP[,2] <- ScalarProduct * Vp$vectors[2,1]

	# Application des couleurs aux points
	points(x_app_ACP[classe_app == 1,], col='red')
	points(x_app_ACP[classe_app == 2,], col='blue')
	points(x_app_ACP[classe_app == 3,], col='green')

	# Affichage de la droite
	pente <- Vp$vectors[2,1] / Vp$vectors[1,1]
	abline (a = 0, b = pente, col = 'blue')
}


# Q3
rdfCalculALD <- function ()
{
	# Calcul de co-variance
	S <- cov (x_app)
	# Inverse de la matrice
	Vp <- eigen (S)
	x_app_ACP <- x_app

	# Produits scalaires
	ScalarProduct_app = x_app %*% Vp$vectors[,1]
	# Normalisation
	ScalarProduct = ScalarProduct_app / (sqrt(sum(Vp$vectors[,1] * Vp$vectors[,1])))

	x_app_ACP[,1] <- ScalarProduct * Vp$vectors[1,1]
	x_app_ACP[,2] <- ScalarProduct * Vp$vectors[2,1]

	x_app_ACP.lda <- lda (ScalarProduct_app, classe_app)
	assigne_app <- predict (x_app_ACP.lda)

	# Règles de décision tirées des données d'apprentissage
	table_classification_app <- table (classe_app, assigne_app$class)
	diag (prop.table (table_classification_app, 1))
	taux_bonne_classif_app <- sum (diag (prop.table (table_classification_app)))
	print (taux_bonne_classif_app)

	# Assignation des couleurs
	couleur <- rep('red', 3)
	couleur[classe_app == 1] = 'red'
	couleur[classe_app == 2] = 'blue'
	couleur[classe_app == 3] = 'green'

	# Et assignations des formes
	shape <- rep(1, 3)
	shape[assigne_app$class==1]=1
	shape[assigne_app$class==2]=2
	shape[assigne_app$class==3]=3

	# Affichage des formes relatives à la décision prise plus haut
	plot (x_app, col = couleur, pch=shape, xlab = "X1", ylab = "X2")

	points(x_app_ACP[classe_app == 1,], col='red')
	points(x_app_ACP[classe_app == 2,], col='blue')
	points(x_app_ACP[classe_app == 3,], col='green')

	# Affichage de la droite
	pente <- Vp$vectors[2,1] / Vp$vectors[1,1]
	abline (a = 0, b = pente, col = 'blue')

	print ("Taux de bonne classification : ")
	print (taux_bonne_classif_app)
	
	print (table_classification_app)
}


# Q4
rdfBonneClassif <- function ()
{
	# Calcul de co-variance
	S <- cov (x_app)
	# Inverse de la matrice
	Vp <- eigen (S)
	x_app_ACP <- x_app

	# On récuère les données de test
	x_test_ACP <- x_test

	# Produits scalaires pour apprentissages et données
	ScalarProduct_app = x_app %*% Vp$vectors[,1]
	ScalarProduct_test = x_test %*% Vp$vectors[,1]

	# Normalisation des deux sets de données
	ScalarProduct = ScalarProduct_app / (sqrt(sum(Vp$vectors[,1] * Vp$vectors[,1])))
	ScalarProduct2 = ScalarProduct_test / (sqrt(sum(Vp$vectors[,1] * Vp$vectors[,1])))

	x_app_ACP[,1] <- ScalarProduct * Vp$vectors[1,1]
	x_app_ACP[,2] <- ScalarProduct * Vp$vectors[2,1]
	x_test_ACP[,1] <- ScalarProduct * Vp$vectors[1,1]
	x_test_ACP[,2] <- ScalarProduct * Vp$vectors[2,1]

	
	x_app_ACP.lda <- lda (ScalarProduct_app, classe_app)

	# On utilise les nouvelles données (test) pour la projection des points
	assigne_app <- predict (x_app_ACP.lda, newdata=ScalarProduct_test)

	# Règles de décision tirées des données d'apprentissage
	table_classification_app <- table (classe_app, assigne_app$class)
	diag (prop.table (table_classification_app, 1))
	taux_bonne_classif_app <- sum (diag (prop.table (table_classification_app)))
	print (taux_bonne_classif_app)

	# Assignation des couleurs
	couleur <- rep('red', 3)
	couleur[classe_app == 1] = 'red'
	couleur[classe_app == 2] = 'blue'
	couleur[classe_app == 3] = 'green'

	# Et assignations des formes
	shape <- rep(1, 3)
	shape[assigne_app$class==1]=1
	shape[assigne_app$class==2]=2
	shape[assigne_app$class==3]=3

	# Affichage des formes relatives à la décision prise plus haut
	plot (x_app, col = couleur, pch=shape, xlab = "X1", ylab = "X2")

	points(x_app_ACP[classe_app == 1,], col='red')
	points(x_app_ACP[classe_app == 2,], col='blue')
	points(x_app_ACP[classe_app == 3,], col='green')

	# Affichage de la droite
	pente <- Vp$vectors[2,1] / Vp$vectors[1,1]
	abline (a = 0, b = pente, col = 'blue')

	print ("Taux de bonne classification : ")
	print (taux_bonne_classif_app)

	print (table_classification_app)
}
