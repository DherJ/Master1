/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 4 novembre 2014 
**/


#include <stdlib.h>
#include <stdio.h>
#include "drive.h"
#include "hardware.h"


void frmt(){
	int valeur = 0;
	int cyl, sec;
	printf("Processus de formatage :\n");
	printf("Saisir la valeur pour formater : \n");
	scanf("%d", &valeur);
	printf("Formatage en cours...\n");
	/* Formatage du disque secteur par secteur */
	for(cyl=0;cyl<HDA_MAXCYLINDER;cyl++ )
		for(sec=0; sec < HDA_MAXSECTOR; sec++)
			format_sector(cyl, sec, 1, valeur);
	printf("Formatage terminé\n");
}


