/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 2 décembre 2014 
**/

#include "inode.h"
#include "bloc.h"
#include "volume.h"


unsigned int create_inode(enum inode_type_e type) {
	struct inode_s inode;
	memset(0, &inode, sizeof(struct(inode_s));  // initialise la structure avec des 0
	inode.in_type = type;
	inumber = newBloc();
	assert(inumber);
	write_inode(inumber, &inode);
	return inumber;
}

// !!!!!!!!!!!!!!!!!!!! rajouter volume dans les paramètres de read_bloc !!!!!!!!!!!!!!!!!!!!!!!!!!!!
int delete_inode(unsigned int inumber){
	struct inode_s inode;
	read_inode(inumber, &inode);                         // on stock l'inode correspondant au inumber dans la structure inode pour avoir accès aux données de la structure
	// blocs directs
	free_blocs(inode.in_direct, N_DIRECT);          // supprime plusieurs blocs
	// bloc indirect
	int blocs[N_BLOC_PAR_BLOC];                    // tableau qui va contenir les numéros des blocs récupérés à partir du bloc indirect 
	read_bloc(inode.in_indirect, blocs);                // récupère les indice des blocs listés dans les blocs indirects
	free_blocs(blocs, N_BLOC_PAR_BLOC);       // supprime les blocs correspondant aux indices récupérés
	free_bloc(inode.in_indirect);                          // supprime le bloc indirect
	//  bloc double indirect
	read_bloc(inode.in_double_indirect, blocs);   // récupère les indice des blocs listés dans les blocs indirects
	free_blocs(blocs, N_BLOC_PAR_BLOC);       // supprime les blocs correspondant aux indices récupérés
	free_bloc(inode.in_db_indirect);                    // supprime le bloc double indirect
	free_bloc(inumber);                                       // supprime le bloc inode
}


void read_inode(unsigned int inumber, struct inode_s *inode) {
	read_bloc_n(inumber, inode, sizeof(struct inode_s);
}


void write_inode(unsigned int inumber, struct inode_s *inode) {
	
}


/* bloc volume d'un bloc inoeud
  Retourne le numéro de bloc sur le volume qui correspond au fbloc - ième bloc de l'inoeud
  Retourne null si le bloc n'a pas été alloué
*/
unsigned int vbloc_of_fbloc(unsigned int inumber, unsigned int fbloc) {
	read_inode(inumber, &inode);
	int blocs[N_BLOC_PAR_BLOC];                    // tableau qui va contenir les numéros des blocs récupérés à partir du bloc indirect 
	// blocs directs
	if ( fbloc < N_DIRECT ) {
		return inode.in_direct[fbloc];
	}
	fbloc -=  N_DIRECT;
	// bloc indirect
	if ( fbloc < N_BLOC_PAR_BLOC ) {
		if ( inode.in_indirect == BLOC_NULL ) {  
			return BLOC_NULL;
		}
		read_bloc(inode.in_indirect, blocs);      // récupère les indice des blocs listés dans les blocs indirects
		return blocs[fbloc];
	}
	fbloc -= N_BLOC_PAR_BLOC;
	// bloc double indirect
	if ( fbloc < N_BLOC_PAR_BLOC ) {
		if ( inode.in_db_indirect == BLOC_NULL ) {  
			return BLOC_NULL;
		}
		read_bloc(inode.in_db_indirect, blocs);      // récupère les indice des blocs listés dans les blocs doubles indirects
		return blocs[fbloc];
	}
}

