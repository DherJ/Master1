Malapel Florian
Jérôme Dhersin 

There are different ways to execute the program, 
here are the basic case with 4 arguments:
	- java tp3 -m=10 -n=5 i=2 j=3
	Launch the graphical game

you can add -naive option if you just want the program to return you the score of a configuration with the naive function :
	- java tp3 -naive m=10 -n=5 i=2 j=3

you can add -dynamic option if you just want the program to return you the score of a configuration with the dynamic function
	- java tp3 -dynamic -m=10 -n=5 i=2 j=3

you can add -sym option if you just want the program to return you the score of a configuration with the dynamic function and the symmetries
	- java tp3 -sym -m=10 -n=5 i=2 j=3

you can add -f127 option if you juste want the program to return you all the configuration (127, 127, i, j) which have the score 127
	- java tp3 -f127 -m=10 -n=5 i=2 j=3

you can also add -game option if you just want the program to launch the graphical game:
	- java tp3 -naive m=10 -n=5 i=2 j=3