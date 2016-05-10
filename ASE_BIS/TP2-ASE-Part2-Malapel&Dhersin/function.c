#include "header.h"

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
	return 1;
}

void exec_f(){
	current_ctx->state = CTX_ACTIVATED;
	current_ctx->f(current_ctx->args);
	exit(1);
}

void switch_to_ctx(struct ctx_s *new_ctx){

	assert(new_ctx != NULL);
	assert(new_ctx->magic == MAGIC);
	assert(new_ctx->state == CTX_READY || CTX_ACTIVATED);

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
