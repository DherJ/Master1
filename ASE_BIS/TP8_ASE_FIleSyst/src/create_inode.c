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
#include "../header/inode.h"


int main(int argc, char **argv) {
    
    uint volume, inode_type;
    init_hard();
    
    
    if(argc < 2){
        printf("exe: ./create_bloc volume type\n");
        printf("On which volume: ");
        scanf("%d", &volume);
        printf("Type: ");
        scanf("%d", &inode_type);

    } else {
        volume = atoi(argv[1]);
        inode_type = atoi(argv[2]);
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
    int i = 0;
    uint inumber;
    while(i < 10) {
        inumber = create_inode(inode_type);
        printf("inumber = %d\n", inumber);
        i++;
    }

    save_super();
    save_mbr();
    
    info_on_volume_to_display(volume);
    
    return 0;
}