/**
KNAPIK Christopher - DHERSIN Jérome 
TP2 ASE - 30 septembre 2014 
**/


#include"switch_ctx.h"

void start_current_ctx(){
	current_ctx->ctx_state=CTX_EXQ;
	current_ctx->ctx_F(current_ctx->ctx_arg);
	current_ctx->ctx_state=CTX_END;	
	exit(0);
}

struct ctx_s *init_ctx(int stack_size, func_t F, void *args){
	struct ctx_s *ctx = malloc(sizeof(struct ctx_s));
	ctx->ctx_stack=malloc(stack_size);
	ctx->ctx_esp=ctx->ctx_ebp=((char *)ctx->ctx_stack)+stack_size-4;
	ctx->ctx_F=F;
	ctx->ctx_arg=args;
	ctx->ctx_magic=CTX_MAGIC;
	ctx->ctx_state=CTX_INIT;
	return ctx;
}

void switch_to_ctx(struct ctx_s *new){
	if(current_ctx){
		asm("movl %%esp, %0\n" :"=r"(current_ctx->ctx_esp));
		asm("movl %%ebp, %0\n" :"=r"(current_ctx->ctx_ebp));
	}
	assert(new->ctx_magic == CTX_MAGIC);
	current_ctx=new;
	asm("movl %0, %%esp\n" ::"r"(current_ctx->ctx_esp));
	asm("movl %0, %%ebp\n" ::"r"(current_ctx->ctx_ebp));
	if(current_ctx->ctx_state==CTX_INIT){
		start_current_ctx();
	}
	return;
}

void f_ping(void *args){
    while(1) {
        printf("~ TP2 ") ;
        switch_to_ctx(ctx_pong);
        printf("Christopher ") ;
        switch_to_ctx(ctx_pong);
    }
}

void f_pong(void *args){
    while(1) {
        printf("ASE ") ;
        switch_to_ctx(ctx_pang);
        printf("DHERSIN ") ;
        switch_to_ctx(ctx_pang);
    }
} 

void f_pang(void *args){
    while(1) {
        printf("KNAPIK ") ;
        switch_to_ctx(ctx_ping);
        printf("Jerome ") ;
        switch_to_ctx(ctx_ping);
    }
} 
