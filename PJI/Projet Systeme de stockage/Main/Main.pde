
/* 
  Système de stockage
  
  Le paramètre deplMaxTige dans le constructeur v3 permet de dire jusqu'où (coordonnée en X) 
  la tige va sortir
*/  

PFont font; // police d'écriture pour les textes

int screenWidth, screenHeight;

String modeFonctionnement;

/* Les éléments du système */
Verin v3;
Verin v21;
Verin v22;
Verin v1;
Plateau plateau;
Piece piece;
Piece piece2;
Tapis niveau0;
Tapis niveau1;
Tapis niveau2;
Capteur c0;
Capteur c1;
Capteur c2;
Capteur c20;
Capteur c21;
Capteur c22;

/* interrupteurs */
Button marche;
Button arret;
Button automatic;
Button manuel;
Button cas1Auto;
Button cas2Auto;
Button cas3Auto;
Button cas4Auto;
Button rentrerTigeV1;
Button sortirTigeV1;
Button rentrerTigeV21;
Button sortirTigeV21;
Button rentrerTigeV22;
Button sortirTigeV22;
Button rentrerTigeV3;
Button sortirTigeV3;
Button newPiece;
/* boutons poussoirs */
Button sortirTigeV1P;
Button sortirTigeV21P;
Button sortirTigeV22P;
Button sortirTigeV3P;

/* Les éléments de l'interface de contrôle */
Interface myInterface;
BlocInterface actionneursBloc;
BlocInterface runBloc;
BlocInterface modeBloc;
BlocInterface controlePieceBloc;
Console console;
CommandeActionneurs commandeActionneurs;
CommandeActionneurs commandeActionneursPoussoir;
CommandeActionneur v1CMD;
CommandeActionneur v21CMD;
CommandeActionneur v22CMD;
CommandeActionneur v3CMD;
CommandeActionneur v1CMDP;
CommandeActionneur v21CMDP;
CommandeActionneur v22CMDP;
CommandeActionneur v3CMDP;

CapteursMonitor capteursMonitor;
Fonctionnement fonctionnement;
Slider sliderNiveau1;
Slider sliderNiveau2;

boolean bool_marche = false;

void setup() {
  smooth();       // Lissage des dessins
  size(800, 700); // Taille de la fenêtre
  font = loadFont("ArialMT-9.vlw");
  textFont(font);
  screenWidth = 800;
  screenHeight = 700;
  initSystem();
}


