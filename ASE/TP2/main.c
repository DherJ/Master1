/**
KNAPIK Christopher - DHERSIN Jérome 
TP2 ASE - 30 septembre 2014 
**/


#include"switch_ctx.h"


int main(int argc, char *argv[]){ 
	ctx_ping = init_ctx(16384, f_ping, NULL);
	ctx_pong = init_ctx(16384, f_pong, NULL);
	ctx_pang = init_ctx(16384, f_pang, NULL);
	switch_to_ctx(ctx_ping);

	exit(EXIT_SUCCESS);
}
