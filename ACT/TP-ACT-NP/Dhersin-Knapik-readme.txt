/*------------------------------------ TP ACT Complexité des problèmes et classe NP ------------------------------------*\
/*																																				   *\
/*-----------------------------------------------------			Dhersin Jérôme	      -----------------------------------------------------*\
/*-----------------------------------------------------	      Knapik Christopher    -----------------------------------------------------*\
/*-----------------------------------------------------	    Master 1 informatique   -----------------------------------------------------*\
/*-----------------------------------------------------	            2014-2015          ------------------------------------------------------*\
/*-------------------------------------------------------------------------------------------------------------------------------------------------*\


Question 1 :

Notion de certificat : C'est un ensemble de données qui peuvent appartenir ou non à une solution possible du problème.
Les certificats vont contenir :

	- nombre d'objets                                     /* Optionnel car la donnée est présente dans l'instance du problème */
	- les objets (poids)                                   /* Optionnel car la donnée est présente dans l'instance du problème */
	- nombre de sac                                      /* Optionnel car la donnée est présente dans l'instance du problème */
	- capacité des sacs                                 /* Optionnel car la donnée est présente dans l'instance du problème */
	- la répartition des objets dans les sacs

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	

Implémentation des certificats :

Dans les certificats nous allons retrouver la répartition des objets dans les différents sacs.
Donc nous aurons un tableau de sacs, puis dans chaque case de ce tableau nous aurons un ensemble d'objets.

exemple :  
Nous avons une liste d'objets :    objet0, objet1, objet2, objet3, objet4.
Nous avons 3 sacs.

sac :     [        sac 0             ,         sac1            ,                    sac2              ]
            [  [ objet0, objet4 ]    ,      [ objet1]          ,          [ objet2, objet3 ]       ]

Donc dans notre exemple nous avons :
objet0 dans sac0
objet1 dans sac1
objet2 dans sac2
objet3 dans sac2
objet4 dans sac0



Deuxième implémentation : 

prenons par exemple 3 sacs :
.	-sac0
	-sac1	
	-sac2
objets :     [ 7, 1, 4, 2, 4, 2  ] représente le tableau des poids 
objet0 -> poids = 7 ....
repartition [  0, 2, 0, 1, 1, 2 ]

Ici nous avons un tableau dont les indices correspondent au numéro de l'objet, et le contenu des cases correspond à l'indice du sac dans lequel est l'objet.
Ici à chaque élément du tableau des poids va correspondre un sac dans le tableau répartition.
C'est à dire pour savoir dans quel sac l'objet i se trouve il suffit de regarder la case i du tableau répartition.

Donc dans notre exemple nous avons :
objet0 dans sac0
objet1 dans sac2
objet2 dans sac0
objet3 dans sac1
objet4 dans sac1
objet5 dans sac2


Das ce cas la taille du certificat = nObjet * log nObjet bits

Formule du cours : L = { u / il existe c. A(c,u) = Vrai, |c| <= Q(|u|) }
c = certificat
u = problème étudié

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

On sait que |c| <= k
Pour coder un ensemble d'objets on peut utiliser leurs indices € 1, ...., nObjet  -> <= log(base 2) nObjet bits
taille certificat = nObjet * log nObjet bits


Taille de l'entrée = log nObjet + ( somme de 1 à nObjet de log xi ) + log nSac + log c
Nous avons :
int nbObjet         ->    pour coder cet élément il faut log nObjet bits
int xnObjets        ->   pour coder cet élément il faut faire la somme des bits des différents objets  -->  somme de 1 à nObjet de log xi bits  
int nSac             ->   pour coder cet élément il faut log nSac bits
int c                   ->   pour coder cet élément il faut log c bits

Rappel : 
Taille des certificats = nObjet * log nObjet bits
On a donc nObjet * log nObjet bits <= log nObjet + ( somme de 1 à nObjet de log xi ) + log nSac + log c
On peut en conclure que la taille du certificat est bien bornée polynomialement par rapport à la taille de l’entrée.


-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Algo de vérification : 

On donne une instance du problème (quels objets va dans quel sac) en entrée puis l'algo renvoie vrai si l'instance comporte une solution, faux sinon.
Vérifie si aucun sac ne déborde.

