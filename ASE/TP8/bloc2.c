/* Nécessaire pour les constantes HDA + alias + include de base */
#include "common.h"
#include "bloc2.h"
#include "drive.h"
#include "mbr.h"	

void init_super( unsigned int vol ){
	printf("Début de l'initialisation du SUPER du volume\n");
	int free_size = mbr.disque[vol].size - 1;
	struct freeBloc_s fb;
	current_super.super_magic = SUPER_MAGIC;
	current_super.super_first_free_bloc = 1;
	current_super.super_nb_free = free_size;
	write_bloc_n(vol, SUPER, (char*) &current_super, sizeof( struct superBloc_s ));
	fb.nb_free= free_size;
	fb.next_free = 0;
	write_bloc_n(vol, 1, (char*) &fb, sizeof( struct freeBloc_s ));
	printf("initialisation du SUPER: ok\n");
}

int load_super( unsigned int vol ){
	printf("Chargement de SUPER\n");
	read_bloc_n(vol, SUPER, (char*) &current_super, sizeof(struct superBloc_s));
	return current_super.super_magic == SUPER_MAGIC;
}

void save_super(){
	write_bloc_n(current_vol, SUPER, (char*) &current_super, sizeof(struct superBloc_s));
	printf("Sauvegarde de SUPER: ok\n");
}



unsigned int new_bloc(){
	printf("Création d'un nouveau bloc...\n");
	unsigned int new;

	// Si il n'y a plus de place dispo dans le volume
	if( current_super.super_nb_free == 0 )
		return 0;

	struct freeBloc_s fb;
	read_bloc_n(current_vol, current_super.super_first_free_bloc, (char*) &fb, sizeof(struct freeBloc_s));
	new = current_super.super_first_free_bloc;

	// Si il ne reste qu'un seul bloc libre
	if( fb.nb_free == 1 )
		current_super.super_first_free_bloc = fb.next_free;
	// Cas général, si il reste plusieurs blocs libre
	else {
		current_super.super_nb_free --;
		fb.nb_free --;
		write_bloc_n(current_vol, current_super.super_first_free_bloc, (char*) &fb, sizeof(struct freeBloc_s));
	}

	printf("Création du bloc: ok\n");
	return new;
}

void free_bloc(unsigned int bloc){
	if( bloc == 0 ){
		printf("Impossible de libérer le bloc 0\n");
		return;
	} 
	printf("Libération d'un bloc...\n");
	struct freeBloc_s fb;
	fb.nb_free = 1;
	fb.next_free = current_super.super_first_free_bloc;
	current_super.super_first_free_bloc = bloc;
	current_super.super_nb_free ++;
	write_bloc_n(current_vol, bloc, (char*)&fb, sizeof(struct freeBloc_s));
	printf("Libération du bloc: ok\n");
}

void test_superbloc(){
	int i=0;	
	execute("list");
	printf("Choisir le volume sur lequel nous allons effectuer les opérations: ");
	scanf("%d", &current_vol);
	
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






