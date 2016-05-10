/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE -  novembre 2014 
**/


Le but de ce TP est de créer une bibliothèque permettant la gestion de fichiers.




Les différentes fonctions utilisées (drive.c) permettent de se déplacer sur un disque, lire, écrire ou supprimer les données du disque :


- void read_sector(unsigned int cyl, unsigned int sec, unsigned char* buffer); 
- void read_sector_n(unsigned int cyl, unsigned int sec, unsigned char* buffer, int size);
- void write_sector(unsigned int cyl, unsigned int sec, unsigned char* buffer);
- void write_sector_n(unsigned int cyl, unsigned int sec, unsigned char* buffer, int size);
- void format();
- void format_sector(unsigned int cyl, unsigned int sec, unsigned int nsector, unsigned int value);
- static void go_to_sector(unsigned int cyl, unsigned int sec);
