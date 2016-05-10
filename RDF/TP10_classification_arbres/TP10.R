library("EBImage")
source("rdfSegmentation.R")


allFacesName="allFaces.png";
allFaces=rdfReadGreyImage(allFacesName);

# création de stackedFaces :
# Empilement des visages 40x33 dans une matrice stackedFaces 40x33x400
# Attention, exécution possiblement longue ~30 sec, pas de panique...)
# numLignes, numColonnes, numFaces


# Remplit une 'matrice 3D' (en vérité, un array) avec les
# valeurs contenues dans le data. C'est une fonction qui
# s'adresse tout particuliÃ¨rement au TP10 - 11 de RDF :)
fillStack <- function (data, d1, d2, d3)
{
	data <- t(imageData(data));
	stackedFaces <- array(rep (0, d1*d2*d3), c(d1, d2, d3));

	for (i in 0:19)
	{
		for (j in 0:19)
		{
        	stackedFaces[,, (i*20 + j + 1)] = data[(1+i*d1) : ((i+1)*d1), (1+j*d2) : ((j+1)*d2)];
    	}
	}

	print ("End of filling");
	return (stackedFaces);
}


fill <- function (data, filled, f, indice)
{
	for (i in 1:40)
	{
		for (j in 1:33)
		{
			filled [i,j,indice] <- data[i,j,f]
		}
	}
	
	return (filled)
}


separateData <- function (data)
{
	test <- array(rep (0, 40*33*400), c(40, 33, 400))
	app <- array(rep (0, 40*33*400), c(40, 33, 400))
	i <- 1
	j <- 1

	for (f in 1:400)
	{
		x <- sample (1:2, 1)
		if (x == 1)
		{
			test <- fill(data, test, f, i)
			i <- i+1
		}
		else
		{
			app <- fill(data, app, f, j)
			j <- j+1
		}
	}	

	print ("End of separation")
	display(test)
	display(app)
	return (list(app = app, test = test))
}

calculEntropie <- function(serie)
{
	entropie = matrix(rep(0,40*33),nrow=33,ncol=40);
	res = 0;
	for (i in 1:33) {
		for (j in 1:40) {
			for (k in 1:400) {
				res = res +serie[i,j,k];
			}
		}
		m = res /400;
		entropie[i,j] <- -log2((res)^(res)) - log2((1-res)^(1-res));
	}
	entropie;
}



entropie <- function (stacked) {
    entrop = zeros(40,33);
    res = 0;
    for (j in 1:40) {
        for (i in 1:33) {
            for (k in 1:400) {
                res = res  + stacked(jj,ii,kk);
            }
            res = res/400;
            entrop(j,i) = -log2(res^res) - log2((1-res)^(1-res));
        }
    }
}



faceStack <- fillStack(allFaces, 40, 33, 400);
list <- separateData (faceStack);

