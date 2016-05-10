/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 4 novembre 2014 
**/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "drive.h"
#include "mbr.h"
#include "hardware.h"

struct mbr_s mbr;

void load_mbr (){
	char *buffer = malloc (HDA_SECTORSIZE * sizeof (char));
	
	check_size_mbr ();
	read_sector (0, 0, buffer);

	puts("Chargement du MBR ...");
	mbr = *((struct mbr_s*) buffer);
	printf("Chargement terminé\n");
	
	if (mbr.magic_number != MAGIC_NUMBER){
		puts("Nouveau disque détecté\n");
		mbr.nbr_volumes = 0;
		mbr.magic_number = MAGIC_NUMBER;
	}
	free (buffer);
}


void save_mbr (){
	puts("Enregistrement du MBR ...");
	write_sector (0, 0, (char*) &mbr);
	printf("Sauvegarde terminée\n");
}

void check_size_mbr (){
	if (HDA_SECTORSIZE < sizeof(mbr)){
		printf("Le MBR est trop volumineux pour un secteur");
		exit (EXIT_FAILURE);
	}
}


unsigned int cylindreOfBlok (const unsigned int num_vol, const unsigned int num_bloc){
	struct volume_s vol = mbr.disque[num_vol];
	int cyl_init = vol.cyl;
	int sec_init = vol.sec;

	return (cyl_init + ((sec_init + num_bloc) / HDA_MAXSECTOR));
}


unsigned int sectorOfBlok (const unsigned int num_vol, const unsigned int num_bloc){
	struct volume_s vol = mbr.disque[num_vol];
	int sec_init = vol.sec;

	return ((sec_init + num_bloc) % HDA_MAXSECTOR);
}


void read_bloc (const unsigned int vol, const unsigned int bloc, char *buffer){
	read_sector(cylindreOfBlok(vol, bloc), sectorOfBlok(vol, bloc), buffer);
}


void write_bloc (const unsigned int vol, const unsigned int bloc, const char *buffer){
	write_sector (cylindreOfBlok(vol, bloc), sectorOfBlok(vol, bloc), buffer);
}

void format_vol (const unsigned int vol){
	struct volume_s formated_vol = mbr.disque[vol];
	format_sector (formated_vol.cyl, formated_vol.sec, formated_vol.size, 0);
}

