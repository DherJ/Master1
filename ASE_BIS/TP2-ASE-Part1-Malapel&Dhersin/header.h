#ifndef _HEADER_H_
#define _HEADER_H_

#include <stdlib.h>
#include <stdio.h>
#include <assert.h>

#define MAGIC 0xDEADBEEF

typedef void (func_t)(void*);
	
enum ctx_state_e {
	CTX_READY, CTX_ACTIVATED, CTX_TERMINATED
};


static struct ctx_s *current_ctx = NULL;

struct ctx_s {
    void *esp;
    void *ebp;
    func_t *f;
    void *args;
    unsigned char *stack; // tableau d'octet
    unsigned magic;
    enum ctx_state_e state;
};

extern int init_ctx(struct ctx_s *ctx, int stack_size, func_t f, void *args);
extern void exec_f();
extern void switch_to_ctx(struct ctx_s *new_ctx);




#endif