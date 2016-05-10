#ifndef COMMON_H_INCLUDE
#define COMMON_H_INCLUDE

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "hardware.h"
#include "volume.h"



struct _cmd {
    char *name;
    void (*fun) ();
    char *comment;
};

extern void frmt();
extern void dump(char *buffer, const unsigned int buffer_size, const unsigned int mode);
extern void dmps();

static struct _cmd commands [] = {
    {"list", list, "Table des partitions"},
    {"new", new, "Crée une nouvelle partition"},
    {"delete", delete,	"Supprime une partition"},
    {"format", frmt, "Formate une partition"},
    {"dmps", dmps, "Dump secteur"},
    {"testSB", testSB, "Test superbloc"},
    {"saveMBR", saveMBR, "Sauvegarde du MBR"},
    {"saveMBRxit", saveMBRxit, "Sauvegarde du MBR et quitte"},
    {"exit", xit, "Quitte sans sauvegarde"},
    {"help", help, "Affiche l'aide"},
    {0, commandError, "Commande inconnue, utilisez la commande help pour voir la liste des commandes"}
} ;

#endif
