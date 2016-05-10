##  Makefile
##  TP2-FileSystem-Malapel&Dhersin
##
##  Last update - by jerome on 16/11/2015.
## 		add : mknfs, dfs, create_bloc_2(test des free_blocs en les insérant dans un ordre trié)
##  Copyright © 2015 Malapel&Dhersin. All rights reserved.


LIBS = -L./libs -lhardware

SRC = ./src/
INC = ./header/
BIN = ./bin/

all: test_vol mknfs dfs test_vofb create_bloc_2 create_bloc delete_vol create_vol print_vol frmt dmps frmt_reverse create_inode

test_vol: $(BIN)test_vol.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o
	gcc -m32 -o test_vol $(BIN)test_vol.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(LIBS)

mknfs: $(BIN)mknfs.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o
	gcc -m32 -o mknfs $(BIN)mknfs.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(LIBS)

dfs: $(BIN)dfs.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o
	gcc -m32 -o dfs $(BIN)dfs.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(LIBS)

test_vofb: $(BIN)test_vofb.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(BIN)alloc.o $(BIN)inode.o 
	gcc -m32 -o test_vofb $(BIN)test_vofb.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(BIN)alloc.o $(BIN)inode.o $(LIBS)

create_bloc_2: $(BIN)create_bloc_2.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(BIN)alloc.o
	gcc -m32 -o create_bloc_2 $(BIN)create_bloc_2.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(BIN)alloc.o $(LIBS)

create_bloc: $(BIN)create_bloc.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(BIN)alloc.o
	gcc -m32 -o create_bloc $(BIN)create_bloc.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(BIN)alloc.o $(LIBS)

create_inode: $(BIN)create_inode.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(BIN)alloc.o $(BIN)inode.o
	gcc -m32 -o create_inode $(BIN)create_inode.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(BIN)alloc.o $(BIN)inode.o $(LIBS)

delete_vol: $(BIN)delete_vol.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o
	gcc -m32 -o delete_vol $(BIN)delete_vol.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(LIBS)

create_vol: $(BIN)create_vol.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o
	gcc -m32 -o create_vol $(BIN)create_vol.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(LIBS)

print_vol: $(BIN)print_vol.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o
	gcc -m32 -o print_vol $(BIN)print_vol.o $(BIN)drive.o $(BIN)mbr.o $(BIN)volume.o $(LIBS)

frmt: $(BIN)frmt.o $(BIN)drive.o
	gcc -m32 -o frmt $(BIN)frmt.o $(BIN)drive.o $(LIBS)

dmps: $(BIN)dmps.o $(BIN)drive.o
	gcc -m32 -o dmps $(BIN)dmps.o $(BIN)drive.o $(LIBS)

frmt_reverse: $(BIN)frmt_reverse.o $(BIN)drive.o
	gcc -m32 -o frmt_reverse $(BIN)frmt_reverse.o $(BIN)drive.o $(LIBS)


$(BIN)test_vofb.o: $(SRC)test_vofb.c $(SRC)drive.c $(SRC)volume.c $(SRC)mbr.c $(SRC)inode.c $(INC)volume.h $(INC)drive.h $(INC)mbr.h $(INC)inode.h $(INC)hardware.h
	gcc -m32 -o $(BIN)test_vofb.o -c $(SRC)test_vofb.c

$(BIN)test_vol.o: $(SRC)test_vol.c $(SRC)drive.c $(SRC)volume.c $(SRC)mbr.c $(INC)volume.h $(INC)drive.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)test_vol.o -c $(SRC)test_vol.c

$(BIN)mknfs.o: $(SRC)make_new_file_syst.c $(SRC)drive.c $(SRC)volume.c $(SRC)mbr.c $(INC)volume.h $(INC)drive.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)mknfs.o -c $(SRC)make_new_file_syst.c

$(BIN)dfs.o: $(SRC)display_file_syst.c $(SRC)drive.c $(SRC)volume.c $(SRC)mbr.c $(INC)volume.h $(INC)drive.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)dfs.o -c $(SRC)display_file_syst.c

