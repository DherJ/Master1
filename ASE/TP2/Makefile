# KNAPIK Christopher - DHERSIN Jérome 
# TP2 ASE - 30 septembre 2014



# compilation complète
TP2ASE: main.o switch_ctx.o
	gcc -m32 -o TP2ASE main.o switch_ctx.o

switch_ctx.o: switch_ctx.c switch_ctx.h
	gcc -m32 -o switch_ctx.o -c switch_ctx.c

main.o: main.c switch_ctx.h
	gcc -m32 -o main.o -c main.c 
 
# clean
clean:
	rm -rf *.bak rm -rf *.o
 
# cleanall
cleanall: clean
	rm -rf TP2ASE
