/**
KNAPIK Christopher - DHERSIN Jérome 
TP4 ASE - 14 octobre 2014 
**/


#include "sem.h"


int main(int argc, char *argv[]){ 
	sem_init(&mutex, "mutex", 1);
	sem_init(&vide, "vide", NBSEM);
	sem_init(&plein, "plein", 0);
	
	mutex.sem_cpt=1;
	vide.sem_cpt=100;
	plein.sem_cpt=0;
	mutex.sem_name="mutex";
	vide.sem_name="vide";
	plein.sem_name="plein";
	
	puts("Creation du contexte producteur ...");
	create_ctx(16384, producteur, NULL);
	puts("Creation du contexte consommateur ...");
	create_ctx(16384, consommateur, NULL);
	yield();
	printf("Fin du programme ...\n");

	exit(EXIT_SUCCESS);
}
