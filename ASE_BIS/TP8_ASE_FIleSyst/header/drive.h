#ifndef drive_h
#define drive_h

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "hardware.h"
#include "hw.h"

#define ENABLE_HDA 1                 //ENABLE_HD = 0 => simulation du disque désactivée
#define HDA_FILENAME "vdiskA.bin"    //nom du fichier de stockage du disque simulé
#define HDA_CMDREG 0x3F6             //registre de commande du disque maitre
#define HDA_DATAREGS 0x110           //base des registres de données (r,r+1,r+2,...r+7)
#define HDA_IRQ 14                   //Interruption du disque
#define HDA_MAXCYLINDER 16           //Nombre de pistes du disque maître
#define HDA_MAXSECTOR 16             //Nombre de secteurs du disque maître
#define HDA_SECTORSIZE 256           //Taille (en octet) d'un secteur du disque maitre

#define CMD_SEEK        0x02
#define CMD_READ        0x04
#define CMD_WRITE       0x06
#define CMD_FORMAT      0x08

typedef unsigned int uint;
typedef unsigned char uchar;



/*
 Fonction qui initialise la librairie hardware ainsi que les interruptions
 */
extern void init_hard();


/*
 Déplacement vers un cylindre et secteur donné en parametre
 */
extern void seek(uint cylinder, uint sector);


/*
 Fonction qui lit la partie du disque demandée par la valeur des paramètres,
 cylinder: cylindre que l'on veut lire
 sector: secteur sque l'on veut lire
 buffer: contient les informations lu a la fin de la fonction
 size: la taille du buffer (taille des données à lire)
 */
extern void read_sector_n(uint cylinder, uint sector, uchar *buffer, uint size);


/*
 Fonction qui lit la partie du disque demandée par la valeur des paramètres (cylinder & sector),
 cylinder: cylindre que l'on veut lire
 sector: secteur sque l'on veut lire
 buffer: contient les informations lu a la fin de la fonction
 */
extern void read_sector(uint cylinder, uint sector, uchar *buffer);


/*
 Fonction qui écrit sur une certaine partie du disque le buffer passé en paramètre.
 cylinder: cylindre sur lequel on veut écrire
 sector: secteur sur lequel on veut écrire
 buffer: contient les informations à écrire
 size: la taille du buffer (taille des données à écrire)
 */
extern void write_sector_n(uint cylinder, uint sector, uchar *buffer, uint size);


/*
 Fonction qui écrit sur une certaine partie du disque le buffer passé en paramètre.
 cylinder: cylindre sur lequel on veut écrire
 sector: secteur sur lequel on veut écrire
 buffer: contient les informations à écrire
 */
extern void write_sector(uint cylinder, uint sector, uchar *buffer);


/*
 Fonction qui formate une certaine partie du disque avec la valeur passée en paramètre.
 cylinder: cylindre que l'on veut formater
 sector: secteur que l'on veut formater
 value: la valeur à écrire à la place des données présente sur le disque lors du formatage
 */
extern void format_sector(uint cylinder, uint sector, uint value);


/*
 Fonction qui formate plusieurs secteurs du disque avec la valeur passée en paramètre.
 cylinder: cylindre que l'on veut formater
 sector: secteur que l'on veut formater
 nbSector: nombre de secteur à formater après le premier
 value: la valeur à écrire à la place des données présente sur le disque lors du formatage
 */
extern void format_n_sector(uint cylinder, uint sector, uint nbSector, uint value);


/*
 Fonction qui formate plusieurs secteurs du disque avec la valeur passée en paramètre,
 cette fonction effectue le formatage en commençant par le dernier, puis l'avant dernier...
 cylinder: cylindre que l'on veut formater
 sector: secteur que l'on veut formater
 nbSector: nombre de secteur à formater après le premier
 value: la valeur à écrire à la place des données présente sur le disque lors du formatage
 */
extern void format_n_sector_reverse(uint cylinder, uint sector, uint nbSector, uint value);

#endif /* drive_h */
