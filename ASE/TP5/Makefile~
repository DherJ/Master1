CC=gcc
ROOTDIR=home/chris/Documents/M1/ASE/TP/TP5

LIBDIR  = $(ROOTDIR)/lib
INCDIR  = -I$(ROOTDIR)/include
LIBS    = -L$(LIBDIR) -lhardware


all: drive

drive: drive.o dmps.o
	${CC} -o drive dmps.o drive.o ${LIBS}

drive.o: drive.c
	${CC} -o drive.o -c drive.c ${INCDIR}

dmps.o: dmps.c
	${CC} -o dmps.o -c dmps.c ${INCDIR}

clean:
	rm -rf *.o *.dSYM

realclean: clean
	rm -rf drive
