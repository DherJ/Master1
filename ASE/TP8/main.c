#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hardware.h"
#include "bloc.h"
#include "common.h"
#include "volume.h"
#include "mbr.h"
#include "drive.h"



void execute(const char *name){
    struct _cmd *c = commands; 
    while (c->name && strcmp (name, c->name))
		c++;
    (*c->fun)();
}

void boucleExec(void){
    char name[64];
    
    while (printf("> "), scanf("%s", name) == 1)
	execute(name) ;
}


int main(void){
	init();
	load_mbr();
	execute("help");
	boucleExec();
	return 0;
}


/*
int main (int argc, char **argv){
	unsigned int i;
	int bloc;
	int rnd;

	printf("Initialisation...\n");
	init();
	load_mbr();
	printf("Fin Initialisation...\n");
	
	do {
		printf("Création new_bloc\n");
		bloc = new_bloc();
		printf("%i new_bloc()\n",bloc);
	} while(bloc);
	for(i=0; i<10; i++){
		rnd = (rand() % 99) + 1;
		printf("free_bloc(%i)\n",rnd);
		free_bloc(rnd);
	}
	for(i=0; i<3; i++){
		bloc = new_bloc();
		printf("%i new_bloc()\n",bloc);
	}
	// and exit! 
	exit(EXIT_SUCCESS);
}
*/
