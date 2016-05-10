/**
KNAPIK Christopher - DHERSIN Jérome 
TP5 ASE - 21 octobre 2014 
**/


#ifndef _DRIVE_H_
#define _DRIVE_H_

#define ENABLE_HDA       1
#define HDA_FILENAME     "vdiskA.bin"
#define HDA_CMDREG       0x3F6
#define HDA_DATAREGS     0x110
#define HDA_IRQ          14
#define HDA_MAXCYLINDER  16
#define HDA_MAXSECTOR    16
#define HDA_SECTORSIZE   32
#define HDA_STPS         2
#define HDA_STPC         1
#define HDA_PON_DELAY    30
#define HDA_POFF_DELAY   30


struct vol_s {
	unsigned int vol_firstCyl;
	unsigned int vol_firstSec;
	unsigned int vol_nbSec;	
	enum vol_type_e vol_type;
};

struct mbr_s{
	struct vol_s mbr_vol[MAX_VOL];	// de 0 à 8 
	unsigned mbr_n_vol;
	unsigned mbr_magic;	
};

struct mbr_s mbr;

enum vol_type_e { VOLT_PR, VOLT_SND, VOLT_OTHER };
	
	//extern int check_hda();
	void read_sector(unsigned int cyl, unsigned int sec, unsigned char* buffer);
	void read_sector_n(unsigned int cyl, unsigned int sec, unsigned char* buffer, int size);
	void write_sector(unsigned int cyl, unsigned int sec, unsigned char* buffer);
	void write_sector_n(unsigned int cyl, unsigned int sec, unsigned char* buffer, int size);
	void format();
	void format_sector(unsigned int cyl, unsigned int sec, unsigned int nsector, unsigned int value);
	static void go_to_sector(unsigned int cyl, unsigned int sec);
	int cylinderOfBloc(int volume, int bloc);
	int sectorOfBloc(int volume, int bloc);
	void loadMBR();
	void saveMBR();

#endif
