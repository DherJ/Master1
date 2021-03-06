/**
KNAPIK Christopher - DHERSIN Jérome
TP1 ASE - 23 septembre 2013 
**/

#include <stdlib.h>
#include <stdio.h>

struct ctx_s{
	void *ctx_esp;
	void *ctx_ebp;
	unsigned ctx_magic;
};
int compt=0;

void appelSuccessif(int nb) {	
	printf("Adresse nb au départ = %08x\n",(int)&nb);
	//appels successifs grâce à une boucle	
	while(nb>0) {
		unsigned int ctx_esp;
		unsigned int ctx_ebp;
		asm("movl %%esp, %0":"=r"(ctx_esp));
		asm("movl %%ebp, %0":"=r"(ctx_ebp));
		printf("esp = %08x ~ ebp = %08x\n",ctx_esp,ctx_ebp);
		nb--;
	}
	printf("Adresse nb à la fin = %08x\n",(int)&nb);
}

void appelRecursif(int nb) {
	if(compt==0) {
		printf("Adresse nb au départ = %08x\n",(int)&nb);
	}
	//appels récursifs, la fonction est rappelée à chaque fin de boucle
	if(nb>0) {
		unsigned int ctx_esp;
		unsigned int ctx_ebp;
		asm("movl %%esp, %0":"=r"(ctx_esp));
		asm("movl %%ebp, %0":"=r"(ctx_ebp));
		printf("esp = %08x ~ ebp = %08x\n",ctx_esp,ctx_ebp);
		compt++;
		appelRecursif(nb-1);
	} else {
		printf("Adresse nb à la fin = %08x\n",(int)&nb);
		compt=0;	
		}
}

int main(void) {
	puts("Appels successifs");
	appelSuccessif(6);
	puts("\nAppels recursifs");
	appelRecursif(6);
	return 0;
}
