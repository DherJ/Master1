// Edited by Malapel Florian
#include "ctx.h"
#include "hw.h"

/**
 *  Procedure qui alloue de la mémoire pour la nouvelle structure créée,
 *  puis les valeurs des variables sont initialisés
 **/
void sem_init(struct sem *s, unsigned int val){
    s = (struct sem*)malloc(sizeof(struct sem));
    s->sem_cpt = val;
    s->sem_listOfCtx = NULL;
}

void sem_up( struct sem *sem ){
    irq_disable();
    sem->sem_cpt++;
    if(sem->sem_cpt >= 0){
        current_ctx->ctx_state = CTX_EXQ;
        sem->sem_listOfCtx = sem->sem_listOfCtx->ctx_next;
        irq_enable();
        yield();
    }
    else irq_enable();
}

void sem_down( struct sem *sem ){
    irq_disable(); // Désactive les interruptions
    sem->sem_cpt--;
    if(sem->sem_cpt < 0){
        current_ctx->ctx_state = CTX_STOPBYSEM;
        current_ctx->ctx_semNext = sem->sem_listOfCtx; // Insertion en tete
        sem->sem_listOfCtx = current_ctx;   // Insertion en tete
        irq_enable(); // Active les interruptions
        yield();
    }
    else irq_enable(); // Active les interruptions
}

void run(void){
    setup_irq(TIMER_IRQ, &yield);
    start_hw();
    yield();
}

/**
 *  Fonction qui initialise toutes les variables de la structure
 **/
int init_ctx(struct ctx_s *ctx, int stack_size, func_t f, void *args){
    ctx->ctx_stack = malloc(stack_size);
    ctx->ctx_esp = ((char*) ctx->ctx_stack) + stack_size - 4;
    ctx->ctx_ebp = ((char*) ctx->ctx_stack) + stack_size - 4;
    ctx->ctx_f = f;
    ctx->ctx_arg = args;
    ctx->ctx_magic = CTX_MAGIC;
    ctx->ctx_state = CTX_INIT;
    ctx->ctx_next = ctx;
    return 0;   // Return always without error
}


/**
 *  Fonction qui alloue de la mémoire pour la structure ctx_s que l'on va créer
 *  puis établie le chainage en forme d'anneau
 **/
int create_ctx(int stack_size, func_t f, void *args){
    struct ctx_s* new = (struct ctx_s*) malloc(sizeof(struct ctx_s));
    if( init_ctx(new, stack_size, f, args) == 0 ){
        if(ctx_ring == NULL){
            ctx_ring = new;
            new->ctx_next = new;
        }
        else {
            new->ctx_next = ctx_ring->ctx_next;
            ctx_ring->ctx_next = new;
        }
        return 0;   // OK
    }
    else return 1; // Error
}


/**
 *  Procedure qui alterne les contexts entre eux
 **/
void yield(){
    if(current_ctx != NULL )
        switch_to_ctx(current_ctx->ctx_next);
    else
        switch_to_ctx(ctx_ring);
}

/**
 *  Procedure qui exécute la fonction associée dans la structure ctx_s
 *  une fois la fonction exécuté, l'état du ctx est passé à CTX_END
 *  et ensuite on appel la fonctien yield() pour passer au ctx suivant
 **/
void start_current_ctx(){
    current_ctx->ctx_state = CTX_EXQ;
    current_ctx->ctx_f(current_ctx->ctx_arg);
    current_ctx->ctx_state = CTX_END;
    yield();
}


/**
 *  Procedure qui gère les registres esp et ebp de chaque structure ctx_s
 **/
void switch_to_ctx(struct ctx_s *new){
    // Tant que l'on tombe sur un ctx qui est en attente
    while (new->ctx_state == CTX_STOPBYSEM) {
        new = new->ctx_next;
//        assert(new==current_ctx); On vérifit que tous n'est pas bloqué
        if(new == current_ctx) { // Si on revient au current_ctx
            printf("\nAll ctx are down\n");
            exit(0);
        }
    }
    
    if (new->ctx_state == CTX_END) {
        if(ctx_ring == new && new != current_ctx)
            ctx_ring = ctx_ring->ctx_next;
        
        if(current_ctx == new){
            free(current_ctx->ctx_stack);
            free(current_ctx);
            return;
        }
        else {
            current_ctx->ctx_next = new->ctx_next;
            free(new->ctx_stack);
            free(new);
            switch_to_ctx(current_ctx->ctx_next);
        }
    }
    current_ctx = new;
    
    if(current_ctx){ // = current_ctx != null
        // on sauvegarde les anciennes valeurs des regitres dans la structure pointée par current_ctx
        asm("movl %%esp, %0\n" : "=r" (current_ctx->ctx_esp));
        asm("movl %%ebp, %0\n" : "=r" (current_ctx->ctx_ebp));
    }

    
    // on met les nouvelles valeurs des registres dans la structure new
    asm("movl %0, %%esp\n" :: "r" (current_ctx->ctx_esp));
    asm("movl %0, %%ebp\n" :: "r" (current_ctx->ctx_ebp));
    
    if(current_ctx->ctx_state == CTX_INIT)
        start_current_ctx();
    return;
}
