CC = gcc 
FLAGS = -m32 -o
CFLAGS = -W -Wall -pedantic -ansi -g
LIBS = -L./ -lhardware

SRC	= $(wildcard *.c)
OBJ	= $(SRC:.c=.o)

all: main

main: main.o drive.o dmps.o mbr.o volume.o frmt.o bloc.o
	$(CC) $(FLAGS) main main.o drive.o dmps.o mbr.o volume.o frmt.o bloc.o $(LIBS)

drive.o: drive.c drive.h common.h hardware.h
	$(CC) $(FLAGS) drive.o -c drive.c 

dmps.o: dmps.c common.h drive.h hardware.h
	$(CC) $(FLAGS) dmps.o -c dmps.c 

mbr.o: mbr.c common.h drive.h mbr.h hardware.h
	$(CC) $(FLAGS) mbr.o -c mbr.c 

volume.o: volume.c volume.h mbr.h drive.h common.h hardware.h
	$(CC) $(FLAGS) volume.o -c volume.c 

frmt.o: frmt.c common.h drive.h hardware.h
	$(CC) $(FLAGS) frmt.o -c frmt.c

bloc.o: bloc.c bloc.h common.h drive.h mbr.h hardware.h
	$(CC) $(FLAGS) bloc.o -c bloc.c

main.o: main.c volume.h mbr.h drive.h common.h hardware.h
	$(CC) $(FLAGS) main.o -c main.c

%.o: %.c
	$(CC) -o $@ -c $< $(CFLAGS)

#Run program
run:	
	./main

.PHONY: clean realclean
clean:
	$(RM) *.o TP7-ASE
realclean: clean 
	$(RM) vdiskA.bin vdiskB.bin
