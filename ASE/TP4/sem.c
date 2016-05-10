/**
KNAPIK Christopher - DHERSIN Jérome 
TP4 ASE - 14 octobre 2014 
**/


#include"sem.h"
#include"hw.h"


// Démarre le contexte actuel
void start_current_ctx(){
	current_ctx->ctx_state=CTX_EXQ;
	current_ctx->ctx_F(current_ctx->ctx_arg);
	current_ctx->ctx_state=CTX_END;

	yield();
	exit(0);
}


// Crée un nouveau contexte
int create_ctx(int stack_size, func_t F, void *args){
	struct ctx_s *new_ctx = malloc(sizeof(struct ctx_s));
	init_ctx(new_ctx, stack_size, F, args);
	irq_disable();
	if(ctx_ring==NULL){
		ctx_ring=new_ctx;
		new_ctx->ctx_next=new_ctx;
	}	
	else {
		new_ctx->ctx_next=ctx_ring->ctx_next;
		ctx_ring->ctx_next=new_ctx;
	}
	irq_enable();
	return 0;
}


// Initialise un nouveau contexte
int init_ctx(struct ctx_s *ctx, int stack_size, func_t F, void *args){
	ctx->ctx_stack=malloc(stack_size);
	ctx->ctx_esp=ctx->ctx_ebp=((char *)ctx->ctx_stack)+stack_size-4;
	ctx->ctx_F=F;
	ctx->ctx_arg=args;
	ctx->ctx_state=CTX_INIT;
	ctx->ctx_next=ctx;
	return 0;
}


void switch_to_ctx(struct ctx_s *new){
	// Passage d'un contexte à un autre    
	while (new->ctx_state==CTX_STOP) {
        new=new->ctx_next;
        if(new==current_ctx) {
		// retour au main
            exit(0);
        }
    }
    
    if (new->ctx_state==CTX_END) {
        if(ctx_ring==new && new!=current_ctx)
           ctx_ring=ctx_ring->ctx_next;
        
        if(current_ctx==new){
		// libération de la mémoire (suppression du contexte)
           free(current_ctx->ctx_stack);
           free(current_ctx);
       		 return;
	}
	else {
            	current_ctx->ctx_next=new->ctx_next;
			// libération de la mémoire (suppression du contexte)
            	free(new->ctx_stack);
            	free(new);
            	switch_to_ctx(current_ctx->ctx_next);
     	   }
    	}
    	current_ctx=new;
    
	    if(current_ctx){
		// On récupère les valeurs des registres du contexte actuel
      		asm("movl %%esp, %0\n" : "=r" (current_ctx->ctx_esp));
      		asm("movl %%ebp, %0\n" : "=r" (current_ctx->ctx_ebp));
	}
	// On rétablit le contexte avec les valeurs récupérées auparavant
    	asm("movl %0, %%esp\n" :: "r" (current_ctx->ctx_esp));
    	asm("movl %0, %%ebp\n" :: "r" (current_ctx->ctx_ebp));
    
   	if(current_ctx->ctx_state==CTX_INIT)
      	start_current_ctx();
    	return;
}


// Passe d'un contexte à un autre
void yield(){
	//passer la main au suivant dans l'anneau
	if(current_ctx!=NULL){
		switch_to_ctx(current_ctx->ctx_next);
	}
	else switch_to_ctx(ctx_ring);
} 


void sem_init(struct sem_s *sem, char* name, unsigned int val){
	//malloc	
	sem=malloc(sizeof(struct sem_s));
	// initialisation du sémaphore	
	sem->sem_name=name;
	sem->sem_cpt=val;
	sem->sem_ctx=	NULL;	
	puts("Initialisation de semaphore ...");
	printf("%s : %d\n", sem->sem_name, sem->sem_cpt);
}


void sem_up(struct sem_s *sem){	
	irq_disable();	
	//incrémentation du compteur
	sem->sem_cpt++;	
	if(sem->sem_cpt<0){
		irq_disable();
		sem->sem_ctx->ctx_state=CTX_EXQ;		
		sem->sem_ctx=sem->sem_ctx->ctx_next;		
		irq_enable();
	}
	irq_enable();
}


void sem_down(struct sem_s *sem){
	irq_disable();	
	//décrémentation du compteur
	sem->sem_cpt--;
	if(sem->sem_cpt<0){
		irq_disable();
		current_ctx->ctx_state=CTX_STOP;
		current_ctx->ctx_sem_next=sem->sem_ctx;
		sem->sem_ctx=current_ctx;
		irq_enable();		
		yield();	
	}
	irq_enable();
}


void producteur(void *args){	
	while(1){
	printf("producteur :  %d (semaphore \"%s\") \n", vide.sem_cpt, vide.sem_name);	
	sem_down(&vide);	
	sem_down(&mutex);
	sem_up(&mutex);
	sem_up(&plein);	
	}
}


void consommateur(void *args){
	while(1){
	printf("consommateur :  %d (semaphore \"%s\") \n", plein.sem_cpt, plein.sem_name);
	sem_down(&plein);
	sem_down(&mutex);
	sem_up(&mutex);
	sem_up(&vide);
	}
}

