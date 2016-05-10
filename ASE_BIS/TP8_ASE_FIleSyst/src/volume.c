//
//  volume.c
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by florian on 19/10/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include "../header/drive.h"
#include "../header/volume.h"
#include "../header/mbr.h"
#include <assert.h>
#include <string.h>

struct superbloc_s superbloc;


uint cylinder_of_bloc(uint vol, uint bloc){
    assert(mbr.magic == (unsigned) MAGIC); // Check si on a initialisé la struct
    assert(vol < mbr.nb_vol);
    assert(bloc < mbr.vol[vol].nb_bloc);
    return mbr.vol[vol].first_cylinder + ((bloc + mbr.vol[vol].first_sector) / HDA_MAXSECTOR);
}

uint sector_of_bloc(uint vol, uint bloc){
    assert(mbr.magic == (unsigned) MAGIC); // Check si on a initialisé la struct
    assert(vol < mbr.nb_vol);
    assert(bloc < mbr.vol[vol].nb_bloc);
    return (bloc + mbr.vol[vol].first_sector) % HDA_MAXSECTOR;
}

void read_bloc(uint vol, uint nbloc, uchar *buffer){
    uint cylinder = cylinder_of_bloc(vol, nbloc);
    uint sector = sector_of_bloc(vol, nbloc);
    read_sector(cylinder, sector, buffer);
}

void read_bloc_n(uint vol, uint nbloc, uchar *buffer, uint size) {
    uint cylinder = cylinder_of_bloc(vol, nbloc);
    uint sector = sector_of_bloc(vol, nbloc);
    read_sector_n(cylinder, sector, buffer, size);
}

void write_bloc_n(uint vol, uint nbloc, uchar *buffer, uint size) {
    uint cylinder = cylinder_of_bloc(vol, nbloc);
    uint sector = sector_of_bloc(vol, nbloc);
    write_sector_n(cylinder, sector, buffer, size);
}

void write_bloc(uint vol, uint nbloc, uchar *buffer) {
    uint cylinder = cylinder_of_bloc(vol, nbloc);
    uint sector = sector_of_bloc(vol, nbloc);
    write_sector(cylinder, sector, buffer);
}

void format_bloc(uint vol, uint nbloc, uint value) {
    uint cylinder = cylinder_of_bloc(vol, nbloc);
    uint sector = sector_of_bloc(vol, nbloc);
    format_sector(cylinder, sector, value);
}

int create_new_volume(uint size, enum type_e type) {
    if(mbr.nb_vol +1 >= MAX_VOL) {
        printf("Stop, max number of volumes reached\n");
        return 0;
    }
    struct volume_s new_vol;
    uchar sectors[HDA_MAXCYLINDER][HDA_MAXSECTOR] = {{0}};
    char name_superbloc[32], number_superbloc[5];
    uint i, j, cyl, sect, nbFree = 0;
    // Put the sectors already use to 1
    // MBR
    sectors[0][0] = 1;
    for (i = 0; i < mbr.nb_vol; i++) {
        for (j = 0; j < mbr.vol[i].nb_bloc; j++) {
            cyl = cylinder_of_bloc(i, j);
            sect = sector_of_bloc(i, j);
            sectors[cyl][sect] = 1;
        }
    }

    // Looking for space to insert the new volume
    for(i = 0; i<(HDA_MAXCYLINDER*HDA_MAXSECTOR); i++) {
        cyl = i / HDA_MAXSECTOR;
        sect = i % HDA_MAXSECTOR;
        
        // If free
        if(sectors[cyl][sect] == 0)
            nbFree ++;
        
        // If there is enought memory to insert our new volume
        if(nbFree >= size) {
            // Go back to the start of the free space
            cyl = (i-(size-1)) / HDA_MAXSECTOR;
            sect = (i-(size-1)) % HDA_MAXSECTOR;
            
            new_vol.first_cylinder = cyl;
            new_vol.first_sector = sect;
            new_vol.nb_bloc = size;
            new_vol.type = type;
            mbr.vol[mbr.nb_vol] = new_vol;
            (mbr.nb_vol)++;
            strcat(name_superbloc, "SUPERBLOC-");
            sprintf(number_superbloc, "%d", mbr.nb_vol-1);
            strcat(name_superbloc, number_superbloc);
            init_super(mbr.nb_vol-1, name_superbloc);
            return 1;
        }
    }
    return 0;
}


int delete_vol(int volume_to_delete) {
    uint i = 0;
    if(volume_to_delete > mbr.nb_vol) {
        printf("Error, there is only %d volumes\n", mbr.nb_vol);
        return 0;
    }

    // Format the volume to delete before remove it
    for(i = 0; i < mbr.vol[volume_to_delete].nb_bloc; i++) {
        format_bloc(volume_to_delete, i, 0);
    }

    // On écrase le volume à supprimer
    for(i = volume_to_delete; i < mbr.nb_vol; i++) {
        mbr.vol[i] = mbr.vol[i +1];
    }

    // Maj du nombre de volume
    mbr.nb_vol--;
    printf("Ok, volume deleted\n");
    return 1;
}



