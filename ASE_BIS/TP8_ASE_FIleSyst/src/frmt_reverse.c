#include "../header/drive.h"


/*
 main qui prend 4 arguments en entrée:
 ./frmt_reverse cylinder sector nb_sector value
 il effectue ensuite le formatage (en reverse) de la partie du disque désignée par
 les valeurs de cylinder et sector jusqu'à sector+nbSector, avec la valeur value passée en paramêtre
 Il affiche l'état de la partie du disque avant le formatage et après
 */
int main(int argc, char **argv){
    
    init_hard();
    
    int cylinder, sector, nbSector, value;
    uchar buffer[HDA_SECTORSIZE];
    
    if(argc < 5){
        printf("exe: ./frmt_reverse cylinder sector nb_sector value\n");
        printf("Numero de cylindre: ");
        scanf("%d", &cylinder);
        printf("\tsector: ");
        scanf("%d", &sector);
        printf("\tHow many sector to format after: ");
        scanf("%d", &nbSector);
        printf("\tvalue: ");
        scanf("%d", &value);
    } else {
        cylinder = atoi(argv[1]);
        sector = atoi(argv[2]);
        nbSector = atoi(argv[3]);
        value = atoi(argv[4]);
    }
    
    assert(cylinder >= 0 && cylinder < HDA_MAXCYLINDER);
    assert(sector >= 0 && sector < HDA_MAXSECTOR);
    
    // Affichage du secteur avant formatage
    printf("Affichage des secteurs avant formatage:\n");
    int c = 0;
    int i = 0;
    int j = 0;
    for(c = nbSector; c >=0; c--){
        read_sector( (uint)cylinder, (uint)sector, buffer );
        for(i=0; i<HDA_SECTORSIZE / 16; i++){
            printf("0%03d: ", i * 16);
            for(j=0; j<16; j++){
                printf(" %02x", buffer[i*16 + j]);
            }
        printf("\n");
        }
    }
    
    // Formatage....
    
    format_n_sector_reverse( (uint)cylinder, (uint)sector, (uint)nbSector, value);
    
    // Affichage du secteur après formatage
    read_sector( (uint)cylinder, (uint)sector, buffer );
    printf("\nAffichage après formatage:\n");
    for(c = nbSector; c >=0; c--){
        for(i=0; i<HDA_SECTORSIZE / 16; i++){
            printf("0%03d: ", i * 16);
            for(j=0; j<16; j++){
                printf(" %02x", buffer[i*16 + j]);
            }
            printf("\n");
        }
    }
    
    
    return 0;
}