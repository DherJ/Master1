#include <stdio.h>
#include <stdlib.h>
#include "../header/drive.h"
#include "../header/volume.h"
#include "../header/mbr.h"
#include <assert.h>
#include <string.h>
#include "../header/inode.h"
#include "../header/alloc.h"



void read_inode(uint inumber, struct inode_s *inode){
	read_bloc_n(current_vol, inumber, (uchar *) inode, sizeof(struct inode_s));
}

void write_inode(uint inumber, struct inode_s *inode){
	write_bloc_n(current_vol, inumber, (uchar *) inode, sizeof(struct inode_s));
}

uint create_inode(enum file_type_e type){
	struct inode_s new_inode;
	uint i_number;
	memset(&new_inode, 0, sizeof(struct inode_s));
	new_inode.type = type;
	i_number = new_bloc();
	new_inode.magic = MAGIC_INODE;
	assert(i_number > 0);
	write_inode(i_number, &new_inode);

	return i_number;
}

int delete_inode(uint inumber){
	struct inode_s inode;	
	int i, j;
	uint indirects[NB_BLOC_INDIRECT], double_indirect[NB_BLOC_INDIRECT];
	assert(inumber > 0);

	read_inode(inumber, &inode);

	// Libération des blocs direct
	for(i=0; i<NB_BLOC_DIRECT; i++){
		free_bloc(inode.direct[i]);
	}

	// Libération des blocs indirect
	memset(&indirects, 0, NB_BLOC_INDIRECT);
	read_bloc_n(current_vol, inode.indirect, (uchar*) &indirects, NB_BLOC_INDIRECT);
	for(i=0; i<NB_BLOC_INDIRECT; i++){
		free_bloc(indirects[i]);
	}
	free_bloc(inode.indirect);

	// Libération des blocs avec double indirections
	memset(&indirects, 0, NB_BLOC_INDIRECT);
	memset(&double_indirect, 0, NB_BLOC_INDIRECT);
	read_bloc_n(current_vol, inode.double_indirect, (uchar*) &double_indirect, NB_BLOC_INDIRECT);
	for(i=0; i<NB_BLOC_INDIRECT; i++){
		read_bloc_n(current_vol, double_indirect[i], (uchar*) &indirects, NB_BLOC_INDIRECT);
		for(j=0; j<NB_BLOC_INDIRECT; j++){
			free_bloc(indirects[j]);
		}
		free_bloc(double_indirect[i]);
	}
	free_bloc(inode.double_indirect);
	free_bloc(inumber);

	printf("Free bloc inode %d succeed\n", inumber);
	return inumber;
}


uint init_bloc(uint addr){
	int new_bloc[NB_BLOC_INDIRECT], i;
	read_bloc_n(current_vol, addr, (uchar *)new_bloc, NB_BLOC_INDIRECT);
	for(i=0; i<NB_BLOC_INDIRECT; i++){
		new_bloc[i] = 0;
	}
	write_bloc_n(current_vol, addr, (uchar *)new_bloc, NB_BLOC_INDIRECT);
	return addr;
}


uint vbloc_of_fbloc(uint inumber, uint fbloc, uint do_allocate){
	struct inode_s inode;
	int indirect[NB_BLOC_INDIRECT];
	int d_indirect[NB_BLOC_INDIRECT];
	// On récupère l'inode
	read_inode(inumber, &inode);
	assert(inode.magic == MAGIC_INODE);
	// Si le fbloc que l'on cherche est un bloc direct
	if(fbloc < NB_BLOC_DIRECT){
		printf("\nDIRECT");
		if(inode.direct[fbloc] == 0){ // Si le bloc n'est pas alloué
			if(do_allocate == TRUE) {
				printf(" + allocate");
				inode.direct[fbloc] = init_bloc(new_bloc());
				write_inode(inumber,&inode);
				return inode.direct[fbloc];
			}
			else return BLOC_NULL;
		}
		else return inode.direct[fbloc];
	}

	fbloc -= NB_BLOC_DIRECT;

	// Si le fbloc que l'on cherche est un bloc indirect
	if(fbloc < NB_BLOC_INDIRECT){
		printf("\nINDIRECT");
		if(inode.indirect == 0){ // Si il n'y a pas de bloc indirect alloué
			if(do_allocate == TRUE) {
				printf(" + allocate");
				inode.indirect = init_bloc(new_bloc());
				write_inode(inumber, &inode);
				return inode.indirect;
			}
			else return BLOC_NULL;
		}
		else {
			// On récupère tout les numéros de blocs alloué pour l'inode
			// en mettant le contenu du bloc 'inode.indirect' dans 'indirect[]'
			read_bloc_n(current_vol, inode.indirect, (uchar *) &indirect, NB_BLOC_INDIRECT);
			
			if(indirect[fbloc] == 0){ // Si le bloc recherché n'est pas alloué
				if(do_allocate == TRUE) {
					printf(" + allocate");
					indirect[fbloc] = init_bloc(new_bloc());
					write_bloc_n(current_vol, inode.indirect, (uchar *)&indirect, NB_BLOC_INDIRECT);
					return indirect[fbloc];
				}
				else return BLOC_NULL;
			}
			else return indirect[fbloc];
		}
	}

	fbloc -= NB_BLOC_INDIRECT;

	// Si le fbloc que l'on cherche est un bloc d_indirect
	if(fbloc < NB_BLOC_INDIRECT*NB_BLOC_INDIRECT){
		printf("\nD_INDIRECT");
		// Si il n'y a pas de bloc d_indirect alloué
		if(inode.double_indirect == 0){ 
			if(do_allocate == TRUE) {
				printf(" + allocate");
				inode.double_indirect = init_bloc(new_bloc());
				write_inode(inumber, &inode);
				return inode.double_indirect;
			}
			else return BLOC_NULL;
		}

		int d_indirect_index = fbloc / NB_BLOC_INDIRECT;

		// On récupère le table de numéro de bloc d_indirect
		read_bloc_n(current_vol, inode.double_indirect, (uchar *) &d_indirect, NB_BLOC_INDIRECT);

		if(d_indirect[d_indirect_index] == 0){
			if(do_allocate == TRUE) {
				printf(" + allocate");
				d_indirect[d_indirect_index] = init_bloc(new_bloc());
				write_bloc_n(current_vol, inode.double_indirect, (uchar*)&d_indirect, NB_BLOC_INDIRECT);
			}
			else return BLOC_NULL;
		} 

		read_bloc_n(current_vol, d_indirect_index, (uchar *) &d_indirect, NB_BLOC_INDIRECT);
		d_indirect_index = fbloc % NB_BLOC_INDIRECT;

		if(d_indirect[d_indirect_index] == 0){
			if(do_allocate == TRUE) {
				printf(" + allocate");
				d_indirect[d_indirect_index] = init_bloc(new_bloc());
				write_bloc_n(current_vol, d_indirect[d_indirect_index], (uchar *)&d_indirect, NB_BLOC_INDIRECT);
			}
			else return BLOC_NULL;
		}

		return d_indirect[d_indirect_index];
	}
	return BLOC_NULL;
}


