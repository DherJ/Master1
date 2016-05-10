CC      = gcc -m32
CFLAGS  = -W -Wall -pedantic -ansi -g
LIBS    = -L./ -lhardware

SRC		= $(wildcard *.c)
OBJ		= $(SRC:.c=.o)
BINARIES= dmps frmt volume

all: $(BINARIES)

dmps: dmps.o drive.o
	$(CC) -o dmps dmps.o drive.o $(LIBS)

frmt: frmt.o drive.o
	$(CC) -o frmt frmt.o drive.o $(LIBS)

volume: volume.o drive.o mbr.o
	$(CC) -o volume volume.o drive.o mbr.o $(LIBS)

drive.o: drive.h hardware.h
dmps.o: drive.h hardware.h
frmt.o: drive.h hardware.h
mbr.o: drive.h hardware.h
volume.o: mbr.h

%.o: %.c
	$(CC) -o $@ -c $< $(CFLAGS)

.PHONY: clean realclean
clean:
	$(RM) *.o $(BINARIES)
realclean: clean 
	$(RM) vdiskA.bin vdiskB.bin
