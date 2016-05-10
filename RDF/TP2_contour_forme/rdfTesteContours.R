# -----------------------------------------------------------------------
# Extraction d'attributs de contours,
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
library(stats)
source ("rdfContours.R")

# Chargement d'un contour
nom_circle <- "rdf-cercle-80.txt"
cont_circle <- rdfChargeFichierContour (nom_circle)

#---------Chargement des images------------#

nom <- "rdf-patate.png"
img <- rdfReadGreyImage (nom)
cont <- rdfContour (img)

#-------------------------------------------#

# Afficher le contour
#plot (cont_circle, main = nom_circle, type = "o", asp = 1, col = "red",
#      ylim = rev (range (Im (cont_circle))))

# Affichage des contours
# on garde 1 point sur 4
#lines(cont_circle[seq(0,80, 4)], col="blue")
# on garde 1 point sur 8
#lines(cont_circle[seq(0,80, 8)], col="green")


# Descripteurs de fourrier sur rdf-carre-80.txt

# Ouvrir une nouvelle fenetre graphique
#dev.new()

#carre <- "rdf-carre-80.txt"
#cont_carre <- rdfChargeFichierContour (carre)
#fourier <- fft(cont_carre)/length(cont_carre)
#plot (cont_carre, main = carre, type = "o", asp = 1, col = "red",
#      ylim = rev (range (Im (cont_carre))), ylab = "Ordonnee", xlab = "Abscisse")
#legend("topright", legend = c("carre", "fourrier", "inv_fourier", "annulerFourier"), col = c("red", "blue", "green", "yellow"), pch = 15, bty = "n", pt.cex = 2, cex = 0.8, text.col = "forestgreen", horiz = TRUE, inset = c(0.1, 0.1))

#dev.new()
#plot (cont_carre, main = carre, type = "o", asp = 1, col = "red",
#      ylim = rev (range (Im (cont_carre))), ylab = "Ordonnee", xlab = "Abscisse")
#legend("topright", legend = c("carre", "fft","inv_fft"), col = c("red", "blue", "green"), pch = 15, bty = "n", pt.cex = 2, cex = 0.8, text.col = "forestgreen", horiz = TRUE, inset = c(0.1, 0.1))

# Affiche le r?sultat du descripteur de fourrier, fft prends une liste de points en entr?es
#lines(fft(cont_carre)/length(cont_carre), main = carre, type = "o", asp = 1, col = "blue", ylim = rev (range (Im (cont_carre))))

# inverse du calcul des descripteurs de fourrier
#lines(fft(fft(cont_carre), inverse=TRUE)/length(cont_carre), main = carre, type = "o", pch=22, lty=2, col = "green")

#dev.new()
#plot(cont_circle, main = nom_circle, type = "o", asp = 1, col = "red", ylim = rev (range (Im (cont_carre))))
#	legend("topright", legend = c("circle", "corde_1", "corde_0.5"), col = c("red", "blue", "green"), pch = 15, bty = "n", pt.cex = 2, cex = 0.8, text.col = "forestgreen", horiz = TRUE, inset = c(0.1, 0.1))

#lines(fft(cont_carre), main = carre, type = "o", asp = 1, col = "purple", ylim = rev (range (Im (cont_carre))))
#lines(fft(fft(cont_carre),inverse=TRUE)/80, main = carre, type = "o", asp = 1, col = "black", ylim = rev (range (Im (cont_carre))))
#lines(rdfAnnuleDescFourrier(cont_carre, 0.8),col="green")
#lines(rdfAlgorithmeCorde(cont_circle, dmax=1), col="blue")
#lines(rdfAlgorithmeCorde(cont_circle, dmax=0.5), col="green")


#dev.new()
plot(cont, main = nom, type = "o", asp = 1, col = "red", ylim = rev (range (Im (cont))))
	legend("topright", legend = c("contour", "fourrier", "corde"), col = c("red", "blue", "green"), pch = 15, bty = "n", pt.cex = 2, cex = 0.8, text.col = "forestgreen", horiz = TRUE, inset = c(0.1, 0.1))

lines(fft(fft(cont),inverse=TRUE)/length(cont), main = nom, type = "o", pch=22, lty=2, col = "blue")
#lines(rdfAnnuleDescFourrier(cont, 0.8),col="green")
lines(rdfAlgorithmeCorde(cont, dmax=1), col="green")
