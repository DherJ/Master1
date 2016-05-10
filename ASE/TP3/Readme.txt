/**
KNAPIK Christopher - DHERSIN Jérome 
TP3 ASE - 7 octobre 2014 
**/


Les différentes fonctions utilisées :


- void start_current_ctx();

Cette procédure permet tout simplement de démarrer le contexte actuel.


- int create_ctx(int stack_size, func_t F, void *args);

Cette fonction permet de créer un nouveau contexte, qui vient s'intégrer à la liste des autres contextes déjà créés. Cette liste est representée sous forme d'un "anneau" qui permet de passer d'un contexte au suivant de manière indéfini.


- void switch_to_ctx(struct ctx_s *new);

Cette procédure permet de passer d'un contexte à un autre. On "arrête" le contexte actuel pour se déplacer dans un autre contexte. On retournera au contexte initial plus tard. Dans notre cas, à chaque printf, on change de contexte pour executer à tour de rôle f_ping, f_pong et f_pang.


- int init_ctx(struct ctx_s *ctx, int stack_size, func_t F, void *args);

Cette fonction permet d'initialiser un contexte. On initialise toutes les valeurs de la structure ctx. Lors de l'initialisation, les registres esp et ebp pointent tous les deux en bas de la pile, et au même endroit. Attention, il est nécessaire de rajouter un -4. Si on ne le fait pas, on se retrouve hors de la pile (la première adresse située en dehors de la pile).


- void f_ping(void *args); void f_pong(void *args); void f_pang(void *args);

Ces 3 procédures vont, à tour de rôle, afficher des caractères. Elles ne servent qu'à observer les changements de contexte. A chaque changement de contexte, on "entre" dans une des 3 procédures et on affiche des caractères, puis on rechange de contexte pour "entrer" dans une autre des 3 procédures.


- void yield();

Cette procédure permet de passer au contexte suivant dans l'"anneau".

