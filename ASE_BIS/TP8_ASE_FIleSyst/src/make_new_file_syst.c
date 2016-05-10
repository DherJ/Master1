//
//  make_new_file_syst.c
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by jerome on 08/11/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include "../header/alloc.h"
#include "../header/volume.h"
#include "../header/mbr.h"

int main(int argc, char **argv) {
    
    uint volume;
    init_hard();
    
    
    if(argc < 2) {
        printf("exe: ./mknfs volume\n");
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
    
    if(load_super(volume) == 1){
        printf("Okay, superbloc of volume n°%d loaded\n", volume);
    } else {
        printf("Superbloc not initialized.. :(\n");
    }
    char name[32] = "SUPERBLOC-DEFAULT";
    init_super(volume, name);
    
    save_super();
    save_mbr();
    
    return 0;
}