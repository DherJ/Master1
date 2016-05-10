//
//  ordonnancement.c
//
//
//  Created by florian on 30/09/2014.
//
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <setjmp.h>
#include <assert.h>

#define CTX_MAGIC 0xCAFEBABE

typedef void (func_t)(int);

enum ctx_state_e {
    CTX_INIT, CTX_EXQ, CTX_END
};

struct ctx_s {
    void *ctx_stack;
    void *ctx_esp;
    void *ctx_ebp;
    func_t *ctx_f;
    int ctx_arg;
    enum ctx_state_e ctx_state;
    unsigned ctx_magic;
    struct ctx_s *next;
};

// Pointeur static qui pointe sur la chaine
static struct ctx_s *ctx_ring = NULL;

static struct ctx_s *current_ctx = NULL;

struct ctx_s *init_ctx(int stack_size, func_t f, int args);
struct ctx_s *create_ctx(int stack_size, func_t f, int args );
void switch_to_ctx(struct ctx_s *new);
void yield();
void displayMultiProcess( int i );
void start_current_ctx();

// esp et ebp pointent tous les deux en bas de la pile au même endroit
// -4 car sinon on sort de la pile (ca serait la 1ere adresse en dehors de la pile)
struct ctx_s *init_ctx(int stack_size, func_t f, int args){
    struct ctx_s *ctx = malloc(sizeof(struct ctx_s));
    ctx->ctx_stack = malloc(stack_size);
    ctx->ctx_esp = ((char*) ctx->ctx_stack) + stack_size - 4;
    ctx->ctx_ebp = ((char*) ctx->ctx_stack) + stack_size - 4;
    ctx->ctx_f = f;
    ctx->ctx_arg = args;
    ctx->ctx_magic = CTX_MAGIC;
    ctx->ctx_state = CTX_INIT;
    ctx->next = NULL;
    return ctx;
}

// Fonction qui construit une nouvelle structure ctx_s et qui fait le chainage en forme d'anneau
struct ctx_s *create_ctx(int stack_size, func_t f, int args ){
    struct ctx_s *new = init_ctx(stack_size,f,args);
    
    // Si il n'y a pas encore de chainage, on fait boucler new sur lui meme
    if(ctx_ring == NULL){
        ctx_ring = new;
        new->next = new;
    }
    // Sinon on insère la nouvelle structure entre le pointeur ctx_ring et la première structure de l'anneau
    else {
        new->next = ctx_ring->next;
        ctx_ring->next = new;
    }
    return new;
}

void switch_to_ctx(struct ctx_s *new){
    //Libération dans le cas ou on est pas dans la pile du suivant et qu'il est terminé
    if(new->ctx_state == CTX_END){
        if( ctx_ring == new  && new != current_ctx ){
            ctx_ring = new->next;
        }
        
        current_ctx->next = new->next;
        printf("\nDelete 1");
        free(new->ctx_stack);
        printf(",2 ");
        free(new);
        new = NULL;
        printf(": success");
        
        if(current_ctx->next == current_ctx){
	
            printf("\nDelete 1");
            free(current_ctx->ctx_stack);
            printf(",2 ");
            free(current_ctx);
            current_ctx = NULL;
            printf(": success");
            printf("\nLast context done... END of the program\n");
            //exit(0); // On redonne la main a main()
        }
        else switch_to_ctx(current_ctx->next); // on vérifie que le suivant n'est pas encore terminée
    }
    
    if(current_ctx){ // = current_ctx != null
        // on sauvegarde les anciennes valeurs des regitres dans la structure current_ctx
        asm("movl %%esp, %0\n" : "=r" (current_ctx->ctx_esp));
        asm("movl %%ebp, %0\n" : "=r" (current_ctx->ctx_ebp));
    }
    assert(new != NULL);
    current_ctx = new;
    
    // on met les nouvelles valeurs des registres dans la structure new
    asm("movl %0, %%esp\n" :: "r" (current_ctx->ctx_esp));
    asm("movl %0, %%ebp\n" :: "r" (current_ctx->ctx_ebp));
    
    if(current_ctx->ctx_state == CTX_INIT)
        start_current_ctx();
    
    return;
}

void yield(){
    // passe la main au suivant de l'anneau
    if(current_ctx != NULL )
        switch_to_ctx(current_ctx->next);
    else
        switch_to_ctx(ctx_ring);
}

void start_current_ctx(){
    current_ctx->ctx_state = CTX_EXQ;
    current_ctx->ctx_f(current_ctx->ctx_arg);
    current_ctx->ctx_state = CTX_END;
    // On ne supprime pas tout de suite le contexte terminé car on est dans sa pile,
    //on refait un tour de l'anneau et on le supprimera avant de rentrer dedans,
    //afin de ne pas etre dans sa pile d'execution lors de la suppression
    //il faudra donc surrement le supp dans switch
    // if(current_ctx->next == current_ctx){
    //     printf("\nLast context done... END of the program\n");
    //     exit(0); // On redonne la main a main()
    // }
    // else yield();
    yield();
    //    exit(0);
}

void displayMultiProcess( int i ){
    printf("\n\tI am the process%d\n",i);
}

int main(void){
    printf("Program start");
    printf("\n...Creating process1...");
    struct ctx_s *process1 = create_ctx(16384,displayMultiProcess, 1);
    printf("\n...Creating process2...");
    struct ctx_s *process2 = create_ctx(16384,displayMultiProcess, 2);
    printf("\n...Creating process3...");
    struct ctx_s *process3 = create_ctx(16384,displayMultiProcess, 3);
    yield();
    printf("This is the end");
    exit(EXIT_SUCCESS);
}