$(BIN)create_bloc_2.o: $(SRC)create_bloc_2.c $(SRC)drive.c $(SRC)volume.c $(SRC)mbr.c $(INC)volume.h $(INC)drive.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)create_bloc_2.o -c $(SRC)create_bloc_2.c

$(BIN)create_bloc.o: $(SRC)create_bloc.c $(SRC)drive.c $(SRC)volume.c $(SRC)mbr.c $(INC)volume.h $(INC)drive.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)create_bloc.o -c $(SRC)create_bloc.c

$(BIN)delete_vol.o: $(SRC)delete_vol.c $(SRC)drive.c $(SRC)volume.c $(SRC)mbr.c $(INC)volume.h $(INC)drive.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)delete_vol.o -c $(SRC)delete_vol.c
	
$(BIN)create_vol.o: $(SRC)create_vol.c $(SRC)drive.c $(SRC)volume.c $(SRC)mbr.c $(INC)volume.h $(INC)drive.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)create_vol.o -c $(SRC)create_vol.c

$(BIN)print_vol.o: $(SRC)print_vol.c $(SRC)drive.c $(SRC)volume.c $(SRC)mbr.c $(INC)volume.h $(INC)drive.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)print_vol.o -c $(SRC)print_vol.c

$(BIN)frmt.o: $(SRC)frmt.c $(SRC)drive.c $(INC)drive.h $(INC)hardware.h
	gcc -m32 -o $(BIN)frmt.o -c $(SRC)frmt.c

$(BIN)dmps.o: $(SRC)dmps.c $(SRC)drive.c $(INC)drive.h $(INC)hardware.h
	gcc -m32 -o $(BIN)dmps.o -c $(SRC)dmps.c

$(BIN)frmt_reverse.o: $(SRC)frmt_reverse.c $(SRC)drive.c $(INC)drive.h $(INC)hardware.h
	gcc -m32 -o $(BIN)frmt_reverse.o -c $(SRC)frmt_reverse.c

$(BIN)mbr.o: $(SRC)mbr.c $(SRC)drive.c $(INC)drive.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)mbr.o -c $(SRC)mbr.c

$(BIN)volume.o: $(SRC)volume.c $(SRC)drive.c $(INC)drive.h $(INC)volume.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)volume.o -c $(SRC)volume.c

$(BIN)alloc.o: $(SRC)alloc.c $(INC)alloc.h $(SRC)drive.c $(INC)drive.h $(INC)volume.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)alloc.o -c $(SRC)alloc.c	

$(BIN)drive.o: $(SRC)drive.c $(INC)drive.h $(INC)hw.h $(INC)hardware.h  
	gcc -m32 -o $(BIN)drive.o -c $(SRC)drive.c

$(BIN)inode.o: $(SRC)inode.c $(INC)inode.h $(SRC)drive.c $(SRC)alloc.c $(INC)drive.h $(INC)volume.h $(INC)mbr.h $(INC)alloc.h $(INC)hardware.h
	gcc -m32 -o $(BIN)inode.o -c $(SRC)inode.c

$(BIN)create_inode.o: $(SRC)create_inode.c $(SRC)drive.c $(SRC)volume.c $(SRC)mbr.c $(SRC)drive.c $(SRC)inode.c $(INC)volume.h $(INC)alloc.h $(INC)drive.h $(INC)inode.h $(INC)mbr.h $(INC)hardware.h
	gcc -m32 -o $(BIN)create_inode.o -c $(SRC)create_inode.c
	



clean:
	rm -rf $(BIN)*.o
	rm -rf test_vol
	rm -rf dfs
	rm -rf mknfs
	rm -rf create_bloc_2
	rm -rf create_bloc
	rm -rf create_vol
	rm -rf create_inode
	rm -rf delete_vol
	rm -rf print_vol
	rm -rf frmt
	rm -rf frmt_reverse
	rm -rf dmps
	rm -rf test_vofb

#cleanall
cleanall: clean
	rm -rf all
	clear