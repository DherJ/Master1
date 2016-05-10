//
//  mi_kernel.c
//  TP9-Mémoire virtuelle
//
//  Created by jerome on 24/11/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../header/hw_config.h"
#include "../header/hardware.h"
#include "../header/mi_syscall.h"


static int current_process;


static int sum(void *ptr) {
	int i;
	int sum = 0;
	for(i = 0; i < PAGE_SIZE * N/2; i++) {
		sum += ((char*)ptr)[i];
	}
}


static void switche_to_process0(void) {
	current_process = 0;
	_out(MMU_CMD, MMU_RESET);
}


static void switche_to_process1(void) {
	current_process = 1;
	_out(MMU_CMD, MMU_RESET);
}


int main(int argc, char* argv) {

	_out(MMU_PROCESS,1);
	if(init_swap("swap")) {
		fprintf(stderr, "Error in swap initialization\n");
		exit(EXIT_FAILURE);
	}
	/* Initialise le matériel */
	if(init_hardware(HARDWARE_INI) == 0) {
		fprintf(stderr, "Error in hardware initialization\n");
		exit(EXIT_FAILURE);
	}

	/* Initialise le vecteur d'interruptions */
	IRQVECTOR[MMU_IRQ] = mmuhandler;
	IRQVECTOR[SYSCALL_SWTCH_0] = switch_to_process0; 
	IRQVECTOR[SYSCALL_SWTCH_1] = switch_to_process1; 
	
	/* Passe la main au code Utilisateur */
	_mask(0x1001);
	init();

}