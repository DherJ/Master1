/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 4 novembre 2014 
**/


#ifndef DRIVE_H_INCLUDED
#define DRIVE_H_INCLUDED

	/* Paramètres du contrôleur IDE */
	#define ENABLE_HDA		1		/* 0 pour désactiver la simulation	*/
	#define HDA_CMDREG      	0x3F6	/* Registre de commande				*/
	#define HDA_DATAREGS    	0x110	/* Base des registres de données	*/
	#define HDA_IRQ         	14		/* Interruption du disque			*/

	/* Géométrie du disque */
	#define HDA_MAXCYLINDER 	16
	#define HDA_MAXSECTOR   	16
	#define HDA_SECTORSIZE  	256

	/* Erreurs */
	#define INVALID_CYLINDER	1
	#define INVALID_SECTOR		2
	#define INVALID_GEOMETRY	4
	#define INVALID_PARAMETER	8

	void read_sector(const unsigned int cyl, const unsigned int sec, char *buffer);
	void read_sector_n(const unsigned int cyl, const unsigned int sec, char *buffer, int size);
	void write_sector(const unsigned int cyl, const unsigned int sec, const char *buffer);
	void write_sector_n(const unsigned int cyl, const unsigned int sec, const char *buffer, int size);
	void format_sector(const unsigned int cyl, const unsigned int sec, const unsigned int nsec, const unsigned int value);
	void init();
	void seek(const unsigned int cyl, const unsigned int sec);

#endif
