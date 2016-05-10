/**
KNAPIK Christopher - DHERSIN Jérome 
TP2 ASE - 30 septembre 2014 
**/


#include<stdio.h>
#include<stdlib.h>
#include<assert.h>

#define CTX_MAGIC 0xCAFEBABE

typedef void(func_t)(void *);

enum ctx_state_e{
	CTX_INIT,
	CTX_EXQ,
	CTX_END,
};

struct ctx_s{
	void* ctx_esp;
	void* ctx_ebp;
	unsigned ctx_magic;
	void* ctx_stack;
	func_t* ctx_F;
	void* ctx_arg;	
	enum ctx_state_e ctx_state;
};

struct ctx_s *ctx_ping; 
struct ctx_s *ctx_pong;
struct ctx_s *ctx_pang; 

static struct ctx_s *current_ctx=(struct ctx_s *)0;

void f_ping(void *arg);
void f_pong(void *arg);
void f_pang(void *arg);

extern void start_current_ctx();
extern void switch_to_ctx(struct ctx_s *new);
extern struct ctx_s *init_ctx(int stack_size, func_t F, void *args);
extern void f_ping(void *args);
extern void f_pong(void *args);
extern void f_pang(void *args);

