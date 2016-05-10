library("MASS")
library("lattice")

im1 <- rdfReadGreyImage ("rdf-2-classes-texture-1.png")
im2 <- rdfReadGreyImage ("rdf-2-classes-texture-1-text.png")

# Q1.1
rdfPlotDonnees <- function ()
{
	# Assignation des couleurs pour classe App
	couleur <- rep('red', 3)
	couleur[iris2$species == 's'] = 'red'
	couleur[iris2$species == 'v'] = 'blue'
	couleur[iris2$species == 'c'] = 'green'

	# Affichage de la fenêtre
	plot (x_aff, col = couleur)
}


# Q1.2
rdfPlotKmeans <- function ()
{
	# Assignation des couleurs pour classe App
	couleur <- rep('red', 3)
	couleur[iris2$species == 's'] = 'red'
	couleur[iris2$species == 'v'] = 'blue'
	couleur[iris2$species == 'c'] = 'green'

	km <- kmeans (x, 3, 15)
	
	# Et assignations des formes
	shape <- rep(1, 3)
	shape[km$cluster==1]=1
	shape[km$cluster==2]=2
	shape[km$cluster==3]=3

	# Affichage de la fenêtre
	plot (x_aff, col = couleur)
	centers_aff <- cbind (km$centers[,2], km$centers[,4])
	points (centers_aff, col = 'black', pch = 8)
}


# Q1.3
rdfPlotKmeans5Times <- function ()
{
	tabpoints <- rep(0, 50)
	for (i in 1:5)
	{
		km <- kmeans (x, 3, 15)
		tabpoints[1,i] = km$centers[,2]
		tabpoints[2,i] = km$centers[,3]
		tabpoints[3,i] = km$centers[,4]
	}

	print (tabpoints)
}





# Q1.4
rdfPlotKmeansInit <- function ()
{
	
}





# Q1.5
rdfPlotKmeansInitACP <- function ()
{
	
}





# Q1.6






# Q2.1
rdfAfficheObs <- function (im1, im2)
{
	M <- cbind (as.vector(im1), as.vector(im2))

	plot (M, col = 'black')
}


# Q2.2
rdfKmeansNoInit <- function (im1, im2)
{
	M <- cbind (as.vector(im1), as.vector(im2))
	
	km <- kmeans (M, 2, 30)

	couleur <- rep('blue', 3)
	couleur[km$cluster == 1] = 'blue'
	couleur[km$cluster == 2] = 'green'

	plot (M, col = couleur)
	centers_aff <- cbind (km$centers[,1], km$centers[,2])
	points (centers_aff, col = 'red', pch = 8)
}



# Q2.3
rdfBinarisationKmeans <- function (im1, im2)
{
	M <- cbind (as.vector(im1), as.vector(im2))
	
	km <- kmeans (M, 2, 50)

	display (array(km$cluster==1, dim = c(128,128), "Image binaire"))
	display (array(km$cluster==2, dim = c(128,128), "Image binaire"))
}

rdfBinarisationKmeans (im1, im2)
