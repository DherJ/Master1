# KNAPIK Christopher - DHERSIN Jérome 
# TP4 ASE - 14 octobre 2014



# compilation complète
TP4ASE: main.o sem.o hw.o
	gcc -g -m32 -o TP4ASE main.o sem.o hw.o

hw.o : hw.c hw.h
	gcc -g -m32 -o hw.o -c hw.c

sem.o: sem.c sem.h hw.h hw.c
	gcc -g -m32 -o sem.o -c sem.c

main.o: main.c sem.h
	gcc -g -m32 -o main.o -c main.c 
 
# clean
clean:
	rm -rf *.bak rm -rf *.o
 
# cleanall
cleanall: clean
	rm -rf TP4ASE
