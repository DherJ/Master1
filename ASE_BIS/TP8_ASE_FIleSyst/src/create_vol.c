//
//  create_vol.c
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by florian on 26/10/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include "../header/drive.h"
#include "../header/volume.h"
#include "../header/mbr.h"

int main(int argc, char **argv){
    
    uint size, type_no;
    enum type_e type;
    
    init_hard();
    
    
    if(argc < 2){
        printf("exe: ./create_vol size\n");
        printf("Volume' size: ");
        scanf("%d", &size);
    } else {
        size = atoi(argv[1]);
    }
    
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
    
    //info_on_volume_to_display(0);
    assert(mbr.nb_vol < MAX_VOL);
    
    if(create_new_volume(size, type) != 0 )
        printf("New volume correctly created\n");
    else printf("Impossible to create the new volume...\n");
    
    displayAllVolume();
    
    save_mbr();
    
    return 0;
}