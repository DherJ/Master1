#include "../header/drive.h"

/* 
 Fonction utilisée pour les interruptions 
 */
void empty(){
    return;
}

/*
 Fonction qui initialise la librairie hardware ainsi que les interruptions 
 */
void init_hard(){
    /* init hardware */
    if (init_hardware(HARDWARE_INI) == 0) {
        fprintf(stderr, "Error in hardware initialization\n");
        exit(EXIT_FAILURE);
    } else {
        printf("Okay, hardware librairie initialized correctly\n");
    }
    int i = 0;
    for(i=0; i<16; i++){
        IRQVECTOR[i] = empty;
    }
}

/*
 Déplacement vers un cylindre et secteur donné en parametre
 */
void seek(uint cylinder, uint sector){
    assert(cylinder >= 0 && cylinder < HDA_MAXCYLINDER);
    assert(sector >= 0 && sector < HDA_MAXSECTOR);
    
    _out(HDA_DATAREGS, (cylinder >> 8) & 0xFF);
    _out(HDA_DATAREGS +1, (cylinder) & 0xFF);
    _out(HDA_DATAREGS +2, (sector >> 8) & 0xFF);
    _out(HDA_DATAREGS +3, (sector) & 0xFF);
    _out(HDA_CMDREG, CMD_SEEK);
    _sleep(HDA_IRQ);
}


/* 
 Fonction qui lit la partie du disque demandée par la valeur des paramètres,
 cylinder: cylindre que l'on veut lire
 sector: secteur sque l'on veut lire
 buffer: contient les informations lu a la fin de la fonction
 size: la taille du buffer (taille des données à lire)
 */
void read_sector_n(uint cylinder, uint sector, uchar *buffer, uint size){
    seek(cylinder, sector);
    _out(HDA_DATAREGS, 0);
    _out(HDA_DATAREGS +1, 1);
    _out(HDA_CMDREG, CMD_READ);
    _sleep(14);
    int i = 0;
    for (i = 0; i < size ; i ++){
        buffer[i] = MASTERBUFFER[i];
    }
}


/*
 Fonction qui lit la partie du disque demandée par la valeur des paramètres (cylinder & sector),
 cylinder: cylindre que l'on veut lire
 sector: secteur sque l'on veut lire
 buffer: contient les informations lu a la fin de la fonction
 */
void read_sector(uint cylinder, uint sector, uchar *buffer){
    read_sector_n(cylinder, sector, buffer, HDA_SECTORSIZE);
}


/*
 Fonction qui écrit sur une certaine partie du disque le buffer passé en paramètre.
 cylinder: cylindre sur lequel on veut écrire
 sector: secteur sur lequel on veut écrire
 buffer: contient les informations à écrire
 size: la taille du buffer (taille des données à écrire)
 */
void write_sector_n(uint cylinder, uint sector, uchar *buffer, uint size){
    seek(cylinder, sector);
    int i = 0;
    for (i = 0; i < size ; i ++){
        MASTERBUFFER[i] = buffer[i];
    }
    
    _out(HDA_DATAREGS, 0);
    _out(HDA_DATAREGS +1, 1);
    _out(HDA_CMDREG, CMD_WRITE);
    _sleep(14);
}


/*
 Fonction qui écrit sur une certaine partie du disque le buffer passé en paramètre.
 cylinder: cylindre sur lequel on veut écrire
 sector: secteur sur lequel on veut écrire
 buffer: contient les informations à écrire
 */
void write_sector(uint cylinder, uint sector, uchar *buffer){
    write_sector_n(cylinder, sector, buffer, HDA_SECTORSIZE);
}


/*
 Fonction qui formate une certaine partie du disque avec la valeur passée en paramètre.
 cylinder: cylindre que l'on veut formater
 sector: secteur que l'on veut formater
 value: la valeur à écrire à la place des données présente sur le disque lors du formatage
 */
void format_sector(uint cylinder, uint sector, uint value){
    seek(cylinder, sector);
    int i = 0;
    uchar buffer[HDA_SECTORSIZE];
    for(i=0; i<HDA_SECTORSIZE; i++){
        buffer[i] = value;
    }
    
    write_sector(cylinder, sector, buffer);
}


/*
 Fonction qui formate plusieurs secteurs du disque avec la valeur passée en paramètre.
 cylinder: cylindre que l'on veut formater
 sector: secteur que l'on veut formater
 nbSector: nombre de secteur à formater après le premier
 value: la valeur à écrire à la place des données présente sur le disque lors du formatage
 */
void format_n_sector(uint cylinder, uint sector, uint nbSector, uint value){
    
    assert((sector+nbSector) < HDA_MAXSECTOR);
    int i = 0;
    for(i=sector; i<(sector+nbSector); i++){
        format_sector(cylinder, i, value);
    }
}


/*
 Fonction qui formate plusieurs secteurs du disque avec la valeur passée en paramètre,
 cette fonction effectue le formatage en commençant par le dernier, puis l'avant dernier...
 cylinder: cylindre que l'on veut formater
 sector: secteur que l'on veut formater
 nbSector: nombre de secteur à formater après le premier
 value: la valeur à écrire à la place des données présente sur le disque lors du formatage
 */
void format_n_sector_reverse(uint cylinder, uint sector, uint nbSector, uint value){
    assert((sector+nbSector) < HDA_MAXSECTOR);
    int i = 0;
    for(i=(sector+nbSector); i>=sector; i--){
        format_sector(cylinder, i, value);
    }
}













