/**
KNAPIK Christopher - DHERSIN Jérome 
TP4 ASE - 14 octobre 2014 
**/


#include<stdio.h>
#include<stdlib.h>
#include<assert.h>


#define CTX_MAGIC 0xCAFEBABE
#define SEM_MAGIC 0xBABECAFE

typedef void(func_t)(void *);

// Etat du contexte
enum ctx_state_e{
	CTX_INIT,
	CTX_EXQ,
	CTX_END,
	CTX_STOP,
};

// Structure contexte
struct ctx_s{
	void* ctx_esp;
	void* ctx_ebp;
	unsigned ctx_magic;
	void* ctx_stack;
	func_t* ctx_F;
	void* ctx_arg;	
	enum ctx_state_e ctx_state;	
	struct ctx_s *ctx_next;
	struct ctx_s *ctx_sem_next;
};

// Contexte courant
static struct ctx_s *current_ctx=(struct ctx_s *)0;
// "Anneau" contenant les différents contextes
static struct ctx_s *ctx_ring=NULL;

// Structure sémaphores
struct sem_s{
	char* sem_name;
	int sem_cpt;
	struct ctx_s *sem_ctx; 
	unsigned sem_magic;
};

#define NBSEM 100
struct sem_s mutex,vide,plein;


extern void start_current_ctx();
extern int create_ctx(int stack_size, func_t F, void *args);
extern int init_ctx(struct ctx_s *ctx, int stack_size, func_t F, void *args);
extern void switch_to_ctx(struct ctx_s *new);
extern void yield();
extern void sem_init(struct sem_s *sem, char* name, unsigned int val);
extern void sem_up(struct sem_s *sem);
extern void sem_down(struct sem_s *sem);
extern void producteur(void *args);
extern void consommateur(void *args);
extern void run(void);




