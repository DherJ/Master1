#include <stdio.h>
#include <stdlib.h>
#include "../header/mmu_handler.h"
#include "../header/mi_syscall.h"
#include "../header/swap.h"
#include "../header/hardware.h"


extern void user_process();

int main(int argc, char **argv) {
	_out(MMU_PROCESS,1);
	if(init_swap("swap")) {
		fprintf(stderr, "Error in swap initialization\n");
		exit(EXIT_FAILURE);
	}
	if(init_hardware(HARDWARE_INI) == 0) {
		fprintf(stderr, "Error in hardware initialization\n");
		exit(EXIT_FAILURE);
	}
	IRQVECTOR[MMU_IRQ] = mmuhandler;

	_mask(0x1001);
	user_process();
	return 0;
}