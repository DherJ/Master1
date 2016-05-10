/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 4 novembre 2014 
**/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "drive.h"
#include "hardware.h"
#include "mbr.h"
#include "bloc.h"
#include "volume.h"
#include "common.h"


char volume_type_name[3][10] = {"Prim", "Sec", "Autre"};


void list(){
	unsigned int i, libre = HDA_MAXSECTOR * HDA_MAXCYLINDER - 1; /* MBR prend 1 */

	if (mbr.nbr_volumes < 1){
		puts("Aucun volume créé\n");
		return;
	}

	puts (" num ID\t Type\t \tDebut\t\t Taille\t");
	puts ("");
	for (i = 0; i < mbr.nbr_volumes; i++){
		printf(" %d\t %s\t\t(%d, %d)\t\t %d \t\n", i+1, volume_type_name[mbr.disque[i].type], mbr.disque[i].cyl, mbr.disque[i].sec, mbr.disque[i].size);
		libre -= mbr.disque[i].size;
	}
	printf(" Libre\t X\t \tX\t\t %d \t\n", libre);
	puts ("");
}

void new(){
	unsigned int type;
	unsigned int taille;
	unsigned int i, j, cyl, sec;
	unsigned int libres = 0;
	struct volume_s volume;
	char secteurs[HDA_MAXCYLINDER][HDA_MAXSECTOR] = {{0}};

	if (mbr.nbr_volumes >= MAX_VOLUME){
		printf("Trop de volumes\n");
		return;
	}

	puts("Creation  d'un nouveau volume");
    	/* Saisie des informations */
  	printf("Taille (Saisir uniquement un entier supérieur à 0) : ");	
	if (scanf("%u", &taille) != 1){
		while ( getchar() != '\n' );
		}


	do 	{
		printf("Type (0 Primaire, 1 Secondaire, 2 Autre) : ");
	  	if (scanf("%u", &type) != 1){
	  		while ( getchar() != '\n' );
	  	}
	} while (type > 2);

	/* MBR */
	secteurs[0][0] = 1;

	/* Secteur déjà utilisé */
	for (i = 0; i < mbr.nbr_volumes; i++){
		for (j = 0; j < mbr.disque[i].size; j++){
			cyl = cylindreOfBlok(i, j);
			sec = sectorOfBlok(i, j);
			secteurs[cyl][sec] = 1;
		}
	}

	/* Où mettre le nouveau volume */
	for (i = 0; i < HDA_MAXSECTOR * HDA_MAXCYLINDER; i++){
		cyl = i / HDA_MAXSECTOR;
		sec = i % HDA_MAXSECTOR;
		
		/* Secteur libre ou occupé */
		if (secteurs[cyl][sec] == 0){
			libres++;
		}
		else{
			libres = 0;
		}

		/* Les taille de secteurs précédents sont libres */
		if (libres >= taille){
			/* position au début de l'espace libre */
			cyl = (i-(taille-1)) / HDA_MAXSECTOR;
			sec = (i-(taille-1)) % HDA_MAXSECTOR;

			/* Initialisation du nouveau volume */
			volume.cyl = cyl;
			volume.sec = sec;
			volume.size = taille;
			switch (type) {
				case (0): volume.type = BASE_VOL;
				break;
		
				case (1): volume.type = ANNEXE_VOL;
				break;

				default: volume.type = OTHER;
				break;
			}

			/* Ajout du nouveau volume */
			mbr.disque[mbr.nbr_volumes] = volume;
			init_super(mbr.nbr_volumes);			
			mbr.nbr_volumes++;
			printf("Nouveau volume créé\n");
			return;
		}
	}

	printf("Pas assez de place pour ce volume\n");
}

void delete(){
    unsigned int id, volume;

    /* Saisie des informations */
    printf("ID du volume à supprimer : ");
    if (scanf("%u", &id) != 1){
		while ( getchar() != '\n' );
	}
	id--;

	if (id >= mbr.nbr_volumes){
		printf("Ce volume n'existe pas\n");
		return;
	}

	/* Décalage des volumes suivants */
	for (volume = id; volume < mbr.nbr_volumes; volume++){
		mbr.disque[volume] = mbr.disque[volume+1];
	}

	mbr.nbr_volumes--;
	printf("Volume supprimé\n");
}

void testSB(){
	test_superbloc();
}

void saveMBR(){
	save_mbr();
}

void saveMBRxit(){
	save_mbr();
	exit(EXIT_SUCCESS);
}

void do_xit(){
	exit(EXIT_SUCCESS);
}

void xit(){
	do_xit(); 
}

void help(){
	struct _cmd *command = commands;

	for (; command->name; command++) 
	printf ("%s ~ %s\n", command->name, command->comment);
}

void commandError(){
	puts("Commande inconnue, utilisez la commande help pour voir la liste des commandes");
}
