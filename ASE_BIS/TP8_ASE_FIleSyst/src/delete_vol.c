//
//  format_vol.c
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by dhersin on 26/10/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include "../header/drive.h"
#include "../header/volume.h"
#include "../header/mbr.h"

int main(int argc, char **argv){
	
	init_hard();
	int volume, value;
	
    if(load_mbr() == 1){
        printf("Okay, partition ready to be use\n");
    } else {
        printf("Partition not initialized.. Now it is !");
    }
    
	if(argc < 3){
        printf("exe: ./delete_vol volume value\n");
        displayAllVolume();
        printf("Number of volume to delete: ");
        scanf("%d", &volume);
		printf("Value to format: ");
        scanf("%d", &value);
    } else {
        volume = atoi(argv[1]);
		value = atoi(argv[2]);
    }
	
	assert(volume >= 0 && volume < MAX_VOL);

	if(delete_vol(volume) == 1){
		printf("Volume %d deleted, now see which volume is the new n°%d\n", volume, volume);
        displayAllVolume();
	}
	
	save_mbr();
	
	return 0;
}