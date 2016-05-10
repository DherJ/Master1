#include "common.h"
#include "volume.h"
#include "mbr.h"
#include "drive.h"



void execute(const char *name){
    struct _cmd *c = commands; 
    while (c->name && strcmp (name, c->name))
		c++;
    (*c->fun)();
}

void boucleExec(void){
    char name[64];
    
    while (printf("> "), scanf("%s", name) == 1)
	execute(name) ;
}

int main(void){
	init();
	load_mbr();
	execute("help");
	boucleExec();
	return 0;
}