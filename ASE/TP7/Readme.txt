/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE -  4 novembre 2014 
**/


Le but de ce TP est de simuler le fonctionnement simplifié d'un disque dur.


Le fichier drive.c contient les fonctions qui permettent de se déplacer sur un disque, lire, écrire ou supprimer les données du disque :

- void init();
Initialise le matériel qui va être utilisé.

- void seek(const unsigned int cyl, const unsigned int sec);
Choisit un secteur et y déplace la tête de lecture.

- void read_sector(const unsigned int cyl, const unsigned int sec, char *buffer);
Récupère le contenu d'un secteur et le place dans un buffer.

- void write_sector(const unsigned int cyl, const unsigned int sec, const char *buffer);
Place le contenu d'un buffer dans un secteur.

- void format_sector(const unsigned int cyl, const unsigned int sec, const unsigned int nsec, const unsigned int value);
Formate un secteur (avec une valeur).
 

Le fichier frmt.c contient les fonctions concernant le formatage du disque.
Le fichier dmps.c permet l'affichage d'un secteur.
le fichier mbr.c permet la gestion du MBR.
le fichier volume.c contient toutes les fonctions concernant la gestion des volumes.

Pour compiler :
"make"
"make clean" supprime les .o générés
"make realclean" supprime les .o et les disques virtuels.

Pour exécuter
dmps
	"./dmps cylindre secteur [mode=1]"
	cylindre : entier
	secteur : entier
	mode : 1 (hexa), 2 (hexa), 3 (hexa et ascii)
frmt
	"./frmt valeur"


