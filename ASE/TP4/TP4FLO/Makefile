all: TP4ASE

TP4ASE: main.o ctx.o hw.o
	gcc -m32 -O3 -o TP4ASE main.o ctx.o hw.o -Wall

hw.o: hw.c hw.h
	gcc -m32 -O3 -o hw.o -c hw.c -Wall

ctx.o: ctx.c ctx.h hw.h hw.c
	gcc -m32 -O3 -o ctx.o -c ctx.c -Wall

main.o: main.c ctx.h 
	gcc -m32 -O3 -o main.o -c main.c -Wall

#clean
clean:
	rm -rf *.o

#cleanall
cleaner: clean
	rm -rf TP4ASE
	clear

#run
run:
	./TP4ASE