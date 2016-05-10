/**
KNAPIK Christopher - DHERSIN Jérome 
TP5 ASE - 21 octobre 2014 
**/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include "include/hardware.h"
#include "dmps.h"
#include "drive.h"



/*
static int check_hda () {
	
}
*/

void read_sector(unsigned int cyl, unsigned int sec, unsigned char* buffer) {
	read_sector_n(cyl, sec, buffer, HDA_SECTORSIZE);
}


void read_sector_n(unsigned int cyl, unsigned int sec, unsigned char* buffer, int size) {
	int i;
	if(cyl<0 || cyl>HDA_MAXCYLINDER){
		printf("Erreur cylindre\n");
		exit(EXIT_FAILURE);
	}
	if(sec<0 || sec>HDA_MAXSECTOR){
		printf("Erreur secteur\n");
		exit(EXIT_FAILURE);
	}
	if(sec<0 || sec>HDA_SECTORSIZE){
		printf("Erreur de taille\n");
		exit(EXIT_FAILURE);
	}
	go_to_sector(cyl, sec);
	_out(HDA_DATAREGS, 0);
	_out(HDA_DATAREGS+1, 1);
	_out(HDA_CMDREG, CMD_READ);
	_sleep(HDA_IRQ);
	for (i=0; i<size; i++){
		buffer[i] = MASTERBUFFER[i];
	}
}


void write_sector(unsigned int cyl, unsigned int sec, unsigned char* buffer) {
	write_sector_n(cyl, sec, buffer, HDA_SECTORSIZE);	
}


void write_sector_n(unsigned int cyl, unsigned int sec, unsigned char* buffer, int size) {
	int i;
	if(cyl<0 || cyl>HDA_MAXCYLINDER){
		printf("Erreur cylindre\n");
		exit(EXIT_FAILURE);
	}
	if(sec<0 || sec>HDA_MAXSECTOR){
		printf("Erreur secteur\n");
		exit(EXIT_FAILURE);
	}
	gotoSector(cyl, sec);
	for (i=0; i<HDA_SECTORSIZE; i++){
		MASTERBUFFER[i] = buffer[i];
	}
	_out(HDA_DATAREGS, 0);
	_out(HDA_DATAREGS+1, 1);
	_out(HDA_CMDREG, CMD_WRITE);
	_sleep(HDA_IRQ);
}


void format(){
	int i;
	for(i=0; i<HDA_MAXCYLINDER; i++){
		format_sector(i, 0, HDA_MAXSECTOR, 0);
	}
}


void format_sector(unsigned int cyl, unsigned int sec, unsigned int nsector, unsigned int value) {
	int i;
	unsigned char buffer[HDA_SECTORSIZE];
	for (i=0; i<HDA_SECTORSIZE ; i++){
		buffer[i] = value;
	}
	for(i=sec; i<sec+nsector; i++){
		write_sector(cyl,i,buffer);
	}
}


static void go_to_sector(unsigned int cyl, unsigned int sec) {
	if(cyl<0 || cyl>HDA_MAXCYLINDER){
		printf("Erreur cylindre\n");
		exit(EXIT_FAILURE);
	}
	if(sec<0 || sec>HDA_MAXSECTOR){
		printf("Erreur secteur\n");
		exit(EXIT_FAILURE);
	}
	_out(HDA_DATAREGS, (cyl>>8) &0xFF);
	_out(HDA_DATAREGS+1, cyl &0xFF);
	_out(HDA_DATAREGS+2, (sec>8) &0xFF);
	_out(HDA_DATAREGS+3, sec &0xFF);
	_out(HDA_CMDREG, CMD_SEEK);
	_sleep(HDA_IRQ);
}


int cylinderOfBloc(int volume, int bloc){
	int Fc = mbr.mbr_vol[vol].firstCyl;	
	int Fs = mbr.mbr_vol[vol].firstSec;
	return Fc + (Fs + bloc) / HDA_MAXSECTOR;
}


int sectorOfBloc(int volume, int bloc){
	int Fs = mbr.mbr_vol[vol].firstSec;
	return (Fs + bloc) % HDA_MAXSECTOR;
}


void load_MBR(){	
	unsigned char buf[HDA_SECTORSIZE];
	if(sizeof(struct mbr_s)>HDA_SECTORSIZE){
		exit(0);
	}
	read_sector_n(0, 0, &mbr, sizeof(struct mbr_s));
	if(mbr.mbr_magic!=MBR_MAGIC){
		mbr.mbr_n_vol=0;
		mbr.mbr_magic=MBR_MAGIC;
		puts("disque vierge, continuer ?");
	}
}


void save_MBR(){
	write_sector_n(0, 0, &mbr, sizeof(struct mbr_s));
}


int main(){
	printf("Test\n");
	if(init_hardware("etc/hardware.ini") == 0) {
		fprintf(stdin, "Erreur d'initialisation hardware\n");
		exit(EXIT_FAILURE);
    }
	return EXIT_SUCCESS;
}
