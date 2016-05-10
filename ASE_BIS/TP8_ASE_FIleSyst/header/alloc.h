//
//  alloc.h
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by florian on 03/11/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#ifndef alloc_h
#define alloc_h

#include "volume.h"


extern int current_vol;
extern struct superbloc_s superbloc;
extern uint new_bloc();
extern void free_bloc(uint bloc);

#endif /* alloc_h */
