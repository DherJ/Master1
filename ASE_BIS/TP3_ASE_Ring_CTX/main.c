#include "header.h"

struct ctx_s ctx_ping;
struct ctx_s ctx_pong;
struct ctx_s ctx_pang;

void f_ping(void *args);
void f_pong(void *args);


void f_ping(void *args){
	printf("I ping\n");
}

void f_pong(void *args){
	printf("I pong\n");
}

void f_pang(void *args){
	printf("I pang\n");
}

int main(int argc, char *argv[]){
	if(create_ctx(16384, f_ping, NULL) != 0)
		printf("Creation ctx ping OK\n");
	else printf("Creation ctx ping FAIL\n");

	if(create_ctx(16384, f_pong, NULL) != 0)
		printf("Creation ctx pong OK\n");
	else printf("Creation ctx pong FAIL\n");

	if(create_ctx(16384, f_pang, NULL) != 0)
		printf("Creation ctx pang OK\n");
	else printf("Creation ctx pang FAIL\n");

	yield();

	printf("Execution finish with success :)\n");
	return (0);
}
