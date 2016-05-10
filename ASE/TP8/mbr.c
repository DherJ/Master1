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
	char *buffer=malloc(HDA_SECTORSIZE * sizeof (char));
	check_size_mbr();
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


unsigned int cylindreOfBlok(unsigned int num_vol, unsigned int num_bloc){
	//printf("Dans cylindreOfBlok, Début\n");	
	struct volume_s vol = mbr.disque[num_vol];
	//printf("Dans cylindreOfBlok, num_vol %u\n", num_vol);
	//printf("Dans cylindreOfBlok, vol.cyl %u & vol.sec %u\n", vol.cyl, vol.sec);	
	int cyl_init = vol.cyl;
	int sec_init = vol.sec;
	//printf("Dans cylindreOfBlok, Fin avant return\n");	

	//return 1; //test avec return 1
	return (cyl_init + ((sec_init + num_bloc) / HDA_MAXSECTOR));
}


unsigned int sectorOfBlok(unsigned int num_vol, unsigned int num_bloc){
	//printf("Dans sectorOfBlok, Début\n");		
	struct volume_s vol = mbr.disque[num_vol];
	//printf("Dans sectorOfBlok, num_vol %u\n", num_vol);	
	//printf("Dans sectorOfBlok, vol.sec %u\n", vol.sec);
	int sec_init = vol.sec;
	//printf("Dans sectorOfBlok, Fin avant return\n");	

	//return 1; //test avec return 1
	return ((sec_init + num_bloc) % HDA_MAXSECTOR);
}


void read_bloc(unsigned int vol, unsigned int bloc, char *buffer){
	//printf("Dans read_bloc, Avant read_sector\n");	
	read_sector(cylindreOfBlok(vol, bloc), sectorOfBlok(vol, bloc), buffer);
	//printf("Dans read_bloc, Après read_sector\n");	
}


void read_bloc_n(unsigned int vol, unsigned int bloc, char *buffer, int size){
	read_sector_n(cylindreOfBlok(vol, bloc), sectorOfBlok(vol, bloc), buffer,size);
}


void write_bloc(unsigned int vol, unsigned int bloc, char *buffer){
	write_sector(cylindreOfBlok(vol, bloc), sectorOfBlok(vol, bloc), buffer);
}


void write_bloc_n(unsigned int vol, unsigned int bloc, char *buffer, int size){
	write_sector_n(cylindreOfBlok(vol, bloc), sectorOfBlok(vol, bloc), buffer, size);
}


void format_vol (unsigned int vol){
	struct volume_s formated_vol = mbr.disque[vol];
	format_sector (formated_vol.cyl, formated_vol.sec, formated_vol.size, 0);
}

