//
//  mbr.c
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by florian on 19/10/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include "../header/mbr.h"
#include "../header/drive.h"

struct MBR_s mbr;


int load_mbr(){
    read_sector_n(0, 0, (unsigned char*)&mbr, sizeof(mbr));
    if(mbr.magic != (unsigned int) MAGIC){ // Si la partition n'est pas initialisé
        mbr.magic = (unsigned int) MAGIC;
        mbr.nb_vol = 0; // On a initialisé un disque qui
        save_mbr(); // Sauvegarde après initialisation
        return 0;  // n'était pas formaté ni initialisé
    }
    return 1;
}


void save_mbr(){
    write_sector_n(0, 0, (unsigned char*)&mbr, sizeof(mbr));
}

