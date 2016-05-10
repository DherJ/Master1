#include "../header/drive.h"

/*
 main qui prend 3 arguments en entrée:
    ./frmt cylinder sector value
 il effectue ensuite le formatage de la partie du disque désignée par
 les valeurs de cylinder et sector, avec la valeur value passée en paramêtre
 Il affiche l'état de la partie du disque avant le formatage et après
 */
int main(int argc, char **argv){
    
    init_hard();
    
    int cylinder, sector, value;
    uchar buffer[HDA_SECTORSIZE];
    
    if(argc < 4){
        printf("exe: ./frmt cylinder sector value\n");
        printf("Numero de cylindre: ");
        scanf("%d", &cylinder);
        printf("\tsector: ");
        scanf("%d", &sector);
        printf("\tvalue: ");
        scanf("%d", &value);
    } else {
        cylinder = atoi(argv[1]);
        sector = atoi(argv[2]);
        value = atoi(argv[3]);
    }
    
    assert(cylinder >= 0 && cylinder < HDA_MAXCYLINDER);
    assert(sector >= 0 && sector < HDA_MAXSECTOR);
    
    // Affichage du secteur avant formatage
    read_sector( (uint)cylinder, (uint)sector, buffer );
    printf("Affichage avant formatage:\n");
    int i = 0;
    int j = 0;
    for(i=0; i<HDA_SECTORSIZE / 16; i++){
        printf("0%03d: ", i * 16);
        for(j=0; j<16; j++){
            printf(" %02x", buffer[i*16 + j]);
        }
        printf("\n");
    }
    
    // Formatage....
    format_sector( (uint)cylinder, (uint)sector, value);
    
    // Affichage du secteur après formatage
    read_sector( (uint)cylinder, (uint)sector, buffer );
    printf("\nAffichage après formatage:\n");

    for(i=0; i<HDA_SECTORSIZE / 16; i++){
        printf("0%03d: ", i * 16);
        for(j=0; j<16; j++){
            printf(" %02x", buffer[i*16 + j]);
        }
        printf("\n");
    }
    
    
    return 0;
}