//
//  mbr.h
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by florian on 19/10/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#ifndef mbr_h
#define mbr_h

#include "volume.h"


#define MAGIC 0xDEADBEEF

struct MBR_s {
    unsigned int magic;
    unsigned int nb_vol;
    struct volume_s vol[MAX_VOL];
};

extern int load_mbr();
extern void save_mbr();

#endif /* mbr_h */
