//
//  create_bloc.c
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by florian on 03/11/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include "../header/alloc.h"
#include "../header/volume.h"
#include "../header/mbr.h"

int main(int argc, char **argv) {
    
    uint volume;
    init_hard();
    
    
    if(argc < 2){
        printf("exe: ./create_bloc volume\n");
        printf("On which volume: ");
        scanf("%d", &volume);
    } else {
        volume = atoi(argv[1]);
    }
    
    assert(volume < MAX_VOL);
    
    if(load_mbr() == 1){
        printf("Okay, partition ready to be use\n");
    } else {
        printf("Partition not initialized.. Now it is !");
    }
    
    info_on_volume_to_display(volume);
    
    if(load_super(volume) == 1){
        printf("Okay, superbloc of volume n°%d loaded\n", volume);
    } else {
        printf("Superbloc not initialized.. :(\n");
    }
    
    while (superbloc.nb_free != 0) {
        printf("New bloc: %d\n", new_bloc());
    }
    
    printf("There is no more freebloc available... :(\n");
    
    
    while (mbr.vol[volume].nb_bloc-1 != superbloc.nb_free) {
        printf("free_bloc(%d)\n", (mbr.vol[volume].nb_bloc-1) - superbloc.nb_free);
        free_bloc((mbr.vol[volume].nb_bloc-1) - superbloc.nb_free);
    }
    
    save_super();
    save_mbr();
    
    printf("All blocs are now available and free. :)\n");
    
    info_on_volume_to_display(volume);
    
    return 0;
}