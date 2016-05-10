/**
KNAPIK Christopher - DHERSIN Jérome
TP1 ASE - 23 septembre 2013 
**/

#ifndef _TRY_
#define _TRY_

#define CTX_MAGIC 0xCAFEBABE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <setjmp.h>
#include <assert.h>


typedef int (func_t)(int);

struct ctx_s{
	void *ctx_esp;
	void *ctx_ebp;
	unsigned ctx_magic;
};

struct ctx_s *pctx_global;

extern void afficherInfoRegistre();
extern int try(struct ctx_s *pctx, func_t *f, int arg); 
extern int throw(struct ctx_s *pctx, int r);


#endif
