//
//  volume.h
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by florian on 19/10/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#ifndef volume_h
#define volume_h

#include "drive.h"

#define MAX_VOL 8

int current_vol;

enum type_e {BASE, ANNEXE, AUTRE};

struct volume_s{
    uint first_cylinder;
    uint first_sector;
    uint nb_bloc;
    enum type_e type;
};


#define MAGIC_SUPERBLOC 0xBEEFDEAD

struct superbloc_s {
    uint nSerie;
    int magic;
    char nom[32];
    uint first_free_bloc;
    uint root;
    uint nb_free;
};

struct free_bloc_s {
    uint next_free_bloc;
};


extern struct MBR_s mbr;


extern void read_bloc(uint vol, uint nbloc, uchar *buffer);
extern void write_bloc(uint vol, uint nbloc, uchar *buffer);
extern void read_bloc_n(uint vol, uint nbloc, uchar *buffer, uint size);
extern void write_bloc_n(uint vol, uint nbloc, uchar *buffer, uint size);
extern void format_bloc(uint vol, uint nbloc, uint value);
extern int create_new_volume(uint size, enum type_e type);
extern void info_on_volume_to_display(int volume);
extern void display_bloc(uchar *buffer);
extern int delete_vol(int volume_to_delete);
extern void displayAllVolume();
extern int load_super(uint vol);
extern void save_super();
extern uint new_bloc();
extern void init_super(uint vol, char *nom);
extern int total_space_of_volume();
extern int space_consumed_of_volume();
extern int free_space_of_volume();
extern void display_space_on_volume();
extern void display_free_blocs();


#endif /* volume_h */
