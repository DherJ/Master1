// Edited by Malapel Florian
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <setjmp.h>
#include <assert.h>

#define CTX_MAGIC 0xCAFEBABE

// Type de fonction qui sera utilisé par les contextes
typedef void (func_t) (void*);

enum ctx_state_e {
    CTX_INIT, CTX_EXQ, CTX_END, CTX_STOPBYSEM
};

// Structures:
// Structure de la liste de contexte en anneau
struct ctx_s {
    void *ctx_esp;
    void *ctx_ebp;
    func_t *ctx_f;
    void* ctx_arg;
    enum ctx_state_e ctx_state;
    unsigned ctx_magic;
    void *ctx_stack;
    struct ctx_s *ctx_next;
    struct ctx_s *ctx_semNext;
};

// Structure d'un sémaphore
struct sem {
    int sem_cpt;
    struct ctx_s *sem_listOfCtx;
};

// Variables Globales :
//  Pointeur static qui pointe sur la chaine
static struct ctx_s *ctx_ring = NULL;
static struct ctx_s *current_ctx = NULL;

// Prototypes:
extern void sem_init(struct sem *sem, unsigned int val);
extern void sem_up(struct sem *sem);
extern void sem_down(struct sem *sem);
extern void switch_to_ctx(struct ctx_s *new);
extern int init_ctx(struct ctx_s *ctx, int stack_size, func_t f, void *args);
extern int create_ctx(int stack_size, func_t f, void *args);
extern void yield();
extern void start_current_ctx();
extern void f_ping();
extern void f_pong();
extern void f_pang();