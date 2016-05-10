//
//  test_vofb.c
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by jerome on 15/11/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include "../header/alloc.h"
#include "../header/volume.h"
#include "../header/mbr.h"
#include "../header/inode.h"

int main(int argc, char **argv) {
    
    uint volume;
    init_hard();
    
    
    if(argc < 2){
        printf("exe: ./test_vofb volume\n");
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
    
    uint inumber1, inumber2, inumber3;
    inumber1 = create_inode(FICHIER);
    inumber2 = create_inode(FICHIER);
    inumber3 = create_inode(FICHIER);

    printf("\nNB_BLOC_DIRECT: %d\n", (int)NB_BLOC_DIRECT);
    printf("NB_BLOC_INDIRECT: %d\n", (int)NB_BLOC_INDIRECT);

    printf("\n\nFiles created: %d & %d & %d\n", inumber1, inumber2, inumber3);

    printf(" %d\n", vbloc_of_fbloc(10, NB_BLOC_DIRECT - 5, 1)); // 1 = allouer si le bloc n'est pas occupé
    printf(" %d\n", vbloc_of_fbloc(11, NB_BLOC_DIRECT + NB_BLOC_INDIRECT - 10, 1));
    printf(" %d\n", vbloc_of_fbloc(12, (NB_BLOC_INDIRECT * NB_BLOC_INDIRECT) - 9, 1));

    info_on_volume_to_display(volume);

    save_super();
    save_mbr();
    
    
    return 0;
}