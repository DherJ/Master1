/**
KNAPIK Christopher - DHERSIN Jérome 
TP4 ASE - 14 octobre 2014 
**/


Le but de ce TP est d'instaurer une synchronisation entre plusieurs contextes grâce à l'utilisation de sémaphores.




Les différentes fonctions utilisées :



- void start_current_ctx();

Cette procédure permet tout simplement de démarrer le contexte actuel.


- int create_ctx(int stack_size, func_t F, void *args);

Cette fonction permet de créer un nouveau contexte, qui vient s'intégrer à la liste des autres contextes déjà créés. Cette liste est representée sous forme d'un "anneau" qui permet de passer d'un contexte au suivant de manière indéfini.


- int init_ctx(struct ctx_s *ctx, int stack_size, func_t F, void *args);

Cette fonction permet d'initialiser un contexte. On initialise toutes les valeurs de la structure ctx. Lors de l'initialisation, les registres esp et ebp pointent tous les deux en bas de la pile, et au même endroit. Attention, il est nécessaire de rajouter un -4. Si on ne le fait pas, on se retrouve hors de la pile (la première adresse située en dehors de la pile).


- void switch_to_ctx(struct ctx_s *new);

Cette procédure permet de passer d'un contexte à un autre. On "arrête" le contexte actuel pour se déplacer dans un autre contexte. On libérera également la mémoire des contextes terminés.


- void yield();

Cette procédure permet de passer au contexte suivant dans l'"anneau".


- void sem_init(struct sem_s *sem, char* name, unsigned int val);

Cette procédure permet d'initialiser le sémaphore passé en entrée (nom, valeur ...).


- void sem_up(struct sem_s *sem);
 
Cette procédure permet d'incrémenter le compteur du sémaphore passé en entrée.


- void sem_down(struct sem_s *sem);
 
Cette procédure permet de décrémenter le compteur du sémaphore passé en entrée.


- void producteur(void *args); void consommateur(void *args);

Ces deux procédures permettent de tester la synchronisation des contextes grâce aux sémaphores en simulant un producteur et un consommateur. 



PS : Le programme n'est pas totalement fonctionnel. Il y a probablement un problème dans l'initialisation de sémaphore (les sémaphores sont initialisés manuellement directement dans le main pour l'éxecution).


