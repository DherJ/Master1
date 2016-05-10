#ifndef mmu_handler_h
#define mmu_handler_h

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hw_config.h"
#include "hardware.h"
#include "swap.h"
#include "mi_syscall.h"

static int current_process;

extern void mmuhandler();
static int ppage_of_vaddr(int process, unsigned vaddr);
extern void print_default_page();
extern void reset_default_page();

#endif /* mmu_handler_h */