//
//  alloc.c
//  TP2-FileSystem-Malapel&Dhersin
//
//  Created by florian on 03/11/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include "../header/alloc.h"
#include "../header/volume.h"


uint new_bloc() {
    int res = superbloc.first_free_bloc;
    struct free_bloc_s fb;
    read_bloc_n(current_vol, res, (uchar *) &fb, sizeof(struct free_bloc_s));
    superbloc.first_free_bloc = fb.next_free_bloc;
    superbloc.nb_free--;
    save_super();
    return res;
}

// void free_bloc(uint bloc) {
//     assert(bloc != 0);
//     struct free_bloc_s new_first_bloc;
//     new_first_bloc.next_free_bloc = superbloc.first_free_bloc;
//     superbloc.first_free_bloc = bloc;
//     write_bloc_n(current_vol, bloc, (uchar *)&new_first_bloc, sizeof(struct superbloc_s));
//     superbloc.nb_free++;
// }

void free_bloc(uint bloc) {
    assert(bloc != 0);
    struct free_bloc_s new_free_bloc, currentFreeBloc;
    if(bloc == superbloc.first_free_bloc) {
        printf("the bloc %d is already free", bloc);
    } else {
        if(superbloc.first_free_bloc == 0) {
            new_free_bloc.next_free_bloc = superbloc.first_free_bloc;
            superbloc.first_free_bloc = bloc;
            write_bloc_n(current_vol, bloc, (uchar *)&new_free_bloc, sizeof(struct superbloc_s));
            superbloc.nb_free++;
        }
        else if(bloc < superbloc.first_free_bloc && superbloc.first_free_bloc != 0) {
            new_free_bloc.next_free_bloc = superbloc.first_free_bloc;
            superbloc.first_free_bloc = bloc;
            write_bloc_n(current_vol, bloc, (uchar *)&new_free_bloc, sizeof(struct superbloc_s));
            superbloc.nb_free++;
        }
        else {
            int numCurrentFreeBloc = superbloc.first_free_bloc;
            read_bloc_n(current_vol, superbloc.first_free_bloc, (uchar *)&currentFreeBloc, sizeof(struct free_bloc_s));
            if(currentFreeBloc.next_free_bloc == 0) {
                currentFreeBloc.next_free_bloc = bloc;
                new_free_bloc.next_free_bloc = 0;
                write_bloc_n(current_vol, numCurrentFreeBloc, (uchar *)&currentFreeBloc, sizeof(struct superbloc_s));
                write_bloc_n(current_vol, bloc, (uchar *)&new_free_bloc, sizeof(struct superbloc_s));
                superbloc.nb_free++;
            }
            else {
                while(currentFreeBloc.next_free_bloc != 0) {
                    // bloc déjà présent dans la liste des blocs libres
                    if(bloc == numCurrentFreeBloc || bloc == currentFreeBloc.next_free_bloc) {
                        printf("the bloc %d is already free", bloc);
                        break;
                    } else {
                        // insertion en fin de liste
                        if(currentFreeBloc.next_free_bloc == 0 && bloc > numCurrentFreeBloc) {
                            currentFreeBloc.next_free_bloc = bloc;
                            new_free_bloc.next_free_bloc = 0;
                            write_bloc_n(current_vol, numCurrentFreeBloc, (uchar *)&currentFreeBloc, sizeof(struct superbloc_s));
                            write_bloc_n(current_vol, bloc, (uchar *)&new_free_bloc, sizeof(struct superbloc_s));
                            superbloc.nb_free++;
                            break;
                        }
                        else if(currentFreeBloc.next_free_bloc != 0 && bloc > numCurrentFreeBloc) {
                            if(bloc > numCurrentFreeBloc && bloc < currentFreeBloc.next_free_bloc) {
                                new_free_bloc.next_free_bloc = currentFreeBloc.next_free_bloc;
                                currentFreeBloc.next_free_bloc = bloc;
                                write_bloc_n(current_vol, numCurrentFreeBloc, (uchar *)&currentFreeBloc, sizeof(struct superbloc_s));
                                write_bloc_n(current_vol, bloc, (uchar *)&new_free_bloc, sizeof(struct superbloc_s));
                                superbloc.nb_free++;
                                break;
                            } else {
                                numCurrentFreeBloc = currentFreeBloc.next_free_bloc;
                                read_bloc_n(current_vol, currentFreeBloc.next_free_bloc, (uchar *)&currentFreeBloc, 
                                    sizeof(struct free_bloc_s));
                            }
                        }
                    }
                }
                if(currentFreeBloc.next_free_bloc == 0) {
                    currentFreeBloc.next_free_bloc = bloc;
                    new_free_bloc.next_free_bloc = 0;
                    write_bloc_n(current_vol, numCurrentFreeBloc, (uchar *)&currentFreeBloc, sizeof(struct superbloc_s));
                    write_bloc_n(current_vol, bloc, (uchar *)&new_free_bloc, sizeof(struct superbloc_s));
                    superbloc.nb_free++;
                }
            }
        }
    }
}