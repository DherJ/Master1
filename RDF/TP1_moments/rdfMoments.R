# -----------------------------------------------------------------------
# Extraction d'attributs de forme,
# Module RdF, reconnaissance de formes
# Copyleft (C) 2014, Universite Lille 1
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
# -----------------------------------------------------------------------

# Chargement d'une image en niveaux de gris
rdfReadGreyImage <- function (nom) {
  image <- readImage (nom)
  if (length (dim (image)) == 2) {
    image
  } else {
    channel (image, 'red')
  }
}

# Calcul de la surface d'une forme
rdfSurface <- function (im) {
  sum (im)
}

# Calcul d'un moment geometrique
rdfMoment <- function (im, p, q) {
  x <- (1 : (dim (im)[1])) ^ p
  y <- (1 : (dim (im)[2])) ^ q
  as.numeric (rbind (x) %*% im %*% cbind (y))
}

# Calcul d'un moment centre
rdfMomentCentre <- function (im, p, q) {
  # Barycentre
  s <- rdfSurface (im)
  cx <- rdfMoment (im, 1, 0) / s
  cy <- rdfMoment (im, 0, 1) / s
  # Initialiser les vecteurs x et y
  x <- (1 : (dim (im)[1]) - cx) ^ p
  y <- (1 : (dim (im)[2]) - cy) ^ q
  # Calcul du moment centre
  as.numeric (rbind (x) %*% im %*% cbind (y))
}

# Calcul d'une matrice d'inertie
rdfMatriceInertie <- function (im)	{
	# Calcul des moments centre
	d <- rdfMomentCentre (im, 1, 1)
	e <- rdfMomentCentre (im, 2, 0)
	f <- rdfMomentCentre (im, 0, 2)
	x <- matrix (c(e, d, d, f), ncol = 2, nrow = 2)
	# print (x)
}

# Calcul des valeurs propres
rdfValeurPropre <- function (im)	{
	# Calcul de la matrice d'Intertie
	m = rdfMatriceInertie (im)
	e <- eigen (m, FALSE, FALSE, FALSE)
	# print (e)
}

# Calcul d'un moment centre normalisé
rdfMomentCentreNormalise <- function (im, p, q)		{
	l <- rdfMomentCentre (im, 0, 0) ^ (1 + (p + q) / 2)
	n <- rdfMomentCentre (im, p, q) / l
	print (n)
}

# Calcul des 5 moments invariants de Hu d'une forme
rdfMomentsInvariants <- function (im)		{
	t30 <- rdfMomentCentreNormalise (im, 3, 0)
	t03 <- rdfMomentCentreNormalise (im, 0, 3)
	t21 <- rdfMomentCentreNormalise (im, 2, 1)
	t12 <- rdfMomentCentreNormalise (im, 1, 2)
	t20 <- rdfMomentCentreNormalise (im, 2, 0)
	t02 <- rdfMomentCentreNormalise (im, 0, 2)

	f1 <- t20 + t02
	print ('Moment de Hu 1:')
		print (f1)

	f2 <- (t20 - t02)^2 + (2*rdfMomentCentreNormalise (im, 1, 1)^2)
	print ('Moment de Hu 2:')
		print (f2)

	f3 <- (t30 - 3*t12)^2 + (3*t21 - t03)^2
	print ('Moment de Hu 3:')
		print (f3)

	f4 <- (t30 + t12)^2 + (t21 + t03)^2
	print ('Moment de Hu 4:')
		print (f4)

	f5 <- (t30 - 3*t12)*(t30 + t12)*((t30 + t12)^2 - 3*(t21 + t03)^2) + 3*(t21 - t03)*(t21 + t03)*(3*(t30 + t12)^2 - (t21 + t03)^2)
	print ('Moment de Hu 5:')
		print (f5)
}