// uint vbloc_of_fblocc(uint inumber, uint fbloc, uint do_allocate){
// 	struct inode_s inode;
// 	int indirect[NB_BLOC_INDIRECT];
// 	int d_indirect[NB_BLOC_INDIRECT];
// 	// On récupère l'inode
// 	read_inode(inumber, &inode);
// 	puts("FFFF");

// 	// Si le fbloc que l'on cherche est un bloc direct
// 	if(fbloc < NB_BLOC_DIRECT){
// 		puts("DIRECT");
// 		if(inode.direct[fbloc] == 0){ // Si le bloc n'est pas alloué
// 			if(do_allocate){
// 				inode.direct[fbloc] = init_bloc(new_bloc());
// 				write_inode(inumber, &inode);
// 			}
// 			else return BLOC_NULL;
// 		}
// 		else return inode.direct[fbloc];
// 	}

// 	fbloc -= NB_BLOC_DIRECT;

// 	// Si le fbloc que l'on cherche est un bloc indirect
// 	if(fbloc < NB_BLOC_INDIRECT){
// 		puts("INDIRECT");
// 		if(inode.indirect == 0){ // Si il n'y a pas de bloc indirect alloué
// 			if(do_allocate){
// 				inode.indirect = init_bloc(new_bloc());
// 				write_inode(inumber, &inode);
// 			}
// 			else return BLOC_NULL;
// 		}
// 		else {
// 			// On récupère tout les numéros de blocs alloué pour l'inode
// 			// en mettant le contenu du bloc 'inode.indirect' dans 'indirect[]'
// 			read_bloc_n(current_vol, inode.indirect, (uchar *) indirect, NB_BLOC_INDIRECT);
			
// 			if(indirect[fbloc] == 0){ // Si le bloc recherché n'est pas alloué
// 				if(do_allocate){
// 					indirect[fbloc] = init_bloc(new_bloc());
// 					write_bloc_n(current_vol, inode.indirect, (uchar *)indirect, NB_BLOC_INDIRECT);
// 				}
// 				else return BLOC_NULL;
// 			}
// 			else return indirect[fbloc];
// 		}
// 	}

// 	fbloc -= NB_BLOC_INDIRECT;

// 	// Si le fbloc que l'on cherche est un bloc d_indirect
// 	if(fbloc < NB_BLOC_INDIRECT*NB_BLOC_INDIRECT){
// 		puts("D_INDIRECT");
// 		// Si il n'y a pas de bloc d_indirect alloué
// 		if(inode.double_indirect == 0){ 
// 			if(do_allocate){
// 				inode.double_indirect = init_bloc(new_bloc());
// 				write_inode(inumber, &inode);
// 			}
// 			else return BLOC_NULL;
// 		}

// 		int d_indirect_index = fbloc / NB_BLOC_INDIRECT;

// 		// On récupère le table de numéro de bloc d_indirect
// 		read_bloc_n(current_vol, inode.double_indirect, (uchar *) d_indirect, NB_BLOC_INDIRECT);

// 		if(d_indirect[d_indirect_index] == 0){
// 			if(do_allocate){
// 				d_indirect[d_indirect_index] = init_bloc(new_bloc());
// 				write_bloc_n(current_vol, inode.double_indirect, (uchar *)d_indirect, NB_BLOC_INDIRECT);
// 			}
// 			else return BLOC_NULL;
// 		} 

// 		read_bloc_n(current_vol, d_indirect_index, (uchar *) indirect, NB_BLOC_INDIRECT);
// 		d_indirect_index = fbloc % NB_BLOC_INDIRECT;

// 		if(indirect[d_indirect_index] == 0){
// 			if(do_allocate){
// 				indirect[d_indirect_index] = init_bloc(new_bloc());
// 				write_bloc_n(current_vol, d_indirect[d_indirect_index], (uchar *)indirect, NB_BLOC_INDIRECT);
// 			}
// 			else return BLOC_NULL;
// 		}

// 		return indirect[d_indirect_index];
// 	}

// 	return BLOC_NULL;
// }

















