// Edited by Malapel Florian
#include "ctx.h"

#define NBSEM 100


//  Semaphore
struct sem mutex, vide, plein;

//Ici vide correspond au nombre de place vide dans la liste
//et plein le nombre d'element dans la liste
void producteur(void *arg){
//    while (1) {
        printf("Production: %d\n", vide.sem_cpt);
        sem_down(&vide);
        sem_down(&mutex);
        sem_up(&mutex);
        sem_up(&plein);
        printf("Production: %d\n", vide.sem_cpt);
//    }
}

void consommateur(void *arg){
//    while (1) {
        printf("Consommation: %d\n", plein.sem_cpt);
        sem_down(&plein);
        sem_down(&mutex);
        sem_up(&mutex);
        sem_up(&vide);
        printf("Consommation: %d\n", plein.sem_cpt);
//    }
}

int main(int argc, char *argv[]){
    
    sem_init(&mutex, 1);
    sem_init(&vide, NBSEM);
    sem_init(&plein, 0);
    
    printf("Creating producteur...\n");
    create_ctx(16384, producteur, NULL);
    printf("Creating consommateur...\n");
    create_ctx(16384, consommateur, NULL);
    yield();
    printf("\nProgram end.\n");
    
    exit(EXIT_SUCCESS);
}