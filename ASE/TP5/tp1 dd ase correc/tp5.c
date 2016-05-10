#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include "tp5.h"
#include "include/hardware.h"
#include "dump.h"

#define DISK_SECT_SIZE_MASK 0x0000FF

static void
empty_it()
{
    return;
}

void gotoSector(int cyl, int sect){
	if(cyl<0 || cyl > HDA_MAXCYLINDER){
		printf("Wrong cylinder index\n");
		exit(EXIT_FAILURE);
	}
	if(cyl<0 || cyl > HDA_MAXSECTOR){
		printf("Wrong sector index\n");
		exit(EXIT_FAILURE);
	}
	
	_out(HDA_DATAREGS, (cyl>>8) && 0xFF);
	_out(HDA_DATAREGS+1, cyl && 0xFF);
	_out(HDA_DATAREGS+2, (sect>>8) && 0xFF);
	_out(HDA_DATAREGS+3, sect && 0xFF);
	_out(HDA_CMDREG, CMD_SEEK);
	_sleep(HDA_IRQ);
}

void read_sector_n(unsigned int cyl, unsigned int sect, unsigned char *buffer, int size){
	int i;
	if(cyl<0 || cyl > HDA_MAXCYLINDER){
		printf("Wrong cylinder index\n");
		exit(EXIT_FAILURE);
	}
	if(sect<0 || sect > HDA_MAXSECTOR){
		printf("Wrong sector index\n");
		exit(EXIT_FAILURE);
	}
	
	if(size<0 || size > HDA_SECTORSIZE){
		printf("Wrong size\n");
		exit(EXIT_FAILURE);
	}
	
	gotoSector(cyl, sect);
	_out(HDA_DATAREGS, 0);
	_out(HDA_DATAREGS+1, 1);
	_out(HDA_CMDREG, CMD_READ);
	_sleep(HDA_IRQ);
	for (i = 0; i < size ; i ++){
		buffer[i] = MASTERBUFFER[i];
	}
}


void read_sector(unsigned int cyl, unsigned int sect, unsigned char *buffer){
	read_sector_n(cyl, sect, buffer, HDA_SECTORSIZE);
}

void write_sector(unsigned int cyl, unsigned int sect, const unsigned char *buffer){
	int i;
	if(cyl<0 || cyl > HDA_MAXCYLINDER){
		printf("Wrong cylinder index\n");
		exit(EXIT_FAILURE);
	}
	if(cyl<0 || cyl > HDA_MAXSECTOR){
		printf("Wrong sector index\n");
		exit(EXIT_FAILURE);
	}
	gotoSector(cyl, sect);
	for (i = 0; i < HDA_SECTORSIZE ; i ++){
		MASTERBUFFER[i] = buffer[i];
	}
	_out(HDA_DATAREGS, 0);
	_out(HDA_DATAREGS+1, 1);
	_out(HDA_CMDREG, CMD_WRITE);
	_sleep(HDA_IRQ);
}

void format(){
	int i;
	for(i = 0 ; i < HDA_MAXCYLINDER; i ++){
		format_sector(i, 0, HDA_MAXSECTOR, 0);
	}
	
}

void format_sector(unsigned int cylinder, unsigned int sector, unsigned int nsector,
unsigned int value){
	int i;
	unsigned char buffer[HDA_SECTORSIZE];
	for (i = 0; i < HDA_SECTORSIZE ; i ++){
		buffer[i] = value;
	}
	for(i = sector ; i < sector+ nsector; i ++){
		write_sector(cylinder,i,buffer);
	}
}


int load_mbr(){
	unsigned char buffer[HDA_SECTORSIZE]; 
	read_sector(0,0,buffer);
	memcopy(&mbr, buffer, sizeof(struct mbr_s));
}











/* return 1 if the size is correct, 0 otherwise */
int check_sector_size(){
	int real_value;
	_out(HDA_CMDREG, CMD_DSKINFO);
<<<<<<< HEAD
	printf("MASTERBUFFER 1 : %d\n",(int)(MASTERBUFFER));
=======
	
>>>>>>> 1a46111f2b7a93b34b64d722978c070cd80904d9
	real_value = ((int)MASTERBUFFER) & DISK_SECT_SIZE_MASK;

	printf("MASTERBUFFER 2 : %d\n",(int)(MASTERBUFFER));
	printf("real_value : %d, HDA_SECTORSIZE : %d\n",real_value,HDA_SECTORSIZE);
	
	int temp = (int)MASTERBUFFER;
	printf("MASTERBUFFER : \"");
	while (temp != 0) {
		printf ("%d",temp%2);
			temp = temp / 2;
	}
	printf("\"\n");
	
	temp =DISK_SECT_SIZE_MASK;
	printf("DISK_SECT_SIZE_MASK : \"");
	while (temp != 0) {
		printf ("%d",temp%2);
			temp = temp / 2;
	}
	printf("\"\n");
	printf("MASTERBUFFER 3 : %d\n",(int)(MASTERBUFFER));
	return real_value == HDA_SECTORSIZE;
}

int main(){
	printf("plop\n");
	int i;
	if(init_hardware("etc/hardware.ini") == 0) {
		fprintf(stdin, "Error in hardware initialization\n");
		exit(EXIT_FAILURE);
    }
	
<<<<<<< HEAD
	// if(!check_sector_size()){
	// 		printf("plop2\n");
	// 		exit(EXIT_FAILURE);
	// 	}
=======
	if(!check_sector_size()){
		fprintf(stderr, "Error in sectors size\n");
		exit(EXIT_FAILURE);
	}
>>>>>>> 1a46111f2b7a93b34b64d722978c070cd80904d9
	
	 /* Interreupt handlers */
    for(i=0; i<16; i++) IRQVECTOR[i] = empty_it;

    /* Allows all IT */
    _mask(1);
	
	unsigned char buffer[HDA_SECTORSIZE];
	for (i = 0; i < HDA_SECTORSIZE ; i ++){
		buffer[i] = 0;
	}
	write_sector(0, 0, buffer);
	for (i = 0; i < HDA_SECTORSIZE ; i ++){
		buffer[i] = 1;
	}
	read_sector(0, 0, buffer);
	dump(buffer, HDA_SECTORSIZE, 1, 1);
	format();
	return EXIT_SUCCESS;
}
