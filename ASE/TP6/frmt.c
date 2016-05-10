/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 4 novembre 2014 
**/


#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include "drive.h"
#include "hardware.h"

void format(const unsigned int val);

int main(int argc, char **argv){
	puts("\nProgramme \"format\" (frmt)\n");

	if (argc != 2){
		puts("Utilisation : ./frmt valeur	\
			\nvaleur : entier\n");
		exit(INVALID_PARAMETER);
	}

	init();

	/* Formate tout le disque avec la valeur choisie */
	puts("Formatage du disque ...");
	format(atoi(argv[1]));
	puts("Formatage terminé");

	return EXIT_SUCCESS;
}

void format(const unsigned int val){
	unsigned int cyl, sec;

	/* Formate le disque secteur par secteur */
	for(cyl = 0; cyl < HDA_MAXCYLINDER; cyl++){
		for (sec = 0; sec < HDA_MAXSECTOR; sec++){
			format_sector(cyl, sec, 1, val);
		}
	}
}

