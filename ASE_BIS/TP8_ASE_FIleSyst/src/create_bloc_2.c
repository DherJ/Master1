//
//  create_bloc_2.c
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by jerome on 07/11/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "../header/alloc.h"
#include "../header/volume.h"
#include "../header/mbr.h"


int rand_a_b(int a, int b) {

   static int first = 0;
   
   if (first == 0) {
      srand (time (NULL));
      first = 1;
   }

    if(b-a == 0)
        return 1;
    else return rand()%(b-a) + a;
}


int main(int argc, char **argv) {
    
    uint volume;
    init_hard();
    int nombre_aleatoire = 0;
    srand(time(NULL)); 
    if(argc < 2) {
        printf("exe: ./create_bloc_2 volume\n");
        printf("On which volume: ");
        scanf("%d", &volume);
    } else {
        volume = atoi(argv[1]);
    }
    
    assert(volume < MAX_VOL);
    
    if(load_mbr() == 1) {
        printf("Okay, partition ready to be use\n");
    } else {
        printf("Partition not initialized.. Now it is !");
    }
    
    info_on_volume_to_display(volume);
    
    if(load_super(volume) == 1) {
        printf("Okay, superbloc of volume n°%d loaded\n", volume);
    } else {
        printf("Superbloc not initialized.. :(\n");
    }
    
    while (superbloc.nb_free != 0) {
        printf("New bloc: %d\n", new_bloc());
    }
    
    printf("There is no more freebloc available... :(\n");
    
    int i;

    // free bloc en commençant par le milieu des blocs jusqu'au bloc 1 sans prendre le milieu
    for(i = ((mbr.vol[volume].nb_bloc - 1) / 2) + 1; i < (mbr.vol[volume].nb_bloc - 1); i++) {
        printf("free_bloc(%d)\n", i);
        free_bloc(i);
        display_free_blocs();
    }

    // free bloc en commençant par la fin des blocs jusqu'au milieu sans prendre le milieu
    i = 0;
    for(i = ((mbr.vol[volume].nb_bloc - 1) / 2) - 1; i > 0; i--) {
        printf("free_bloc(%d)\n", i);
        free_bloc(i);
        display_free_blocs();
        if(i == 1)
            break;
    }

    // free bloc du bloc du milieu
    i = (mbr.vol[volume].nb_bloc - 1) / 2;
    printf("free_bloc(%d)\n", i);
    free_bloc(i);
    display_free_blocs();

    save_super();
    save_mbr();
    
    printf("All blocs are now available and free. :)\n");
    
    info_on_volume_to_display(volume);
    
    return 0;
}