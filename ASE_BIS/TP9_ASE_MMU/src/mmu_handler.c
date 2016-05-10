//
//  mmu_handler.c
//  TP9-Mémoire virtuelle
//
//  Created by jerome on 24/11/2015.
//  Copyright © 2015 Malapel&Dhersin. All rights reserved.
//

#include "../header/mmu_handler.h"

static int current_process;

int default_page_number = 0;
unsigned current_vpage = -1;
unsigned current_ppage = -1;

struct tlb_entry_s {
	unsigned tlb_unused:8;
	unsigned tlb_vpage:12;
	unsigned tlb_ppage:8;
	unsigned tlb_acces:3;
	unsigned tlb_active:1;
};


void print_default_page() {
	printf("Default page number : %d\n", default_page_number);
}

void reset_default_page() {
	default_page_number = 0;
}


/* version 1 : 1 seule page physique, toutes les pages virtuelles sont mappées sur la page physique 1 */
void mmuhandler() {

	unsigned vaddr, vpage, ppage;
	default_page_number++;
	
	vaddr = _in(MMU_FAULT_ADDR);
	ppage = ppage_of_vaddr(current_process, vaddr);
	vpage = (vaddr >> 12) & 0xFF;

	//On sauvegarde la page dans le fichier de swap
	if(current_vpage != -1 && current_ppage != -1) { 
		store_to_swap(current_vpage, current_ppage);
	}

	//On supprime le mapping dans la TLB pour cette page
	struct tlb_entry_s tlb_request;

 	tlb_request.tlb_vpage = current_vpage;
	_out(TLB_DEL_ENTRY, *(int*)&tlb_request);
	current_ppage = ppage;
	current_vpage = vpage;

	//Charger la page correspondant à l'adresse fautive
	fetch_from_swap(current_vpage, current_ppage);

	//Mettre à jour la TLB
	tlb_request.tlb_vpage = current_vpage;	
	tlb_request.tlb_ppage = 1;
	tlb_request.tlb_acces = 0x7;
	tlb_request.tlb_active = 1;

	_out(TLB_ADD_ENTRY, *(int*)&tlb_request);
}


static int ppage_of_vaddr(int process, unsigned vaddr) {

	// if((vaddr > virtual_memory + VM_SIZE - 1) || (vaddr < virtual_memory)) {
	// 	return -1;
	// }

	// struct tlb_entry_s *tlb_entries;
	// int val_entries = _in(TLB_ENTRIES);
	// tlb_entries = (struct tlb_entry_s)&val_entries;
	// int vpage = (vaddr >> 12) & 0xFF;

	// if (vpage > N/2 || vpage < 0) return -1;
	// 	return -1;

	// int i = 0;
	// for(i = 0; i < TLB_SIZE; i++) {
	// 	if(tlb_entries[i].tlb_vpage == vpage)
	// 		return tlb_entries[i].tlb_vpage;
	// 	break;
	// }
	// return -1;

	if(vaddr > ((int)virtual_memory+(N/2)*PAGE_SIZE-1) || vaddr < (int)virtual_memory)
		return -1;
	else return vaddr + 1 + process * (N/2);
}