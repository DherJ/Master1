/**
KNAPIK Christopher - DHERSIN Jérome
TP1 ASE - 23 septembre 2013 
**/

#include "try.h"


void afficherInfoRegistre(){
	int x=0;
	int y=0;
	asm ("movl %%esp, %0\n" :"=r"(x));
	asm ("movl %%ebp, %0\n" :"=r"(y));
	printf("%%ebp = %08x\n", x);
	printf("%%esp = %08x\n", y);
}


/**
la fonction try récupère les valeurs des registres esp/ebp et retourne ensuite la fonction passée en argument
**/

int try(struct ctx_s *pctx, func_t *f, int arg){	
	asm ("movl %%esp, %0" : "=r"(pctx->ctx_esp));
	asm ("movl %%ebp, %0" : "=r"(pctx->ctx_ebp));
	pctx->ctx_magic=CTX_MAGIC;
	return f(arg);
}     

/**
la fonction throw permet le retour au contexte précédent l'éxecution de mul grâce aux valeurs récupérées par la fonction try
**/

int throw(struct ctx_s *pctx, int r){
	static int throw_r=0; 
	throw_r=r;
	assert(pctx->ctx_magic == CTX_MAGIC);	
	asm ("movl %0, %%esp\n" ::"r"(pctx->ctx_esp));
	asm ("movl %0, %%ebp\n" ::"r"(pctx->ctx_ebp));
	return throw_r;
}   
