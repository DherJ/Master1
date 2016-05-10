/**
KNAPIK Christopher - DHERSIN Jérome
TP1 ASE - 23 septembre 2013 
**/ 

#include "try.h"

int mul(int depth){
    	int i;
	switch (scanf("%d", &i)) {
        	case EOF :	
			return 1; // neutral element
        	case 0 :
        	   	return mul(depth+1); // erroneous read
        	case 1 :
        	    	if (i) return i * mul(depth+1);
       			else
			//return 0;	
			{
				puts("Info Registre avant throw");				
				afficherInfoRegistre();
				throw(pctx_global,0);
			}
    }
}


int main(){
    	int product=0;

	puts("Info Registre avant mul");
	afficherInfoRegistre();
	// affiche les valeurs d'esp/ebp avant mul

	pctx_global = (struct ctx_s*) malloc(sizeof(struct ctx_s));
	printf("Inserez une liste d'entiers :\n");	
	product = try(pctx_global, &mul, 0); // calcule le produit
   	printf("product = %d\n", product); // affiche le résultat du produit

	puts("Info Registre après throw");
	afficherInfoRegistre();
	// affiche les valeurs d'esp/ebp après mul
	return 0;
}