tableau repartition : entier
i : entier
poid_tot : entier
tableau sac : entier
pour tous les éléments i dans repartition faire	
	sac[ repartition[ i ] ] = objet [ i ] + sac[ repartition[ i ] ];   /* on calcule la place occupée dans chaque sac */
fin pour
pour tous les éléments i dans sac faire
	si ( sac[ i ] > capacite ) alors        /* On vérifie que chaque sac ne déborde passe l'algo s'arrête au 1er sac qui déborde qu'on trouvera */
		retourner faux
	fin si
fin pour
retourner vrai

Complexité :
=> Algorithme en O(nObjet) + O(nSac)


Question 3: tester tous les certificats, trouver un algo qui permet de stocker un certificat et permet de passer de l'un à l'autre.

Proposition pour classer tous les certificats :

Prenons par exemple une suite d'objets : 2, 3, 1, 2, 5, 3
Nous avec 3 sac de capacité 6.
Certificat 1 :                                                 Certificat 2 :                                                 Certificat 2 :                                
objet0 dans le sac 0                                     objet0 dans le sac 1                                     objet0 dans le sac 2
objet1 dans le sac 0                                     objet1 dans le sac 1                                     objet1 dans le sac 2
objet2 dans le sac 0                                     objet2 dans le sac 1                                     objet2 dans le sac 2
objet3 dans le sac 1                                     objet3 dans le sac 0                                     objet3 dans le sac 1
objet4 dans le sac 2                                     objet4 dans le sac 2                                     objet4 dans le sac 0
objet5 dans le sac 1                                     objet5 dans le sac 0                                     objet5 dans le sac 1

Poids des sacs :
sac0 = 6                                                      sac0 = 5                                                     sac0 = 5
sac1 = 5                                                      sac1 = 6                                                     sac1 = 5
sac2 = 5                                                      sac2 = 5                                                     sac2 = 6

Ordre : 
Certificat 1 -> Certificat 2 -> Certificat 3
L'ordre se fera par rapport au poids des sacs. Dans le sens du 1er sac au dernier (indice des sacs).




2. Réduction polynomialement

4.1

Problème de partition :

Donnée:
n –un nombre d’entiers
x1, ., ., ., xn les entiers.

Sortie:  Oui, si il existe un sous-ensemble de [1::n] tel que la somme des xi correspondants soit exactement la moitié de la somme des xi
Ces données correspondent au même format que les données (nombre d'objet et liste des poids) du problème BinPack donc nous pourvons en conclure
que la taille de la donnée est :
n –un nombre d’entiers         n log n bits.
x1, ., ., ., xn les entiers.   n * k log k bits avec k l'indice de l'élément.

Taille de la donnée = n log n + n * k log k bits

On sait égalment que le problème BinPack est NP.

pb BINPACK              <-                pb Partition
nObjet                                           n
S                                                  S'       -> S = ensemble des entiers, S' = ensemble des poids
nSac                                             2  
cap                                               somme des éléments de l'ensemble / 2 

L'élément n dans le prpblème Partition va correspondre à l'élément nObjet dans le problème BinPack.
Pour chaque élément (ensemble des entiers) du problème Partition va correspondre un élément (un poids) dans le problème BinPack. Cette opération est donc polynomiale en la taille de la donné du problème partition.
Donc le problème Partition se réduit polynomialement en BinPack.

4.2

Si le problème Partition est connu comme NP-dur, comme Partition se réduit en BinPack alors le problème BinPack est au plus aussi dur que le problème Partition.

5.1

Sum est un cas particulier de Partition.
Le problème SUM se réduit polynomialement en Partition, et comme vu précédement le problème Partition se réduit polynomialement en BinPack.
Donc nous pouvons en conclure que par transitivité le problème SUM se réduit polynomiaIement en BinPACK.

5.2

Réduction de SUM en partition :
	définir s la somme des éléments de S.
	si (2c - s) <0, alors 
		construire S' avec l'élément supplémentaire (s - 2c).     /* c représente l'entier cible */
		output S'.
	sinon 	
		construire S' = S  avec l'élément supplémentaire (2c - s).
		output S' . la fonction est reminée.
