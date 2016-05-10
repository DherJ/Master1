
// Déclaration des objets utilisés dans le programme
PFont font;

int screenWidth, screenHeight;

Interface myInterface;
BlocInterface itf_controleEtage;
Console console;

Ascenseur ascenseur;
Etage rdc;
Etage etage1;
Etage etage2;

boolean one_button_active = false;
int num_button_active = 0;


void setup() {
  smooth();
  screenWidth = 800;
  screenHeight = 600;
  size(screenWidth, screenHeight); //Taille de la fenêtre

  font = loadFont("LucidaConsole-11.vlw");
  textFont(font);

  // Initialisation de l'interface et de la console
  myInterface = new Interface(0, 480, screenWidth, 500-screenHeight);
  console = new Console(0, 480, screenWidth, 30);
  console.setText("Les messages d'information seront affichés dans cette boite", "green");

  itf_controleEtage = new BlocInterface("Contrôle de l'ascenseur", myInterface.xInterface, console.y+console.hauteur, screenWidth, screenHeight-myInterface.yInterface);
  itf_controleEtage.setText("Contrôle de l'ascenseur");
  itf_controleEtage.setColorBG(color(170));
  itf_controleEtage.setColorText(color(0, 50, 200));

  myInterface.addBlocInterface(itf_controleEtage);
  myInterface.setConsole(console);

  rdc = new Etage(100, 400, 500, 20);
  rdc.attribuerNumEtage(0);
  rdc.addButton("RDC", 100, 550, 30, 20);
  rdc.button.setText("0");
  rdc.button.setColorBG("green");
  rdc.addCapteur(rdc.x+rdc.largeur-20, rdc.y, 20, rdc.hauteur);
  rdc.capteur.setValue(rdc.y, 3);

  etage1 = new Etage(100, 250, 500, 20);
  etage1.attribuerNumEtage(1);
  etage1.addButton("Etage 1", 140, 550, 30, 20);
  etage1.button.setText("1");
  etage1.button.setColorBG("green");
  etage1.addCapteur(etage1.x+etage1.largeur-20, etage1.y, 20, etage1.hauteur);
  etage1.capteur.setValue(etage1.y, 3);

  etage2 = new Etage(100, 100, 500, 20);
  etage2.attribuerNumEtage(2);
  etage2.addButton("Etage 2", 180, 550, 30, 20);
  etage2.button.setText("2");
  etage2.button.setColorBG("green");
  etage2.addCapteur(etage2.x+etage2.largeur-20, etage2.y, 20, etage2.hauteur);
  etage2.capteur.setValue(etage2.y, 3);  


  // Ascenseur(float _xPied, float _yPied, float _largeurPied, float _hauteurPied, float _hauteurCabine) 
  ascenseur = new Ascenseur(etage1.x+etage1.largeur, 300, 120, 10, 80);
  ascenseur.initPorte(color(120, 160, 140), color(120, 160, 140));

  ascenseur.definirNbEtage(3);

  ascenseur.attribuerEtage(rdc, 0);
  ascenseur.attribuerEtage(etage1, 1);
  ascenseur.attribuerEtage(etage2, 2);
  
  ascenseur.setDefaultEtage(rdc);

}

void draw() {
  frameRate(60);
  background(255);

  myInterface.display();

  ascenseur.display();

  ascenseur.listEtage[0].capteur.detect(ascenseur.yPied);
  ascenseur.listEtage[1].capteur.detect(ascenseur.yPied);
  ascenseur.listEtage[2].capteur.detect(ascenseur.yPied);
  
  if (ascenseur.listEtage[0].button.buttonActive) {
    ascenseur.deplacerVersEtage(ascenseur.listEtage[0]);
    one_button_active = ascenseur.listEtage[0].button.buttonActive;
  } 

  if (ascenseur.listEtage[1].button.buttonActive) {
    ascenseur.deplacerVersEtage(ascenseur.listEtage[1]);
    one_button_active = ascenseur.listEtage[1].button.buttonActive;
  } 
    
  if (ascenseur.listEtage[2].button.buttonActive) {
    ascenseur.deplacerVersEtage(ascenseur.listEtage[2]);
    one_button_active = ascenseur.listEtage[2].button.buttonActive;
  } 
}

void mouseClicked() { 

  if (!one_button_active) {
    clicOnButton(ascenseur.listEtage[0].button);
    clicOnButton(ascenseur.listEtage[1].button);
    clicOnButton(ascenseur.listEtage[2].button);
  } else { 
    print("Un bouton est déjà activé pour le moment !"); 
    console.setText("Un bouton est déjà activé pour le moment !", "red");
  }
}

void clicOnButton(Button button) {
  if ( (mouseX >= button.x) && (mouseX <= button.x+button.largeur) && 
    (mouseY >= button.y) && (mouseY <= button.y+button.hauteur)) {
    button.buttonPressed();
  }
}