void initSystem() {
  
  // Tapis (float x, float y, float largeur, float hauteur, String directionFonctionnement)
  niveau0 = new Tapis(300, 380, 300, 30, "gauche");
  niveau1 = new Tapis(300, 250, 300, 30, "droite");
  niveau2 = new Tapis(300, 100, 300, 30, "droite");
  
  // Plateau (float xPlateau, float yPlateau, float largeurPlateau, float hauteurPlateau, Verin verin)
  plateau = new Plateau(niveau0.xTapis - 210, niveau0.yTapis, 200, 20);
  
  // Verin (float _xBase, float _yBase, float _largeurBase, float _hauteurBase, float _largeurTige, 
     //float _deplMaxTige, color _couleurBase, color _couleurTige, String type, String direction)
       // type = monostable ou bistable, direction = haut, bas, gauche, droite
  v3  = new Verin(plateau.xPlateau - 60, plateau.yPlateau - 30, 80, 30, 40, 250, color(#352D56), color(#7D7D7D), "monostable", "droite");
  v1  = new Verin(niveau0.xTapis + niveau0.largeurTapis, niveau0.yTapis - 30, 80, 30, 40, 245, color(#352D56), color(#7D7D7D), "monostable", "gauche");
  v22 = new Verin(plateau.xPlateau + (plateau.largeurPlateau / 2) - 15, plateau.yPlateau + plateau.hauteurPlateau + 110, 
        80, 30, 30, 178, color(#352D56), color(#7D7D7D), "monostable", "haut");
  v22.touche = true;
  v21 = new Verin(v22.xBase, v22.yBase + v22.largeurTige + v22.largeurBase, 
        80, 30, 30, 158, color(#352D56), color(#7D7D7D), "monostable", "haut");
  
  // Capteur (float xCapteur, float yCapteur, float largeurCapteur, float hauteurCapteur)
  c0 = new Capteur(niveau0.xTapis, niveau0.yTapis, 20, niveau0.hauteurTapis);
  c0.setName("c0");
  c1 = new Capteur(niveau1.xTapis, niveau1.yTapis, 20, niveau1.hauteurTapis);
  c1.setName("c1");
  c2 = new Capteur(niveau2.xTapis, niveau2.yTapis, 20, niveau2.hauteurTapis);
  c2.setName("c2");
  c20 = new Capteur(plateau.xPlateau, plateau.yPlateau, 20, plateau.hauteurPlateau);
  c20.setName("c20");
  c21 = new Capteur(v3.xBase, niveau1.yTapis, 20, niveau1.hauteurTapis);
  c21.setName("c21");
  c22 = new Capteur(v3.xBase, niveau2.yTapis, 20, niveau2.hauteurTapis);
  c22.setName("c22");
  
  c20.setPlateau(plateau);
  c21.setPlateau(plateau);
  c22.setPlateau(plateau);
  
  niveau0.setVerinV1(v1);
  niveau0.setCapteurC0(c0);
  niveau0.setNiveauTapis(0);  // pour indiquer que c'est le niveau 0 
  niveau1.setCapteurC1(c1);
  niveau1.setVitesseTapis((float)1);
  niveau2.setCapteurC2(c2);
  niveau2.setVitesseTapis((float)3);
  
  v22.setPlateau(plateau);
  
  plateau.setVerinV22(v22);
  plateau.setVerinV21(v21);
  plateau.setVerinV3(v3);
  plateau.setVerinV1(v1);
  plateau.setCapteurC20(c20);
  plateau.setCapteurC21(c21);
  plateau.setCapteurC22(c22);
  plateau.setCapteurC0(c0);
  plateau.setCapteurC1(c1);
  plateau.setCapteurC2(c2);
  plateau.setNiveau0(niveau0);
  plateau.setNiveau1(niveau1);
  plateau.setNiveau2(niveau2);
  
  initInterface(300, 420, screenWidth - 300, screenHeight - 420);
  
  initFonctionnement("manuel");
}

void initFonctionnement(String mode) {
  modeFonctionnement = mode;
  fonctionnement = new Fonctionnement(modeFonctionnement);
  fonctionnement.setModeFonctionnement(modeFonctionnement);
  fonctionnement.setNBPiecesMax(5);
  fonctionnement.setButtonMarche(marche);
  fonctionnement.setButtonArret(arret);
  fonctionnement.setButtonNewPiece(newPiece);
  fonctionnement.setButtonRentrerV1(sortirTigeV1);
  fonctionnement.setButtonSortirV1(rentrerTigeV1);
  fonctionnement.setButtonRentrerV21(rentrerTigeV21);
  fonctionnement.setButtonSortirV21(sortirTigeV21);
  fonctionnement.setButtonRentrerV22(rentrerTigeV22);
  fonctionnement.setButtonSortirV22(sortirTigeV22);
  fonctionnement.setButtonRentrerV3(rentrerTigeV3);
  fonctionnement.setButtonSortirV3(sortirTigeV3);
  fonctionnement.setButtonSortirV1P(sortirTigeV1P);
  fonctionnement.setButtonSortirV21P(sortirTigeV21P);
  fonctionnement.setButtonSortirV22P(sortirTigeV22P);
  fonctionnement.setButtonSortirV3P(sortirTigeV3P);
  fonctionnement.setPlateau(plateau);
  fonctionnement.setVerinV22(v22);
  fonctionnement.setVerinV21(v21);
  fonctionnement.setVerinV3(v3);
  fonctionnement.setVerinV1(v1);
  fonctionnement.setCapteurC20(c20);
  fonctionnement.setCapteurC21(c21);
  fonctionnement.setCapteurC22(c22);
  fonctionnement.setCapteurC0(c0);
  fonctionnement.setCapteurC1(c1);
  fonctionnement.setCapteurC2(c2);
  fonctionnement.setNiveau0(niveau0);
  fonctionnement.setNiveau1(niveau1);
  fonctionnement.setNiveau2(niveau2);
  fonctionnement.setTimeToWait(5000); // attente de 3 secondes entre l'apparition de 2 pièces le temps donné en paramètre est en millisecondes
  fonctionnement.setConsole(console);
}

void initInterface(int x, int y, int largeur, int hauteur) {
  /* Initialisation de l'interface de contrôle */
  myInterface = new Interface(x, y, largeur, hauteur);
  console = new Console(myInterface.xInterface, myInterface.yInterface, screenWidth - myInterface.xInterface, 29);
  console.setText("Système de stockage", "green");
  
  actionneursBloc = new BlocInterface("actionneurs", myInterface.xInterface, console.y + console.hauteur, (screenWidth - myInterface.xInterface) / 3, screenHeight - (console.y + console.hauteur));
  actionneursBloc.setText("actionneurs");
  actionneursBloc.setColorBG(color(170));
  actionneursBloc.setColorText(color(0, 50, 200));
  runBloc = new BlocInterface("onOff", actionneursBloc.xBloc + actionneursBloc.largeurBloc, console.y + console.hauteur, (screenWidth - myInterface.xInterface) / 3, (screenHeight - (console.y + console.hauteur)) / 2);
  runBloc.setText("on/off");
  runBloc.setColorBG(color(170));
  runBloc.setColorText(color(0, 50, 200));
  modeBloc = new BlocInterface("fonctionnement", runBloc.xBloc, runBloc.yBloc + runBloc.hauteurBloc + 10, (screenWidth - myInterface.xInterface) / 3, (screenHeight - (console.y + console.hauteur)) / 2);
  modeBloc.setText("mode fonctionnement");
  modeBloc.setColorBG(color(170));
  modeBloc.setColorText(color(0, 50, 200));
  controlePieceBloc = new BlocInterface("controlePiece", runBloc.xBloc + runBloc.largeurBloc, console.y + console.hauteur, (screenWidth - myInterface.xInterface) / 3, screenHeight - (console.y + console.hauteur));
  controlePieceBloc.setText("Générer une nouvelle Piece");
  controlePieceBloc.setColorBG(color(170));
  controlePieceBloc.setColorText(color(0, 50, 200));
  myInterface.addBlocInterface(actionneursBloc);
  myInterface.addBlocInterface(runBloc);
  myInterface.addBlocInterface(modeBloc);
  myInterface.addBlocInterface(controlePieceBloc);
  myInterface.setConsole(console);
  
  //Button (String name, float _xBouton, float _yBouton, float _largeurBouton, float _hauteurBouton)
  
  marche = new Button("Marche", runBloc.xBloc + 40, runBloc.yBloc + 30, 60, 20); 
  marche.setTypeButton("interrupeteur");
  marche.setColorBG("green");
  marche.originalColorButton = marche.colorButton;
  marche.setText("marche");
  marche.setColorText("white");
  marche.setTailleText(10);
  arret = new Button("Arret", runBloc.xBloc + 40, runBloc.yBloc + 70, 60, 20); 
  arret.setTypeButton("interrupteur");
  arret.setColorBG("red");
  arret.originalColorButton = arret.colorButton;
  arret.setText("arret");
  arret.setColorText("white");
  arret.setTailleText(10);
   
  newPiece = new Button("Nouvelle Piece", controlePieceBloc.xBloc + 40, controlePieceBloc.yBloc + 30, 90, 20);
  newPiece.setTypeButton("interrupteur");
  newPiece.setColorBG("blue");
  newPiece.originalColorButton = arret.colorButton;
  newPiece.setText("nouvelle Piece");
  newPiece.setColorText("white");
  newPiece.setTailleText(10);
  
  automatic = new Button("automatic", modeBloc.xBloc + 40, modeBloc.yBloc + 30, 60, 20);
  automatic.setTypeButton("interrupteur");
  automatic.setColor(color(#837F7F));
  automatic.setColorFilled(color(#959393));
  automatic.originalColorButton = arret.colorButton;
  automatic.setText("automatic");
  automatic.setColorText("white");
  automatic.setTailleText(10);
  
  cas1Auto = new Button("cas1", modeBloc.xBloc + 20, modeBloc.yBloc + 25, 40, 20);
  cas1Auto.setTypeButton("interrupteur");
  cas1Auto.setColor(color(#837F7F));
  cas1Auto.setColorFilled(color(#959393));
  cas1Auto.originalColorButton = arret.colorButton;
  cas1Auto.setText("cas1");
  cas1Auto.setColorText("white");
  cas1Auto.setTailleText(10);
  cas2Auto = new Button("cas2", modeBloc.xBloc + 80, modeBloc.yBloc + 25, 40, 20);
  cas2Auto.setTypeButton("interrupteur");
  cas2Auto.setColor(color(#837F7F));
  cas2Auto.setColorFilled(color(#959393));
  cas2Auto.originalColorButton = arret.colorButton;
  cas2Auto.setText("cas2");
  cas2Auto.setColorText("white");
  cas2Auto.setTailleText(10);
  cas3Auto = new Button("cas3", modeBloc.xBloc + 20, modeBloc.yBloc + 50, 40, 20);
  cas3Auto.setTypeButton("interrupteur");
  cas3Auto.setColor(color(#837F7F));
  cas3Auto.setColorFilled(color(#959393));
  cas3Auto.originalColorButton = arret.colorButton;
  cas3Auto.setText("cas3");
  cas3Auto.setColorText("white");
  cas3Auto.setTailleText(10);
  cas4Auto = new Button("cas4", modeBloc.xBloc + 80, modeBloc.yBloc + 50, 40, 20);
  cas4Auto.setTypeButton("interrupteur");
  cas4Auto.setColor(color(#837F7F));
  cas4Auto.setColorFilled(color(#959393));
  cas4Auto.originalColorButton = arret.colorButton;
  cas4Auto.setText("cas4");
  cas4Auto.setColorText("white");
  cas4Auto.setTailleText(10);
  
  manuel = new Button("manuel", modeBloc.xBloc + 20, modeBloc.yBloc + 75, 60, 20);
  manuel.setTypeButton("interrupteur");
  manuel.setColor(color(#837F7F));
  manuel.setColorFilled(color(#959393));
  manuel.originalColorButton = arret.colorButton;
  manuel.setText("manuel");
  manuel.setColorText("white");
  manuel.setTailleText(10);
  
  rentrerTigeV1 = new Button("Sortir V1", 300, 600, 50, 20); 
  rentrerTigeV1.setColorBG("orange");
  rentrerTigeV1.originalColorButton = arret.colorButton;
  rentrerTigeV1.setColorText("black");
  rentrerTigeV1.setTailleText(8);
  rentrerTigeV1.setText("Sortir V1");
  
  sortirTigeV1 = new Button("Rentrer V1", 200, 600, 50, 20); 
  sortirTigeV1.setColorBG("orange");
  sortirTigeV1.originalColorButton = arret.colorButton;
  sortirTigeV1.setColorText("black");
  sortirTigeV1.setTailleText(8);
  sortirTigeV1.setText("Rentrer V1");
  
  rentrerTigeV21 = new Button("Rentrer V21", 300, 600, 50, 20); 
  rentrerTigeV21.setColorBG("orange");
  rentrerTigeV21.originalColorButton = arret.colorButton;
  rentrerTigeV21.setColorText("black");
  rentrerTigeV21.setTailleText(8);
  rentrerTigeV21.setText("Rentrer V21");
  
  sortirTigeV21 = new Button("Sortir V21", 200, 600, 50, 20); 
  sortirTigeV21.setColorBG("orange");
  sortirTigeV21.originalColorButton = arret.colorButton;
  sortirTigeV21.setColorText("black");
  sortirTigeV21.setTailleText(8);
  sortirTigeV21.setText("Sortir V21");
  
  rentrerTigeV22 = new Button("Rentrer V22", 300, 600, 50, 20); 
  rentrerTigeV22.setColorBG("orange");
  rentrerTigeV22.originalColorButton = arret.colorButton;
  rentrerTigeV22.setColorText("black");
  rentrerTigeV22.setTailleText(8);
  rentrerTigeV22.setText("Rentrer V22");
  
  sortirTigeV22 = new Button("Sortir V22", 200, 600, 50, 20); 
  sortirTigeV22.setColorBG("orange");
  sortirTigeV22.originalColorButton = arret.colorButton;
  sortirTigeV22.setColorText("black");
  sortirTigeV22.setTailleText(8);
  sortirTigeV22.setText("Sortir V22");
  
  rentrerTigeV3 = new Button("Rentrer V3", 300, 600, 50, 20); 
  rentrerTigeV3.setColorBG("orange");
  rentrerTigeV3.originalColorButton = arret.colorButton;
  rentrerTigeV3.setColorText("black");
  rentrerTigeV3.setTailleText(8);
  rentrerTigeV3.setText("Rentrer V3");
  
  sortirTigeV3 = new Button("Sortir V3", 200, 600, 50, 20); 
  sortirTigeV3.setColorBG("orange");
  sortirTigeV3.originalColorButton = arret.colorButton;
  sortirTigeV3.setColorText("black");
  sortirTigeV3.setTailleText(8);
  sortirTigeV3.setText("Sortir V3");
  
  /* boutons poussoirs pour actionner les vérins */
  sortirTigeV1P = new Button("Sortir V1", 300, 600, 50, 20); 
  sortirTigeV1P.setColorBG("orange");
  sortirTigeV1P.originalColorButton = arret.colorButton;
  sortirTigeV1P.setColorText("black");
  sortirTigeV1P.setTailleText(8);
  sortirTigeV1P.setText("Sortir V1");
  
  sortirTigeV21P = new Button("Sortir V21", 370, 600, 50, 20); 
  sortirTigeV21P.setColorBG("orange");
  sortirTigeV21P.originalColorButton = arret.colorButton;
  sortirTigeV21P.setColorText("black");
  sortirTigeV21P.setTailleText(8);
  sortirTigeV21P.setText("Sortir V21");
  
  sortirTigeV22P = new Button("Sortir V22", 300, 630, 50, 20); 
  sortirTigeV22P.setColorBG("orange");
  sortirTigeV22P.originalColorButton = arret.colorButton;
  sortirTigeV22P.setColorText("black");
  sortirTigeV22P.setTailleText(8);
  sortirTigeV22P.setText("Sortir V22");
  
  sortirTigeV3P = new Button("Sortir V3", 370, 630, 50, 20); 
  sortirTigeV3P.setColorBG("orange");
  sortirTigeV3P.originalColorButton = arret.colorButton;
  sortirTigeV3P.setColorText("black");
  sortirTigeV3P.setTailleText(8);
  sortirTigeV3P.setText("Sortir V3");
  
  commandeActionneurs = new CommandeActionneurs(actionneursBloc.xBloc + 10, actionneursBloc.yBloc + 50);
  v1CMD = new CommandeActionneur("v1", color(0, 0, 0), 10);
  v1CMD.setButtonArreter(sortirTigeV1);
  v1CMD.setButtonActiver(rentrerTigeV1);
  
  v21CMD = new CommandeActionneur("v21", color(0, 0, 0), 10);
  v21CMD.setButtonArreter(rentrerTigeV21);
  v21CMD.setButtonActiver(sortirTigeV21);
  
  v22CMD = new CommandeActionneur("v22", color(0, 0, 0), 10);
  v22CMD.setButtonArreter(rentrerTigeV22);
  v22CMD.setButtonActiver(sortirTigeV22);
  
  v3CMD = new CommandeActionneur("v3", color(0, 0, 0), 10);
  v3CMD.setButtonArreter(rentrerTigeV3);
  v3CMD.setButtonActiver(sortirTigeV3);
  
  commandeActionneurs.addCommandeActionneur(v1CMD);
  commandeActionneurs.addCommandeActionneur(v21CMD);
  commandeActionneurs.addCommandeActionneur(v22CMD);
  commandeActionneurs.addCommandeActionneur(v3CMD);
  commandeActionneurs.setText("Interrupteurs");
  commandeActionneurs.setTextSize(11);
  commandeActionneurs.setColorText(color(#161617));
  
  commandeActionneursPoussoir = new CommandeActionneurs(commandeActionneurs.x, commandeActionneurs.y + 150);
  v1CMDP = new CommandeActionneur("v1", color(0, 0, 0), 10);
  v1CMDP.setButtonActiver(sortirTigeV1P);
  v21CMDP = new CommandeActionneur("v21", color(0, 0, 0), 10);
  v21CMDP.setButtonActiver(sortirTigeV21P);
  
  v22CMDP = new CommandeActionneur("v22", color(0, 0, 0), 10);
  v22CMDP.setButtonActiver(sortirTigeV22P);
  
  v3CMDP = new CommandeActionneur("v3", color(0, 0, 0), 10);
  v3CMDP.setButtonActiver(sortirTigeV3P);
  
  commandeActionneursPoussoir.addCommandeActionneur(v1CMDP);
  commandeActionneursPoussoir.addCommandeActionneur(v21CMDP);
  commandeActionneursPoussoir.addCommandeActionneur(v22CMDP);
  commandeActionneursPoussoir.addCommandeActionneur(v3CMDP);
  
  commandeActionneursPoussoir.setText("Bouton Poussoir");
  commandeActionneursPoussoir.setTextSize(11);
  commandeActionneursPoussoir.setColorText(color(#161617));
  
  runBloc.addButton(marche);
  runBloc.addButton(arret);
  //modeBloc.addButton(automatic);
  modeBloc.addButton(cas1Auto);
  modeBloc.addButton(cas2Auto);
  modeBloc.addButton(cas3Auto);
  modeBloc.addButton(cas4Auto);
  modeBloc.addButton(manuel);
  modeBloc.setPositionBoutonFixe(true);
  controlePieceBloc.addButton(newPiece);
  actionneursBloc.setCommandeActionneurs(commandeActionneurs);
  actionneursBloc.setCommandeActionneurs(commandeActionneursPoussoir);
  
  capteursMonitor = new CapteursMonitor(controlePieceBloc.xBloc + 5, controlePieceBloc.yBloc + 90);
  capteursMonitor.addCapteur(c0);
  capteursMonitor.addCapteur(c1);
  capteursMonitor.addCapteur(c2);
  capteursMonitor.addCapteur(c20);
  capteursMonitor.addCapteur(c21);
  capteursMonitor.addCapteur(c22);
  
  sliderNiveau1 = new Slider("niveau 1", 10, 620, 100, 16, 10);
  sliderNiveau1.setCursorColor(color(#606C71));
  sliderNiveau1.setSliderBGColor(color(#2793BF));
  sliderNiveau1.setMinValue(0);
  sliderNiveau1.setMaxValue(5);
  sliderNiveau2 = new Slider("niveau 2", sliderNiveau1.xSlider, sliderNiveau1.ySlider + sliderNiveau1.hauteur + 30, 100, 16, 10);
  sliderNiveau2.setCursorColor(color(#606C71));
  sliderNiveau2.setSliderBGColor(color(#2793BF));
  sliderNiveau2.setMinValue(0);
  sliderNiveau2.setMaxValue(5);
}

void draw() {
  background(255);
  fill(0, 0, 0);
  afficherVariables();
  sliderNiveau1.display();
  sliderNiveau2.display();
  fonctionnement.getNiveau1().setVitesseTapis(sliderNiveau1.getValue());
  fonctionnement.getNiveau2().setVitesseTapis(sliderNiveau2.getValue());
  fill(204,0,51);
  v3.display();
  v21.display();
  v22.display();
  v1.display();
  plateau.display();
  niveau0.display();
  niveau1.display();
  niveau2.display();
  c0.display();
  c1.display();
  c2.display();
  c20.display();
  c21.display();
  c22.display();
  myInterface.display();
  capteursMonitor.display();
  textFont(font, 16);
  if(marche.buttonClicked()) {
    arret.buttonActive = false;
    bool_marche = true;
  }
  if(arret.buttonClicked()) {
    bool_marche = false;
    marche.buttonActive = false;
  }
  if(bool_marche == true) {
      fonctionnement.run();
  }
  fill(0,0,0);
  textSize(9);
  text("mode de fonctionnement : " + fonctionnement.getModeFonctionnement(), modeBloc.xBloc + 10, modeBloc.yBloc + 110);
  if(bool_marche) {
    text("Etat : marche", runBloc.xBloc + 55, runBloc.yBloc + 110);
  } else {
    text("Etat : arrêt", runBloc.xBloc + 55, runBloc.yBloc + 110);
  }
  noFill();
}

void afficherVariables() {
  textSize(10);
  text("c22", c22.xCapteur, c22.yCapteur + c22.hauteurCapteur + 9);
  text("c21", c21.xCapteur, c21.yCapteur + c21.hauteurCapteur + 9);
  text("c20", c20.xCapteur, c20.yCapteur + c20.hauteurCapteur + 9);
  text("c2", c2.xCapteur, c2.yCapteur + c2.hauteurCapteur + 9);
  text("c1", c1.xCapteur, c1.yCapteur + c1.hauteurCapteur + 9);
  text("c0", c0.xCapteur, c0.yCapteur + c0.hauteurCapteur + 9);
  text("v1", v1.xBase + 9, v1.yBase + (v1.hauteurBase / 2));
  text("v21", v21.xBase - 20, v21.yBase - (v21.largeurBase / 2));
  text("v22", v22.xBase - 20, v22.yBase - (v22.largeurBase / 2));
  text("v3", v3.xBase - 20, v3.yBase + (v3.hauteurBase / 2));
  text("niveau0 - arrivée des pièces", niveau0.xTapis + niveau0.largeurTapis + 30, niveau0.yTapis + (niveau0.hauteurTapis / 2));
  text("niveau1", niveau1.xTapis + niveau1.largeurTapis + 30, niveau1.yTapis + (niveau1.hauteurTapis / 2));
  text("niveau2", niveau2.xTapis + niveau2.largeurTapis + 30, niveau2.yTapis + (niveau2.hauteurTapis / 2));
  text("automatique", modeBloc.xBloc + 20, modeBloc.yBloc + 20);
}

void mouseClicked() { 
  fonctionnement.mouseClicked(mouseButton);
}

void mousePressed() {
  fonctionnement.mousePressed(mouseButton);
}

void mouseReleased() {
   fonctionnement.mouseReleased(mouseButton);
}
