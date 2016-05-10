/**
KNAPIK Christopher - DHERSIN Jérome 
TP3 ASE - 7 octobre 2014 
**/


#include"create_ctx.h"

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
	if(ctx_ring==NULL){
		ctx_ring=new_ctx;
		new_ctx->ctx_next=new_ctx;
	}	
	else {
		new_ctx->ctx_next=ctx_ring->ctx_next;
		ctx_ring->ctx_next=new_ctx;
	}
	return 0;
}

// Initialise un nouveau contexte
int init_ctx(struct ctx_s *ctx, int stack_size, func_t F, void *args){
	ctx->ctx_stack=malloc(stack_size);
	ctx->ctx_esp=ctx->ctx_ebp=((char *)ctx->ctx_stack)+stack_size-4;
	ctx->ctx_F=F;
	ctx->ctx_arg=args;
	ctx->ctx_magic=CTX_MAGIC;
	ctx->ctx_state=CTX_INIT;
	ctx->ctx_next=ctx;
	return 0;
}


void switch_to_ctx(struct ctx_s *new){
	
	assert(new->ctx_magic == CTX_MAGIC);
	if(current_ctx){
		asm("movl %%esp, %0\n" :"=r"(current_ctx->ctx_esp));
		asm("movl %%ebp, %0\n" :"=r"(current_ctx->ctx_ebp));
	}	
	if(new->ctx_state==CTX_END){		
		//free();
		//liberer memoire		
		struct ctx_s *tmp = new; 
		new = new->ctx_next;
		free(tmp->ctx_stack);
		free(tmp);
		printf("Suppression du contexte ...\n");
		if(current_ctx->ctx_next==current_ctx){
			//message+exit	
			//retourner au main
			free(current_ctx->ctx_stack);
			free(current_ctx);
			printf("Ceci est le dernier contexte");
			exit(0);
		}	
	}	
	current_ctx=new;
	asm("movl %0, %%esp\n" ::"r"(current_ctx->ctx_esp));
	asm("movl %0, %%ebp\n" ::"r"(current_ctx->ctx_ebp));
	if(current_ctx->ctx_state==CTX_INIT){
		start_current_ctx();
	}
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


void f_ping(void *args){
	printf("ping\n");
}


void f_pong(void *args){
	printf("pong\n");
} 


void f_pang(void *args){
	printf("pang\n");
}
 
