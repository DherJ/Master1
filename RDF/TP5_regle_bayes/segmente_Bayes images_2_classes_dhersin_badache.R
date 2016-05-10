# -----------------------------------------------------------------------
# Extraction d'attributs de pixels pour la classification,
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

# Chargement des fonctions externes
library ("EBImage")
source ("rdfSegmentation.R")
source ("TP5.R")

# Chargement de l'image
#nom <- "rdf-2-classes-texture-0.png"
nom <- "2classes_100_100_8bits.png"
image <- rdfReadGreyImage (nom)

# Calcul et affichage de son histogramme
nbins <- 256
h <- hist (as.vector (image), freq=FALSE, breaks = seq (0, 1, 1 / nbins))

# Segmentation par binarisation 0.3
seuil <- 0.36
binaire30 <- (image - seuil) >= 0


# Affichage des deux images
# if (interactive ()) {
# display (binaire30, "image binaire 0.3")
#  display (binaire35, "image binaire 0.35")
#  display (binaire40, "image binaire 0.4")
# }

# Chargement de l'image omega1
nom <- "2classes_100_100_8bits_omega1.png"
omega1 <- rdfReadGreyImage (nom)

# Chargement de l'image omega2
nom2 <- "2classes_100_100_8bits_omega2.png"
omega2 = rdfReadGreyImage (nom2)

#  Calcul des probas a priori des classes.
#  Utilisation de la macro pour calculer les probabilités
p1 <- rdfCalculPrioriClasse (image, omega1, nbins)
p2 <- rdfCalculPrioriClasse (image, omega2, nbins)

print ("Probabilité à priori pour Omega 1 :")
print (p1)
print ("Probabilité à priori pour Omega 2 :")
print (p2)

#  Calcul des probas conditionnelles
pIC <- rdfCalculProbaConditionnelle (image, 79, nbins)
p1C <- rdfCalculProbaConditionnelle (omega1, 79, nbins)
p2C <- rdfCalculProbaConditionnelle (omega2, 79, nbins)

print ("Probabilité conditionnelle pour Image :")
print (pIC)
print ("Probabilité conditionnelle pour Omega 1 :")
print (p1C)
print ("Probabilité conditionnelle pour Omega 2 :")
print (p2C)


# 	Pour le seuil X calcul de l'erreur d'assignation
rdfProbaErreur (image, omega1, omega2, nbins)

#	seuil = seuil_minimum_erreur/255 
# binaire_Bayes <- (image - seuil) >= 0
# display (binaire_Bayes, "image binaire Bayes")

