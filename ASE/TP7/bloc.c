/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 4 novembre 2014 
**/


#include <stdio.h>
#include "bloc.h"
#include "drive.h"
#include "mbr.h"
#include "hardware.h"



/* initialiser un volume --> superBloc + freeBloc */
void init_super(unsigned int vol) {
	/* initialisation du superBloc */
	int freeSize;
	char* buffer;
	struct superBloc super;
	super.super_magic = SUPER_MAGIC;
	/* initialisation du 1er freeBloc */
	super.super_first_free_bloc = 1;
	freeSize=mbr.disque[vol].size - 1;
	super.super_n_free = freeSize;
	write_bloc(vol, SUPER, buffer);
}


/* charger un superBloc */
int load_super(unsigned int vol) {
	char* buffer;
	//read_bloc(vol, SUPER, (char*) current_super);
	read_bloc(vol, SUPER, buffer);
	memcpy(buffer, &current_super, sizeof(struct superBloc));
	return (current_super.super_magic == SUPER_MAGIC);                      /* si le bloc a bien été chargé on retourne 1 sinon 0 */
}


/* sauvegarde le superBloc courrant après l'avoir modifié */
void save_super() {
	char* buffer; 
	memcpy(buffer, &current_super, sizeof(struct superBloc));
	write_bloc(current_vol, SUPER, buffer);
}


/* on va chercher un bloc libre dans le volume puis on va créer un bloc à la place du 1er bloc libre trouvé */
int new_bloc() {
	unsigned int new = current_super.super_first_free_bloc;           /* on garde le numéro du free bloc sur lequel on va créer le bloc */
	char* buffer;	
	struct freeBloc free_bloc;
	/* cas 1 : il n'y a plus de bloc libre */
	if(current_super.super_n_free == 0) {
		return 0;       
	}
	read_bloc(current_vol, current_super.super_first_free_bloc, buffer);
	/* cas 2 : le 1er bloc est libre */
	if(free_bloc.fb_n_free == 1) {
		current_super.super_first_free_bloc = free_bloc.fb_next; /* on fait pointer le superBloc sur le blo sur lequel pointait le free bloc */
		current_super.super_n_free--;                            /* on retire un free bloc de la liste */
		return new;                                             
	}
	/* cas 3 : cas général */
	else {
		current_super.super_n_free--;                  /* on réduit le nombre de blocs libres */
		current_super.super_first_free_bloc++;         /* on décale le pointeur pointant sur un free bloc(dans lequel on va créer le bloc) d'un bloc */
		write_bloc(current_vol, &current_super.super_first_free_bloc, buffer);
		return new;
	}
}

void test_superbloc(){
	unsigned int i=0;
	execute("list");
	printf("Choisir le volume sur lequel nous allons effectuer les opérations: ");
	scanf("%d", &current_vol);
	printf("Size: %d", mbr.disque[current_vol].size);
	for(i=0; i<5; i++){
		printf("\ni = %d", i);
		read_bloc(current_vol, SUPER, (char *) &current_super);	
		printf("\nfirst-free-bloc: %d", current_super.super_first_free_bloc);
		printf("\nnb_free: %d", current_super.super_n_free);
		new_bloc();
	}
}
