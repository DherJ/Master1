Noel Antonin
Malapel Florian

java -cp saxon9he.jar net.sf.saxon.Query monProgramme.xq |sed "s/></>$</g" |tr '$' '\n'

				Exercice 1.
	Question 1 :

query_bib1 : 
	-> renvoie les livres dans la bibliothèque dont le "publisher" est "Addison-Wesley" ecrit après 1991
query_bib2 :
	-> renvoies les titres avec chaque auteurs du livre
query_bib3 :
	-> renvoies tous les auteurs avec les livres qu'il a ecrit
query_bib4 :
	-> meme chose que la 4 grace a une fonction locale
query_bib5 :
	-> renvoie la liste des auteurs pour chaque titre, seulement si il y a au moins 1 auteur et maximum 2 auteurs. Si il y a plus de 2 auteurs, il renvoies les 2 premiers puis la balise <et-al>
query_bib6 :
	-> renvoie tous les auteurs, avec le nombre de livre auquels ils ont contribué
query_bib7 :
	-> renvoie la moyen des prix de livres par année
