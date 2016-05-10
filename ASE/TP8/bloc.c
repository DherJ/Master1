/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 4 novembre 2014 
**/


#include <stdio.h>
#include <string.h>
#include "bloc.h"
#include "drive.h"
#include "mbr.h"
#include "hardware.h"
#include "assert.h"




void init_super(unsigned int vol)
{
	printf("Initialisation du superbloc du volume ...\n");	
	struct volume_s volume;
	struct freeBloc_s fb;
	unsigned char buffer[HDA_SECTORSIZE];
	//load_mbr();
	volume = mbr.disque[vol];
	current_super.super_magic = SUPER_MAGIC;
	current_super.super_first_free_bloc = 1;
	current_super.super_nb_free = volume.size - 1;
	//strncpy(current_super.super_name, nom, 32);
	fb.freebloc_nb_free=volume.size - 1;
	fb.freebloc_next=0;			
	save_super();
	memset(buffer, 0, HDA_SECTORSIZE);
	memcpy(buffer, &fb, sizeof(struct freeBloc_s));
	write_bloc(vol, 1, buffer);
	current_vol=vol;
	printf("Initialisation du superbloc du volume terminé\n");	
	
}


int load_super(unsigned vol){
	//vol=0;	//test avec constante
	unsigned char buffer[HDA_SECTORSIZE];
	memset(buffer, 0, HDA_SECTORSIZE);
	//printf("Dans load_super, Avant read_bloc\n");	
	read_bloc_n(vol, SUPER, buffer, sizeof(struct superBloc_s));
	//printf("Dans load_super, Avant memcpy\n");	
	//memcpy(&current_super, buffer, sizeof(struct superBloc_s));
	//printf("Dans load_super, Après memcpy\n");
	//current_vol=vol;	
		
	//if(current_super.super_magic==SUPER_MAGIC){
	//	printf("Dans load_super, dans le if de MAGIC\n");
	//	current_vol=vol;
	//	return 0;
	//}
	return current_super.super_magic==SUPER_MAGIC;
}

/*
// charger un superBloc 
int load_super(unsigned int vol) {
	//char* buffer;
	//read_bloc(vol, SUPER, (char*) current_super);
	read_bloc_n(vol, SUPER, &current_super, sizeof(current_super));
	memcpy(buffer, &current_super, sizeof(struct superBloc));
	return (current_super.super_magic == SUPER_MAGIC);    //si le bloc a bien été chargé on retourne 1 sinon 0 
}
*/


void save_super(){
	unsigned char buffer[HDA_SECTORSIZE];
	memset(buffer, 0, HDA_SECTORSIZE);
	memcpy(buffer, &current_super, sizeof(struct superBloc_s));
	write_bloc(current_vol, 0, buffer);
}


/*
// sauvegarde le superBloc courrant après l'avoir modifié
void save_super() {
	//char* buffer; 
	//memcpy(buffer, &current_super, sizeof(struct superBloc));
	write_bloc_n(current_vol, SUPER, buffer, sizeof(struct superBloc));
}
*/


unsigned int new_bloc(){
	struct freeBloc_s *fb;
	unsigned char buffer[HDA_SECTORSIZE];
	unsigned res;
	if(current_super.super_nb_free==0)
		return 0;
	assert(current_super.super_first_free_bloc);
	read_bloc(current_vol, current_super.super_first_free_bloc, buffer);
	fb = (struct freeBloc_s*) buffer;
	res = current_super.super_first_free_bloc;
	if(fb->freebloc_nb_free > 1){
		current_super.super_nb_free --;
		fb->freebloc_nb_free--;
		write_bloc(current_vol, current_super.super_first_free_bloc, buffer);
	}
	else
	{
		current_super.super_first_free_bloc = fb->freebloc_next;
	}
	//current_super.super_first_free_bloc--;
	save_super();
	return res;
}	


/*
// on va chercher un bloc libre dans le volume puis on va créer un bloc à la place du 1er bloc libre trouvé
int new_bloc() {
	unsigned int new = current_super.super_first_free_bloc;   // on garde le numéro du free bloc sur lequel on va créer le bloc
	char* buffer;	
	struct freeBloc free_bloc;
	// cas 1 : il n'y a plus de bloc libre 
	if(current_super.super_n_free == 0) {
		return 0;       
	}
	read_bloc(current_vol, current_super.super_first_free_bloc, buffer);
	// cas 2 : le 1er bloc est libre
	if(free_bloc.fb_n_free == 1) {
		current_super.super_first_free_bloc = free_bloc.fb_next; // on fait pointer le superBloc sur le bloc sur lequel pointait le free bloc
		current_super.super_n_free--;                            // on retire un free bloc de la liste 
		return new;                                             
	}
	// cas 3 : cas général 
	else {
		current_super.super_n_free--;                  // on réduit le nombre de blocs libres 
		current_super.super_first_free_bloc++;         // on décale le pointeur pointant sur un free bloc(dans lequel on va créer le bloc) d'un bloc 
		write_bloc(current_vol, &current_super.super_first_free_bloc, buffer);
		return new;
	}
}
*/


void free_bloc(unsigned int bloc) {
	if(bloc==0){
		printf("Impossible de libérer le bloc : bloc inexistant\n");
		return;
	}
	struct freeBloc_s freeB;
	unsigned char buffer[HDA_SECTORSIZE];
	freeB.freebloc_nb_free = 1;
	freeB.freebloc_next = current_super.super_first_free_bloc;
	current_super.super_first_free_bloc = bloc;
	current_super.super_nb_free++;
	memset(buffer, 0, HDA_SECTORSIZE);
	memcpy(buffer, &freeB, sizeof(struct freeBloc_s));
	write_bloc(current_vol, bloc, buffer);
	save_super();
}


/*
void test_superbloc(){
	unsigned int i=0;
	execute("list");
	printf("Choisir le volume sur lequel nous allons effectuer les opérations: ");
	scanf("%d", &current_vol);
	printf("Size: %d\n", mbr.disque[current_vol-1].size);
	
	for(i=0; i<5; i++){
		printf("\ni = %d \n", i);
		//puts("");
		//read_bloc(current_vol, SUPER, (char *) &current_super);	
		//printf("\nfirst-free-bloc: %d", current_super.super_first_free_bloc);
		//printf("\nnb_free: %d", current_super.super_nb_free);
		//new_bloc();
	}
	
}
*/

void test_superbloc(){
	int i=0;	
	execute("list");
	printf("Choisir le volume sur lequel nous allons effectuer les opérations: ");
	scanf("%d", &current_vol);
	current_vol=current_vol-1;
	//current_vol=1;	
	if(current_vol == 0 && mbr.nbr_volumes == 0){
		printf("Erreur il n'y a pas de volume, veuillez en créer un avant\n");
		return;
	}

	if( load_super(current_vol) == 1 ){
		printf("Chargement de Super réussi !\n");
	}
	else 
		printf("Chargement de Super: FAIL\n");
	printf("Size: %d", mbr.disque[current_vol].size);
	for(i=0; i<5; i++){
		printf("\ni = %d", i);
		printf("\tfirst-free-bloc: %d", current_super.super_first_free_bloc);
		printf("\tnb_free: %d\n", current_super.super_nb_free);
		new_bloc();
	}
	i=10;
	for(i=10; i<15; i++){
		printf("\ni = %d", i);
		printf("\tfirst-free-bloc: %d", current_super.super_first_free_bloc);
		printf("\tnb_free: %d\n", current_super.super_nb_free);
		free_bloc(i);
	}

}
