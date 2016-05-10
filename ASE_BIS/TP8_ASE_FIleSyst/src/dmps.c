#include "../header/drive.h"

/*
 main qui prend 3 arguments en entrée:
    ./dmps cylinder sector value
 il affiche la partie du disque désignée par les arguments cylinder & sector
 */
int main(int argc, char **argv){
    
    init_hard();
    
    int cylinder, sector;
    uchar buffer[HDA_SECTORSIZE];
    
    if(argc < 3){
        printf("exe: ./dmps cylinder sector\n");
        printf("Numero de cylindre: ");
        scanf("%d", &cylinder);
        printf("\tsector: ");
        scanf("%d", &sector);
    } else {
        cylinder = atoi(argv[1]);
        sector = atoi(argv[2]);
    }
    
    assert(cylinder >= 0 && cylinder < HDA_MAXCYLINDER);
    assert(sector >= 0 && sector < HDA_MAXSECTOR);

    read_sector( (uint)cylinder, (uint)sector, buffer );
    int i = 0;
    int j = 0;
    for(i=0; i<HDA_SECTORSIZE / 16; i++){
        printf("0%03d: ", i * 16);
        for(j=0; j<16; j++){
            printf(" %02x", buffer[i*16 + j]);
        }
        printf("\n");
    }
    
    
    return 0;
}