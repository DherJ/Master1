/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 4 novembre 2014 
**/


#ifndef MBR_H_INCLUDED
#define MBH_H_INCLUDED

#define MAGIC_NUMBER	0xBABECAFE
#define MAX_VOLUME		8

typedef enum {BASE_VOL, ANNEXE_VOL, OTHER} volume_type;
extern char volume_type_name[3][10];

struct volume_s	{
	unsigned int cyl, sec, size;
	volume_type type;
};

struct mbr_s	{
	struct volume_s disque[MAX_VOLUME];
	unsigned int nbr_volumes;
	unsigned int magic_number;
};

extern struct mbr_s mbr;

void load_mbr();
void save_mbr();
void check_size_mbr();

unsigned int cylindreOfBlok(const unsigned int num_vol, const unsigned int num_bloc);
unsigned int sectorOfBlok(const unsigned int num_vol, const unsigned int num_bloc);

void read_bloc(const unsigned int vol, const unsigned int bloc, char* buffer);
void write_bloc(const unsigned int vol, const unsigned int bloc, const char* buffer);
void format_bloc(const unsigned int vol);

#endif
