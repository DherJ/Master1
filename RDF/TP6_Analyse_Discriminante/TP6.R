# Yassine BADACHE
# Jérôme DHERSIN
# Fichier contenant les macros
# correspondant aux réponses du TP6


# Macro 1.1 et 1.2
# Macro permettant d'afficher les données de test
# et les données d'apprentissage. Elle prend en
# paramètre le nom du fichier à charger, et affiche
# les deux fenêtres, avec les trois classes de
# différentes couleurs: 1 = red, 2 = blue, 3 = green
#
# -> Décommenter les 'print' pour vérifier la
# véracité des résultats obtenus

library("MASS")
library("lattice")

rdfPlotDonnees <- function (data_filename)
{
	load(data_filename)
	# Couleurs
	couleur <- rep('red', n_app)
	couleur[classe_app == 1] = 'red'
	couleur[classe_app == 2] = 'blue'
	couleur[classe_app == 3] = 'green'


	plot (x_app, col = couleur)

	dev.new()

	# Couleurs
	couleur <- rep('red', n_test)
	couleur[classe_test == 1] = 'red'
	couleur[classe_test == 2] = 'blue'
	couleur[classe_test == 3] = 'green'

	plot (x_test, col = couleur)
}


# Macro 1.3
rdfEstimations <- function (data_filename)
{
	load(data_filename)

	M1 <- seq(1,2)
	M1[1] = mean (x_app[classe_app == 1, 1])
	# 0.3901397 comme résultat
	M1[2] = mean (x_app[classe_app == 1, 2])
	# 1.483721 comme résultat
	# print (M1)

	# -> M1 = (0.3901397	1.483721)
	# -> m1 = (1			2)

	M2 <- seq(1,2)
	M2[1] = mean (x_app[classe_app == 2, 1])
	# 5.979027 comme résultat
	M2[2] = mean (x_app[classe_app == 2, 2])
	# 5.815149 comme résultat
	# print (M2)

	# -> M2 = (5.979027		5.815149)
	# -> m2 = (6			6)

	M3 <- seq(1,2)
	M3[1] = mean (x_app[classe_app == 3, 1])
	# 4.192731 comme résultat
	M3[2] = mean (x_app[classe_app == 3, 2])
	# 0.05827418 comme résultat
	# print (M3)

	# -> M3 = (4.192731		0.05827418)
	# -> m3 = (4			0)


	Sigma1 <- matrix(1, 2, 2)
	Sigma2 <- matrix(1, 2, 2)
	Sigma3 <- matrix(1, 2, 2)
	for (i in 1:2)
	{
		for (j in 1:2)
		{
		Sigma1[i, j] = cov (as.vector(x_app[classe_app == 1, i]), as.vector (x_app[classe_app == 1, j]))
		Sigma2[i, j] = cov (as.vector(x_app[classe_app == 2, i]), as.vector (x_app[classe_app == 2, j]))
		Sigma3[i, j] = cov (as.vector(x_app[classe_app == 3, i]), as.vector (x_app[classe_app == 3, j]))
		}
	}

	# print (Sigma1)
	#            	[,1]       	[,2]
	# [1,]  	1.0361627 	-0.9333708
	# [2,] 		-0.9333708  2.4645182

	# print (Sigma2)
	#           	[,1]      	[,2]
	# [1,] 		9.1558080 	0.7322511
	# [2,] 		0.7322511 	1.1636842

	# print (Sigma3)
 	#	         [,1]     		[,2]
	# [1,] 		9.1558080 	0.7322511
	# [2,] 		0.7322511 	1.1636842

	# print (s)
	# 			[,1]			[,2]
	# s1		1.0				2
	# s2		3.0				1
	# s3		1.5				2
	print (s)
}

# Macro 1.4
rdfLDA_Classi <- function (data_filename)
{
	load(data_filename)
# Grille d'estimation de la densité de probabilité en 50 intervalles selon 1er attribut
	xp1 <- seq (min (x_app[,1]), max (x_app[,1]), length=50)
# Grille d'estimation de la densité de probabilité en 50 intervalles selon 2ème attribut
	xp2 <- seq (min (x_app[,2]), max (x_app[,2]), length=50)

	grille <- expand.grid (x1=xp1,x2=xp2)

	# Résultat pour QDA à décommenter :
	# x_app.qda <- qda (x_app,classe_app)
	 x_app.lda <- lda (x_app,classe_app)

	grille = cbind (grille[,1], grille[,2])

	
	# A décommenter pour QDA :
	# Zp <- predict (x_app.qda,grille)
	# assigne_test <- predict (x_app.qda, newdata=x_test)
	Zp <- predict (x_app.lda,grille)
	assigne_test <- predict (x_app.lda, newdata=x_test)
	
# Estimation des taux de bonnes classifications
	table_classification_test <- table (classe_test, assigne_test$class)
# table des classes correctes vs classification
	diag (prop.table (table_classification_test, 1))
# total pourcentage correct
	taux_bonne_classif_test <- sum (diag (prop.table (table_classification_test)))

# Pour obtenir un pourcentage
	print (taux_bonne_classif_test * 100)

# Question 1.5
# Création du vecteur contenant le code de la forme des données test assignées aux classes - code initialisé à 1
	shape<-rep(1,n_test);
# forme des données assignées à la classe 2
	shape[assigne_test$class==1]=1;
	shape[assigne_test$class==2]=2;
	shape[assigne_test$class==3]=3;
# Affichage avec code couleur et forme adaptées
	plot(x_test,col=couleur,pch=shape,xlab = "X1", ylab = "X2")

	xp1 <- seq (min (x_app[,1]), max (x_app[,1]), length=50)
	xp2 <- seq (min (x_app[,2]), max (x_app[,2]), length=50)

	grille <- expand.grid (x1=xp1,x2=xp2)
	grille = cbind (grille[,1],grille[,2])

	# A décommenter pour QDA:
	# Zp <- predict (x_app.qda,grille)
	Zp <- predict (x_app.lda,grille)

	zp1 <- Zp$post[,3] - pmax (Zp$post[,2], Zp$post[,1])
	zp2	<- Zp$post[,1] - pmax (Zp$post[,3], Zp$post[,2])
	zp3 <- Zp$post[,2] - pmax (Zp$post[,1], Zp$post[,3])

	contour(xp1, xp2, matrix(zp1,50), add=TRUE, levels=0, drawlabels=FALSE)
	contour(xp1, xp2, matrix(zp2,50), add=TRUE, levels=0, drawlabels=FALSE)
	contour(xp1, xp2, matrix(zp3,50), add=TRUE, levels=0, drawlabels=FALSE)
}


