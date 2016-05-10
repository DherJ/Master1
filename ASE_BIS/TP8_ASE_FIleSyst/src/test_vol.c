//
//  test_vol.c
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by jerome on 30/10/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include "../header/drive.h"
#include "../header/volume.h"
#include "../header/mbr.h"

int main(int argc, char **argv){
    init_hard();
    int volume, i;
	uint size, type_no;
	enum type_e type;
    uchar buffer[HDA_SECTORSIZE];
	
	/************************************/
	/**     Création du volume         **/
	/************************************/
	// saisie de la taille du volume à créer
	printf("Enter the size of the new volume: \n");
    scanf("%d", &size);
	
	// saisie du type du volume à créer
	printf("Choose the type of the new volume: \n");
    printf("\tBASIC --> 0\n\tANNEX --> 1\n\tOTHER --> 2\nType: ");
    scanf("%d", &type_no);
	
	if(type_no == 0)
        type = BASE;
    else if(type_no == 1)
        type = ANNEXE;
    else type = AUTRE;
    
    if(load_mbr() == 1){
        printf("Okay, partition ready to be use\n");
    } else {
        printf("Partition not initialized.. Now it is !");
    }
	
	assert(mbr.nb_vol < MAX_VOL);
	
	// création et sauvegarde du volume    
	if(create_new_volume(size, type) == 1)
        printf("New volume correctly created\n");
    else printf("Impossible to create the new volume...\n");

    displayAllVolume();
	
	
	/************************************/
	/**    Affichage du volume  créé   **/
	/************************************/
    printf("\nHere it is the new created volume: \n");
	volume = mbr.nb_vol-1;
	assert(volume >= 0 && volume < MAX_VOL);
    for (i = 0; i < mbr.vol[volume].nb_bloc; i++) {
        read_bloc(volume, i, buffer);
        display_bloc(buffer);
    }

	/************************************/
	/**     Formatage du volume créé   **/
	/************************************/
    printf("\nStart deleting volume n°%d\n", volume);
    delete_vol(volume);
	
    displayAllVolume();
    
	save_mbr();
	
	return 0;
}