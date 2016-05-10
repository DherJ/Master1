//
//  i_file.C
//  TP3-FileSystem-Malapel&Dhersin
//
//  Created by jerome on 15/11/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include "../header/inode.h"
#include "../header/ifile.h"


uint create_ifile(enum file_type_e type) {
	return create_inode(type);
}

int delete_ifile(uint inumber) {
	delete_inode(inumber);
	return 1;
}

int open_ifile(file_desc_s *fd, uint inumber) {
	if(inode.magic_inode != MAGIC_INODE) {
		printf("Cannot open file who is not create!")
		return 0;
	} else {
			struct inode_s inode;
			read_inode(inumber, &inode);
			fd->size = inode.inode.size;
			fd->offset = 0;
			fd->inumber = inumber;
			fd->dirty = 0;
			memset(fd->buffer, 0, HDA_SECTORSIZE);
			return 1;
		}
}

void close_ifile(file_desc_s *fd) {
	flush_ifile(fd);
	free(fd);
}

/* vide le buffer et écrit les données du buffer sur le disque */
void flush_ifile(file_desc_s *fd) {
	if(fd->dirty) {
		write_bloc(current_vol, vbloc_of_fbloc(fd->inumber, (fd->offset / HDA_SECTORSIZE), fd->buffer));
		fd->dirty = 0;
	}
}

/* relatif */
void seek_ifile(file_desc_s *fd, int r_offset) {
	assert(r_offset >= 0);
	int old_position = fd->offset;
	fd->offset += r_offset;
	if(bloc_of_pos(fd->offset) != bloc_of_pos(old_position)) { // on change de bloc 
		flush_ifile(fd);
		/* on récupère le bloc qui correspond à la position dans le fichier */
		fbloc = bloc_of_pos(fd->offset);
		/* on récupère le bloc disque qui correspond au bloc de la position dans le fichier */
		vbloc = vbloc_of_fbloc(fd->inumber, fbloc, FALSE);
		if (vbloc == FALSE)
	   		 /* si vbloc correspond au bloc 0 sur le disque (le super_bloc) -> on réinitialise le buffer */
	   		 memset(fd->buffer, 0, HDA_SECTORSIZE);
		else read_bloc(current_vol, vbloc, fd->buffer); // on charge le buffer avec les données du bloc qui correspond à la position dans le fichier
	}
}

/* absolu */
void seek2_ifile(file_desc_s *fd int a_offset) {
	assert(a_offset >= 0);
	fd->offset = a_offset;
}

int readc_ifile(file_desc_s *fd) {
	char c;
	/* si on est à la fin du fichier */
	if(fd->offset > fd->size) { // si on est à la fin du fichier
		flush_ifile(fd);
		return READ_EOF;
	}
	// on lit l'octet */
	c = fd->buffer[fd->offset];
	/* on incrémente l'offset de 1 pour passer à l'octet suivant */
	seek_ifile(fd, 1);
	return c;
}

int writec_ifile(file_desc_s *fd, char c) {
	uint ibloc;
	fd->buffer[ibloc_of_pos(fd->offset)] = c;

	/* first write in the bloc ? ensure the data bloc allocation */
    if (fd->dirty == FALSE) {
        ibloc = vbloc_of_fbloc(fd->inumber, bloc_of_pos(fd->offset), TRUE);
        if (ibloc == FALSE) 
            return RETURN_FAILURE;
        fd->dirty = TRUE;
    }
    /* si le buffer est remplit */
    if (ibloc_of_pos(fd->offset) == (HDA_SECTORSIZE - 1)) {
    	/* on charge le nouveau bloc fichier */
        ibloc = vbloc_of_fbloc(fd->inumber, bloc_of_pos(fd->offset), FALSE);
	    /* on écrit les données du buffer dans le bloc qui correspond à la position du curseur dans le fichier */    
		write_bloc(current_vol, ibloc, fd->buffer);
		/* read the new buffer */
		ibloc = vbloc_of_fbloc(fd->inumber, bloc_of_pos(fd->offset + 1), FALSE);
		/* si le bloc n'est pas alloué */
		if (ibloc == FALSE) 
		    memset(fd->buffer 0, HDA_SECTORSIZE);
		else
		    read_bloc(current_vol, ibloc, fd->buffer);
		fd->dirty = FALSE;
    }
    
    /* update the file cursor and size */
    if (fd->size < fd->offset)
		fd->size = fd->offset;
    (fd->offset)++;
    
    /* the position of the written char */
    return (fd->offset - 1);
}

int read_ifile(file_desc_s *fd, void *buf, uint nbyte) {

}

int write_ifile(file_desc_s *fd, const void *buf, uint nbyte) {

}	



/** Fonctions utilitaires **/

/* retourne le numéro de bloc qui correspond à la position du curseur dans le fichier */
int bloc_of_pos(int position_in_file) {
	return ((position_in_file) / HDA_SECTORSIZE);
}

/* retourne le numéro de bloc d'un fichier qui correspond à la position dans le fichier */
int ibloc_of_pos(position_in_file) {
	return ((position_in_file) % HDA_SECTORSIZE);
}