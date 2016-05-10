#ifndef inode_h
#define inode_h

// #define NB_BLOC_DIRECT ((HDA_SECTORSIZE - 2*sizeof(int)) / sizeof(int)) - 2*sizeof(int)
#define NB_BLOC_DIRECT ((HDA_SECTORSIZE - sizeof(enum file_type_e) - 4*sizeof(int)) / sizeof(int))
#define NB_BLOC_INDIRECT (HDA_SECTORSIZE / sizeof(int))
#define BLOC_NULL 0;
#define MAGIC_INODE 0xEEAABB
#define TRUE 1
#define FALSE 0

enum file_type_e {FICHIER, DIRECTORY};

struct inode_s {
    enum file_type_e type;
    int size;
    int direct[NB_BLOC_DIRECT];
    int indirect;
    int double_indirect;
    uint magic;
};


extern int current_vol;
extern void read_inode(uint inumber, struct inode_s *inode);
extern void write_inode(uint inumber, struct inode_s *inode);
extern uint create_inode(enum file_type_e type);
extern int delete_inode(uint inumber);
extern uint vbloc_of_fbloc(uint inumber, uint fbloc, uint do_allocate);

#endif 