/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 4 novembre 2014 
**/


#ifndef BLOC_H_INCLUDED
#define BLOC_H_INCLUDED

#define SUPER_MAGIC 0xF4B    /* identifie un superBloc */
#define SUPER 0


static int current_vol; /* variable qui correspond au volume courrant sur lequel on travail */
static struct superBloc current_super;  /* structure superBloc qui correspond au volume courrant */


	/* structure d'un bloc vide présent dans un volume */
struct freeBloc {
	unsigned int fb_n_free;
	struct freeBloc *fb_next;          /*  = 0 si il n'y a pas de bloc suivant  */
};	

	/* structure du bloc comportant les informations des bloc d'un volume */
struct superBloc {
	unsigned int super_magic;
	unsigned int super_first_free_bloc;
	unsigned int super_n_free;
};


	/* Prototypes des fonctions */
	void init_super(unsigned int vol);
	int load_super(unsigned int vol);
	void save_super();
	int new_bloc();
	void test_superbloc();
	/* extern void free_bloc(unsigned int bloc); */

#endif
