/**
KNAPIK Christopher - DHERSIN Jérome 
TP3 ASE - 7 octobre 2014 
**/


#include "create_ctx.h"


int main(int argc, char *argv[]){ 
	printf("Creation du contexte ping ...\n");
	create_ctx(16384, f_ping, NULL);
	printf("Creation du contexte pong ...\n");
	create_ctx(16384, f_pong, NULL);
	printf("Creation du contexte pang ...\n");	
	create_ctx(16384, f_pang, NULL);	
	yield();

	exit(EXIT_SUCCESS);
}
