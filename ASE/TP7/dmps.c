/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 4 novembre 2014 
**/


#include <stdlib.h>
#include <stdio.h>
#include "drive.h"
#include "hardware.h"


void dump(char *buffer, const unsigned int buffer_size, const unsigned int mode){
    unsigned int i, j;
    for (i = 0; i < buffer_size; i+=16){
		/* offset */
		printf("%.8o", i);
		/* octal dump */
		if (mode==0){
			for (j = 0; j < 8; j++)
				printf(" %.2x", buffer[i+j]);
			printf(" - ");
			for (; j < 16; j++)
				printf(" %.2x", buffer[i+j]);
			if (mode==1)
				printf("\t");
		}
		/* ascii dump */
		if (mode==1){
			for (j = 0; j < 8; j++)
				printf(" %1c ", isprint(buffer[i+j])?buffer[i+j]:' ');
			printf(" - ");
			for (; j < 16; j++)
				printf(" %1c ", isprint(buffer[i+j])?buffer[i+j]:' ');
		}
		printf("\n");
    }
}

void dmps(){
	printf("Affichage d'un secteur");
	unsigned int cyl=0, sec=0, mode=0;
	char *buffer = malloc(HDA_SECTORSIZE * sizeof(char));

	printf("Utilisation : ./dmps cylindre secteur [par defaut mode=1]	\
			\ncylindre : entier [0 - %d]						\
			\nsecteur : entier [0 - %d]						\
			\nmode : 1 (hexa), 2 (ascii), 3 (hexa et ascii)\n\n",
			HDA_MAXCYLINDER-1, HDA_MAXSECTOR-1);
	
	printf("Saisir la valeur du cylindre, du secteur et le mode d'affichage\n");
	scanf("%d %d %d", &cyl, &sec, &mode);

	if(cyl > HDA_MAXCYLINDER){
		printf("Erreur : valeur de cylindre trop grande\n");
		exit(INVALID_CYLINDER);
	}

	if(sec > HDA_MAXSECTOR){
		printf("Erreur ; valeur de secteur trop grande\n");
		exit(INVALID_SECTOR);
	}

	if(mode != 1 && mode != 2 && mode != 3){
		printf("Erreur : mode de l'affichage incorrect\n");
	}

	/* Récupère et affiche le secteur choisi */
	printf("\nAffichage du cylindre %u, secteur %u: \n", cyl, sec);
	read_sector(cyl, sec, buffer);
	dump(buffer, HDA_SECTORSIZE, mode);
}


/*
int dmps2(int argc, char** argv){
	unsigned int cyl = 0, sec = 0, mode = 1;
	unsigned int ascii = 0, hexa = 1;
	char *buffer = malloc(HDA_SECTORSIZE * sizeof(char));
	puts("\nProgramme \"dump sector\" (dmps)\n");
	if (argc != 3 && argc != 4){
		printf("Utilisation : ./dmps cylindre secteur [par defaut mode=1]	\
			\ncylindre : entier [0 - %d]						\
			\nsecteur : entier [0 - %d]						\
			\nmode : 1 (hexa), 2 (ascii), 3 (hexa et ascii)\n\n",
			HDA_MAXCYLINDER-1, HDA_MAXSECTOR-1);
		exit(INVALID_PARAMETER);
	}
	cyl = atoi(argv[1]);
	sec = atoi(argv[2]);
	
	if (argc == 4){
		mode = atoi(argv[3]);
	}

	if (cyl >= HDA_MAXCYLINDER){
		printf("Problème rencontré - Cylindre invalide : 0 à %d\n", HDA_MAXCYLINDER-1);
		exit(INVALID_CYLINDER);
	}

	if (sec >= HDA_MAXSECTOR){
		printf("Problème rencontré - Secteur invalide : 0 à %d\n", HDA_MAXSECTOR-1);
		exit(INVALID_SECTOR);
	}

	// Choix du mode d'affichage 
	switch (mode){
		case 1:
			ascii = 0;
			hexa = 1;
		break;
		case 2:
			ascii = 1;
			hexa = 0;
		break;
		case 3:
			ascii = 1;
			hexa = 1;
		break;
		default:
			puts ("Problème rencontré - Mode invalide. Hexa par défaut.	\
				\n\t1 (hexa), 2 (ascii), 3 (hexa et ascii)\n");
		break;
	}

	init();
	printf("Lecture du cylindre %u, secteur %u\n\n", cyl, sec);
	read_sector(cyl, sec, buffer);
	dump(buffer, HDA_SECTORSIZE, ascii, hexa);

	return EXIT_SUCCESS;
}
*/


