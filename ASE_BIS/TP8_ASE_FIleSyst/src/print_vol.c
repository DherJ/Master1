#include <stdio.h>
#include <stdlib.h>
#include "../header/drive.h"
#include "../header/volume.h"
#include "../header/mbr.h"


int main(int argc, char **argv){
    init_hard();
    int volume, i;
    uchar buffer[HDA_SECTORSIZE];
    
    if(argc < 2) {
        printf("exe: ./print_vol volume\n");
        printf("Numero du volume: ");
        scanf("%d", &volume);
    } else {
        volume = atoi(argv[1]);
    }
    
    assert(volume >= 0 && volume < MAX_VOL);
    
    if(load_mbr() == 1){
        printf("Okay, partition ready to be use\n");
    } else {
        printf("Partition not initialized.. Now it is !");
    }
    
    displayAllVolume();

    for (i = 0; i<mbr.vol[volume].nb_bloc; i++) {
        read_bloc(volume, i, buffer);
        display_bloc(buffer);
    }
    
    
    return 0;
}