
// Déclaration des objets utilisés dans le programme
PFont font;

int screenWidth, screenHeight;

Interface myInterface;
BlocInterface itf_controlePoste;
Console console;

Pince pince;

Button d1;
Button d2;
Button d3;
//Button utiliserPince;

Tapis t1;
Tapis t2;
Tapis t3;

//Piece piece1;
//Piece piece2;
//Piece piece3;

Capteur c1;
Capteur c2;
Capteur c3;

Poste poste1;
Poste poste2;
Poste poste3;
boolean one_button_active = false;
int num_button_active = 0;


void setup() {
  smooth();
  screenWidth = 800;
  screenHeight = 600;
  size(screenWidth, screenHeight); //Taille de la fenêtre
  //test = loadImage("img/test2.jpg");
  font = loadFont("LucidaConsole-11.vlw");
  textFont(font);

  // Initialisation de l'interface et de la console
  myInterface = new Interface(0, 450, screenWidth, 29);
  console = new Console(0, 450, screenWidth, 29);
  console.setText("Les messages d'information seront affichés dans cette boite", "green");
  
  itf_controlePoste = new BlocInterface("Contrôle des postes", myInterface.xInterface, console.y+console.hauteur, screenWidth, screenHeight-myInterface.yInterface);
  itf_controlePoste.setText("Contrôle des postes");
  itf_controlePoste.setColorBG(color(170));
  itf_controlePoste.setColorText(color(0, 50, 200));

  myInterface.addBlocInterface(itf_controlePoste);
  myInterface.setConsole(console);

  d1 = new Button("d1");
  d1.setText("d1");
  d1.setPosition(100, 520);
  d1.setSize(30, 20);
  d1.setColorBG("green");
  d1.setColorText("black");

  d2 = new Button("d2");
  d2.setText("d2");
  d2.setPosition(140, 520);
  d2.setSize(30, 20);
  d2.setColorBG("green");
  d2.setColorText("black");

  d3 = new Button("d3");
  d3.setText("d3");
  d3.setPosition(180, 520);
  d3.setSize(30, 20);
  d3.setColorBG("green");
  d3.setColorText("black");


  t1 = new Tapis(150, 380, 100, 30);
  //t1.allouerPiece(piece1);
  t1.ajouterPiece(t1.xTapis+30, t1.yTapis-30, 40, 30);
  t2 = new Tapis(350, 380, 100, 30);
  //t2.allouerPiece(piece2);
  t3 = new Tapis(550, 380, 100, 30);

  // Pince(float _xBras, float _yBras, float _largeurBras, float _hauteurBras,
  //  float _largeurPince, float _hauteurPince)
  pince = new Pince(100, 100, 20, 100, 40, 10);
  //pince.setHauteurBrasMax(t1.yTapis - piece1.y);
  pince.setHauteurBrasMax(t1.yTapis); 
  pince.setSpeed(3);
  //pince.attribuerBouton(utiliserPince);
  pince.bool_pince_ouverte=false;

  //  utiliserPince = new Button("Utiliser Pince");
  //  utiliserPince.setText("Ouvrir");
  //  utiliserPince.setPosition(300, 560);
  //  utiliserPince.setSize(100, 20);
  //  utiliserPince.setColorBG("green");
  //  utiliserPince.originalColorButton = utiliserPince.colorButton;
  //  utiliserPince.setColorText("black");
  //  utiliserPince.doubleUse = true;

  c1 = new Capteur("c1", 190, 80, pince.largeurBras, 20);
  c1.setValue(190, 5);
  c1.setPince(pince);

  c2 = new Capteur("c2", 390, 80, pince.largeurBras, 20);
  c2.setValue(390, 5);
  c2.setPince(pince);

  c3 = new Capteur("c3", 590, 80, pince.largeurBras, 20);
  c3.setValue(590, 5);
  c3.setPince(pince);

  poste1 = new Poste(d1, c1, t1);
  poste2 = new Poste(d2, c2, t2);
  poste3 = new Poste(d3, c3, t3);

  pince.definirNbPoste(3);

  poste1.setNumPoste(0);
  pince.attribuerPoste(poste1, 0);
  poste2.setNumPoste(1);
  pince.attribuerPoste(poste2, 1);
  poste3.setNumPoste(2);
  pince.attribuerPoste(poste3, 2);
  pince.setDefaultPoste(poste1, 0);

  //print("test poste 1 : " +pince.listPoste[0].tapis.xTapis+ "\n");
  //print("test poste 2 : " +pince.listPoste[1].tapis.xTapis+ "\n");
  //print("test poste 3 : " +pince.listPoste[2].tapis.xTapis+ "\n");
}

void draw() {
  frameRate(60);
  background(255);
  
  myInterface.display();

  fill(0, 0, 0);
  rect(50, 80, 700, 20);

  t1.display();
  t2.display();
  t3.display();

  pince.display();

  //piece1.display();
  //piece2.display();
  //piece3.display();

  d1.display();
  d2.display();
  d3.display();
  //utiliserPince.display();

  c1.display();
  c1.detect(pince.xBras);

  c2.display();
  c2.detect(pince.xBras);

  c3.display();
  c3.detect(pince.xBras);

  /*
  if (!pince.listPoste[0].button.buttonActive &&
   !pince.listPoste[1].button.buttonActive &&
   !pince.listPoste[2].button.buttonActive) {
   one_button_active = false;
   }
   */

    if (pince.listPoste[0].button.buttonActive && !pince.listPoste[1].button.buttonActive && !pince.listPoste[2].button.buttonActive) {
      pince.actionPiece(pince.listPoste[0]);
      one_button_active = pince.listPoste[0].button.buttonActive;
      print("Test one_button_active : " +one_button_active+ "\n");
    } else if (pince.listPoste[1].button.buttonActive && !pince.listPoste[0].button.buttonActive && !pince.listPoste[2].button.buttonActive) { 
      pince.actionPiece(pince.listPoste[1]);
      one_button_active = pince.listPoste[1].button.buttonActive;
    } else if (pince.listPoste[2].button.buttonActive && !pince.listPoste[0].button.buttonActive && !pince.listPoste[1].button.buttonActive) {
      pince.actionPiece(pince.listPoste[2]);
      one_button_active = pince.listPoste[2].button.buttonActive;
    } else if (!pince.listPoste[0].button.buttonActive && !pince.listPoste[1].button.buttonActive && !pince.listPoste[2].button.buttonActive){ 
        console.setText("aucun bouton actif", "red"); 
    } else {
        print("Un bouton est déjà activé pour le moment !"); 
        console.setText("Un bouton est déjà activé pour le moment !", "red");
    }
}



