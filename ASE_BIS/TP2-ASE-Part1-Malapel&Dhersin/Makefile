all: main

main: main.o function.o
	gcc -m32 -o main main.o function.o

main.o: main.c
	gcc -m32 -o main.o -c main.c

function.o: function.c header.h
	gcc -m32 -o function.o -c function.c

clean:
	rm -rf *.o

#cleanall
cleanall: clean
	rm -rf all
	clear
