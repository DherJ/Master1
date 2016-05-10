# KNAPIK Christopher - DHERSIN Jérome 
# TP1 ASE - 23 septembre 2013

# compilation complète
TP1ASE: try_mul.o try.o
	gcc -m32 -o TP1ASE try_mul.o try.o

try.o: try.c try.h
	gcc -m32 -o try.o -c try.c

try_mul.o: try_mul.c try.h
	gcc -m32 -o try_mul.o -c try_mul.c 
 
# clean
clean:
	rm -rf *.bak rm -rf *.o
 
# cleanall
cleanall: clean
	rm -rf TP1ASE
