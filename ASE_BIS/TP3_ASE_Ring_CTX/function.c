#include "header.h"

int create_ctx(int stack_size, func_t f, void *args){
	struct ctx_s *new = (struct ctx_s*) malloc(sizeof(struct ctx_s));

	if( init_ctx(new, stack_size, f, args) == 1) {
		if(ctx_ring == NULL){
			ctx_ring = new;
			new->next_ctx = new;
		} else {
			new->next_ctx = ctx_ring->next_ctx;
			ctx_ring->next_ctx = new;
		}
		return 1;
	}
	else return 0;

	return 0;
}


int init_ctx(struct ctx_s *ctx, int stack_size, func_t f, void *args){
	ctx->stack = (unsigned char*) malloc(stack_size);
	if(ctx->stack == NULL){
		return 0;
	}
	ctx->ebp = &ctx->stack[stack_size-4];
	ctx->esp = &ctx->stack[stack_size-4];
	ctx->state = CTX_READY;
	ctx->f = f;
	ctx->args = args;
	ctx->magic = MAGIC;
	ctx->next_ctx = NULL;
	return 1;
}

void exec_f(){
	current_ctx->state = CTX_ACTIVATED;
	current_ctx->f(current_ctx->args);
	current_ctx->state = CTX_TERMINATED;
	yield();
}

void switch_to_ctx(struct ctx_s *new_ctx){

	assert(new_ctx != NULL);
	assert(new_ctx->magic == MAGIC);
	assert(new_ctx->state == CTX_READY || CTX_ACTIVATED || CTX_TERMINATED);

	if(new_ctx->state == CTX_TERMINATED){
		if(ctx_ring == new_ctx && new_ctx != current_ctx){
			ctx_ring = ctx_ring->next_ctx;
		}

		if(current_ctx == new_ctx){
			free(current_ctx->stack);
			free(current_ctx);
			return;
		}
		else {
			current_ctx->next_ctx = new_ctx->next_ctx;
			free(new_ctx->stack);
			free(new_ctx);
			switch_to_ctx(current_ctx->next_ctx);
		}
	}

	current_ctx = new_ctx;

	if(current_ctx != NULL){
		asm("movl %%ebp, %0" : "=r" (current_ctx->ebp));
		asm("movl %%esp, %0" : "=r" (current_ctx->esp));
	}

	current_ctx = new_ctx; // On fait pointer current_ctx sur ctx
	asm("movl %0, %%esp" :: "r" (current_ctx->esp)); // Il faut utiliser current_ctx car c'est une variable globale
	asm("movl %0, %%ebp" :: "r" (current_ctx->ebp)); // On est sur de sa valeur

	if(current_ctx->state == CTX_READY){
		exec_f();
	}
}


void yield(){
	if(current_ctx != NULL){
		switch_to_ctx(current_ctx->next_ctx);
	}
	else switch_to_ctx(ctx_ring);
}
