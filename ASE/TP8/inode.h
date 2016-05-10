/**
KNAPIK Christopher - DHERSIN Jérome 
TP6 ASE - 2 décembre 2014 
**/

#define N_DIRECT 2
#define BLOC_NULL 0
#define N_BLOC_PAR_BLOC 5
#define NNBPB (BLOC_SIZE/sizeof(int)) // ???

typedef enum {IT_FILE, IT_DIR, IT_OTHER};

struct inode_s {
	enum inode_type_e in_type;
	unsigned int in_size;
	unsigned int in_direct[N_DIRECT];
	unsigned int in_indirect;
	unsigned int in_db_indirect;
};

int delete_inode(unsigned int inumber);
void read_inode(unsigned int inumber, struct inode_s *inode);
void write_inode(unsigned int inumber, struct inode_s *inode);
unsigned int create_inode(enum inode_type_e type);
unsigned int vbloc_of_fbloc(unsigned int inumber, unsigned int fbloc);