// Superbloc
void init_super(uint vol, char *nom) {
    // read_bloc_n(vol, 0, (uchar *) &superbloc, sizeof(struct superbloc_s));
    load_super(vol);
    if(superbloc.magic == MAGIC_SUPERBLOC){
        printf("Wait, superbloc already initialized :)\n");
        exit(EXIT_FAILURE);
    }
    
    else {
        superbloc.magic = MAGIC_SUPERBLOC;
        strcpy(superbloc.nom, nom);
        superbloc.first_free_bloc = 1;
        superbloc.root = 0;
        superbloc.nb_free = mbr.vol[vol].nb_bloc-1;
        int i = 0;
        for(i = 1; i<mbr.vol[vol].nb_bloc; i++) {
            struct free_bloc_s fb;
            if(i == mbr.vol[vol].nb_bloc-1)
                fb.next_free_bloc = 0;
            else fb.next_free_bloc = i+1;
                
            write_bloc_n(vol, i, (uchar *) &fb, sizeof(struct free_bloc_s));
        }
        save_super();
    }
}

int load_super(uint vol) {
    current_vol = vol;
    read_bloc_n(current_vol, 0, (uchar *) &superbloc, sizeof(struct superbloc_s));
    if(superbloc.magic == MAGIC_SUPERBLOC)
        return 1;
    else return 0;
}

void save_super() {
    write_bloc_n(current_vol, 0, (uchar *) &superbloc, sizeof(struct superbloc_s));
}

/** Fonction qui donne l'espace disponible en octets du disque courrant **/
int free_space_of_volume() {
    return (superbloc.nb_free * HDA_SECTORSIZE);
}

/** Fonction qui donne l'epace occupé en octets du disque courrant **/
int space_consumed_of_volume() {
    return ( (mbr.vol[current_vol].nb_bloc * HDA_SECTORSIZE) - free_space_of_volume() );
}

/** Fonction qui donne l'epace total en octets du disque courrant **/
int total_space_of_volume() {
    return (mbr.vol[current_vol].nb_bloc * HDA_SECTORSIZE);
}


// Fonctions d'affichages

/** Affiche les infos de chaque volume **/
void displayAllVolume() {
    int i = 0;
    for(i = 0; i<mbr.nb_vol; i++) {
        info_on_volume_to_display(i);
    }
    printf("\n\n");
}

/** Affiche l'espace occupé/disponible sur le disque courrant **/
void display_space_on_volume() {
    int i = 0;
    int pourcentage_free = (100 * free_space_of_volume()) / total_space_of_volume();
    int pourcentage_consumed = 100 - pourcentage_free;

    for(i = 0; i < 70; i++) {
        printf("-");
    }
    printf("\n");
    printf("\t|");

    for(i = 0; i < pourcentage_consumed / 20; i++) {
        printf(" ");
    }
    printf("Consumed: %d octets (%d%%)", space_consumed_of_volume(), pourcentage_consumed);
    for(i = 0; i < pourcentage_consumed / 20; i++) {
        printf(" ");
    }
    printf("|");

    for(i = 0; i < pourcentage_free / 20; i++) {
        printf(" ");
    }
    printf("Free: %d octets (%d%%)", free_space_of_volume(), pourcentage_free);
    for(i = 0; i < pourcentage_free / 20; i++) {
        printf(" ");
    }
    printf("|");
    printf("\n");
}

/** Affiche les informations du disque courrant **/
void info_on_volume_to_display(int volume) {
    load_super(volume);
    struct volume_s vol = mbr.vol[volume];
    printf("\n----------------------------------------------------------------------\n");
    printf("Volume n°%d\n", volume);
    printf("Cylinder n°%d\tSector n°%d\n", vol.first_cylinder, vol.first_sector);
    printf("Number of bloc: %d\t", vol.nb_bloc);
    printf("Number freebloc: %d\n", superbloc.nb_free);

    display_space_on_volume();

    if(vol.type == BASE)
        printf("Type: Base\n");
    else if(vol.type == ANNEXE)
        printf("Type: Annex\n");
    else if(vol.type == AUTRE)
        printf("Type: Other\n");
    else
        printf("Type: Unknown\n");
    printf("----------------------------------------------------------------------\n");
}

/** Affiche le contenu d'un bloc **/
void display_bloc(uchar *buffer) {
    int i = 0;
    int j = 0;
    for(i=0; i<HDA_SECTORSIZE / 16; i++) {
        printf("0%03d: ", i * 16);
        for(j=0; j<16; j++) {
            printf(" %02x", buffer[i*16 + j]);
        }
        printf("\n");
    }
    printf("-----\n");
}

/** Affiche la liste des blocs libres **/
void display_free_blocs() {
    int i = 0;
    struct free_bloc_s fb;
    read_bloc_n(current_vol, superbloc.first_free_bloc, (uchar *)&fb, sizeof(struct free_bloc_s));
    printf("List of free blocs : ");
    printf(" %d ", superbloc.first_free_bloc);
    while(i < superbloc.nb_free) {
         printf(" %d ", fb.next_free_bloc);
         read_bloc_n(current_vol, fb.next_free_bloc, (uchar *)&fb, sizeof(struct free_bloc_s));
         i++;
    }
    printf("\n\n");
}