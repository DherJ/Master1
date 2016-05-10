
// Déclaration des objets utilisés dans le programme
//PImage test;
PFont font;

int screenWidth, screenHeight;

Interface myInterface;
BlocInterface itf_actionneurs;
BlocInterface itf_automatique;
BlocInterface itf_controleBoite;
Console console;

Perceuse perceuse;
Fraiseuse fraiseuse;

Boite boite;

Capteur a0;
Capteur a1;
Capteur b0;
Capteur b1;
Capteur c0;

Button OnOff;

Button AllerFraiseuse;
Button RetourFraiseuse;
Button AllerPerceuse;
Button RetourPerceuse;

Button Automatic;

Button newBoite;


boolean bool_marche;
String etatSys;


void setup() {
  smooth();       //Lissage des dessins
  screenWidth = 800;
  screenHeight = 600;
  size(screenWidth, screenHeight); //Taille de la fenêtre
  //test = loadImage("img/test2.jpg");
  font = loadFont("LucidaConsole-11.vlw");
  textFont(font);

  // Initialisation de l'interface et de la console
  myInterface = new Interface(0, 420, screenWidth, 29);
  console = new Console(0, 420, screenWidth, 29);
  console.setText("Les messages d'information seront affichés dans cette boite", "green");
  
  itf_actionneurs = new BlocInterface("Actionneurs", myInterface.xInterface, console.y+console.hauteur, 300, screenHeight-myInterface.yInterface);
  itf_actionneurs.setText("Actionneurs");
  itf_actionneurs.setColorBG(color(170));
  itf_actionneurs.setColorText(color(0, 50, 200));
  
  itf_automatique = new BlocInterface("Automatique", itf_actionneurs.xBloc+itf_actionneurs.largeurBloc, console.y+console.hauteur, 170, screenHeight-myInterface.yInterface);
  itf_automatique.setText("Automatique");
  itf_automatique.setColorBG(color(170));
  itf_automatique.setColorText(color(0, 50, 200));

  itf_controleBoite = new BlocInterface("Gestion de boite", itf_automatique.xBloc+itf_automatique.largeurBloc, console.y+console.hauteur, screenWidth-itf_actionneurs.largeurBloc-itf_automatique.largeurBloc, screenHeight-myInterface.yInterface);
  itf_controleBoite.setText("Gestion de boite");
  itf_controleBoite.setColorBG(color(170));
  itf_controleBoite.setColorText(color(0, 50, 200));

  myInterface.addBlocInterface(itf_actionneurs);
  myInterface.addBlocInterface(itf_automatique);
  myInterface.addBlocInterface(itf_controleBoite);
  myInterface.setConsole(console);


  // Initialisation de tous les objets du programme
  OnOff = new Button("Marche Arret");
  OnOff.setText("Marche");
  OnOff.setPosition(50, 60);
  OnOff.setSize(100, 20);
  OnOff.setColorBG("green");
  OnOff.originalColorButton = OnOff.colorButton;
  OnOff.setColorText("white");

  AllerFraiseuse = new Button("Fraiseuse Rf");
  AllerFraiseuse.setText("Rf");
  AllerFraiseuse.setPosition(50, 500);
  AllerFraiseuse.setSize(100, 20);
  AllerFraiseuse.setColorBG("green");
  AllerFraiseuse.originalColorButton = AllerFraiseuse.colorButton;
  AllerFraiseuse.setColorText("white");

  AllerPerceuse = new Button("Perceuse Rp");
  AllerPerceuse.setText("Rp");
  AllerPerceuse.setPosition(50, 530);
  AllerPerceuse.setSize(100, 20);
  AllerPerceuse.setColorBG("green");
  AllerPerceuse.originalColorButton = AllerPerceuse.colorButton;
  AllerPerceuse.setColorText("white");

  RetourFraiseuse = new Button("Fraiseuse Lf");
  RetourFraiseuse.setText("Lf");
  RetourFraiseuse.setPosition(160, 500);
  RetourFraiseuse.setSize(100, 20);
  RetourFraiseuse.setColorBG("green");
  RetourFraiseuse.originalColorButton = RetourFraiseuse.colorButton;
  RetourFraiseuse.setColorText("white");

  RetourPerceuse = new Button("Perceuse Lp");
  RetourPerceuse.setText("Lp");
  RetourPerceuse.setPosition(160, 530);
  RetourPerceuse.setSize(100, 20);
  RetourPerceuse.setColorBG("green");
  RetourPerceuse.originalColorButton = RetourPerceuse.colorButton;
  RetourPerceuse.setColorText("white");

  Automatic = new Button("Automatique");
  Automatic.setText("Automatique");
  Automatic.setPosition(330, 500);
  Automatic.setSize(100, 20);
  Automatic.setColorBG("blue");
  Automatic.originalColorButton = Automatic.colorButton;
  Automatic.setColorText("white");

  newBoite = new Button("Boite");
  newBoite.setText("Nouvelle boite");
  newBoite.setPosition(500, 500);
  newBoite.setSize(120, 20);
  newBoite.setColorBG("green");
  newBoite.originalColorButton =  newBoite.colorButton;
  newBoite.setColorText("white");

  //  Boite (float _xBoite, float _yBoite, float _largeurBoite, float _hauteurBoite, 
  //   color _couleurBoite, float _vitDeplBoite)
  boite = new Boite(300, 250, 200, 100, 
  color(51, 204, 255), 3);
  boite.initBoite();

  //  Perceuse (float _xBase, float _yBase, float _largeurBase, float _hauteurBase, 
  //   float _largeurForet, float _hauteurForet, color _couleurBase, color _couleurForet)
  perceuse = new Perceuse(680, 260, 40, 100, 
  8, 30, color(47, 79, 79), color(128, 128, 128));  
  perceuse.setDirection("gauche");
  // float _xMin, float _xMax
  perceuse.setX(perceuse.xBase, boite.xBoite+boite.largeurBoite-perceuse.hauteurForet+10);
  perceuse.attribuerBoite(boite);

  //  Fraiseuse (float _xBase, float _yBase, float _largeurBase, float _hauteurBase, 
  //   float _largeurFraise, float _hauteurFraise, color _couleurBase, color _couleurFraise)
  fraiseuse = new Fraiseuse(220, 125, 40, 100, 
  25, 40, color(47, 79, 79), color(128, 128, 128));
  fraiseuse.setX(fraiseuse.xBase, boite.xBoite+50);
  fraiseuse.attribuerBoite(boite);

  // Capteur(String _nameCapteur, float _xCapteur, float _yCapteur)
  a0 = new Capteur("a0", 700, 30);
  a0.setSize(5, 10);
  a0.setValue(perceuse.xMin, 2);

  a1 = new Capteur("a1", 700, 60);
  a1.setSize(5, 10);
  a1.setValue(perceuse.xMax, 2);

  b0 = new Capteur("b0", 700, 90);
  b0.setSize(5, 10);
  b0.setValue(fraiseuse.xMin, 2);

  b1 = new Capteur("b1", 700, 120);
  b1.setSize(5, 10);
  b1.setValue(fraiseuse.xMax, 2);

  c0 = new Capteur("c0", 700, 150);
  c0.setSize(5, 10);
  c0.setColor("green");

  perceuse.attribuerCapteurOrigine(a0);
  perceuse.attribuerCapteurMax(a1);

  fraiseuse.attribuerCapteurOrigine(b0);
  fraiseuse.attribuerCapteurMax(b1);

  bool_marche = false;
}


void draw() {
  frameRate(60);
  background(255);
  //background(test);
  
  myInterface.display();
  //console.display();
  //drawInterface(450);
  
  boite.display(); 

  perceuse.display();
  fraiseuse.display();

  OnOff.display();
  AllerFraiseuse.display();
  RetourFraiseuse.display();
  AllerPerceuse.display();
  RetourPerceuse.display();
  Automatic.display();
  newBoite.display();

  a0.display();
  a1.display();
  b0.display();
  b1.display();
  c0.display();
  
  etatSysteme(bool_marche); 

  checkButtons();

  if (bool_marche) {
    perceuse.moove();
    fraiseuse.moove();

    perceuse.PercageBoite();
    perceuse.capt_origin.detect(perceuse.xBase);
    perceuse.capt_max.detect(perceuse.xForet-perceuse.hauteurForet);

    fraiseuse.FraisageBoite();
    fraiseuse.capt_origin.detect(fraiseuse.xBase);
    fraiseuse.capt_max.detect(fraiseuse.xFraise+fraiseuse.largeurFraise);
  }
}

void etatSysteme(boolean bool_marche) {
  if (bool_marche) {
    etatSys = "Marche";
  } else etatSys = "Arret";
  
  fill(0, 0, 0);
  text("Etat du systeme : " +etatSys, 50, 50);
}


void drawInterface(int y) {
  
  stroke(0);
  line(0, y-1, screenWidth, y-1);
  
  // Partie actionneurs
  noStroke();
  fill(170);
  rect(0, y, 300, screenHeight-y);
  
  stroke(0);
  line(300, y, 300, screenHeight);
  
  fill(0, 50, 200);
  text("Actionneurs", 50, 480);
  
  // Partie automatique
  noStroke();
  fill(170);
  rect(301, y, 170, screenHeight-y);
  stroke(0);
  line(471, y, 471, screenHeight);
  
  fill(0, 50, 200);
  text("Mode automatique", 330, 480);
  
  // Partie nouvelle boite
  noStroke();
  fill(170);
  rect(472, y, screenWidth-472, screenHeight-y);
    
  fill(0, 50, 200);
  text("Générer une nouvelle boite", 500, 480);
}

void checkButtons() {

    if (OnOff.text.equals("Marche") && OnOff.test()) {
      bool_marche = true;
      OnOff.setText("Arret");
      OnOff.setColorBG("red");
    } else if (OnOff.text.equals("Arret") && OnOff.test()) {
        bool_marche = false;
        OnOff.setText("Marche");
        OnOff.setColorBG("green");
    }

    if (bool_marche) {
      if (AllerFraiseuse.buttonActive) {
        RetourFraiseuse.buttonActive = false;
        fraiseuse.bool_aller_fraiseuse = true;
        fraiseuse.bool_retour_fraiseuse = false;
        fraiseuse.vitesseFraiseuse(2.5);
      } else if (RetourFraiseuse.buttonActive) {
        AllerFraiseuse.buttonActive = false;
        fraiseuse.bool_retour_fraiseuse = true;
        fraiseuse.bool_aller_fraiseuse = false;
        fraiseuse.vitesseFraiseuse(1);
      }

      if (AllerPerceuse.buttonActive) {
        RetourPerceuse.buttonActive = false;
        perceuse.bool_aller_perceuse = true;
        perceuse.bool_retour_perceuse = false;
        perceuse.vitessePercage(2.5);
      } else if (RetourPerceuse.buttonActive) {
        AllerPerceuse.buttonActive = false;
        perceuse.bool_aller_perceuse = false;
        perceuse.bool_retour_perceuse = true;
        perceuse.vitessePercage(1);
      }

      if (Automatic.buttonActive) {
        perceuse.bool_automatic = true; 
        fraiseuse.bool_automatic = true;
        if (perceuse.boite.trouPerceuse && fraiseuse.boite.trouFraiseuse) {
          
          console.setText("Les trous ont déjà été percé pour cette boite !", "yellow");
          print("Les trous ont déjà été percé pour cette boite !\n");
        }
      }

      if (newBoite.buttonActive) {
        console.setText("Veuillez arrêter le système avant de placer une nouvelle boite", "red");
        print("Veuillez arrêter le système avant de placer une nouvelle boite\n");
        newBoite.buttonActive = false;
      }
    }

    if (!bool_marche) {
      if (newBoite.buttonActive) {

        boite.initBoite();
        console.setText("Une nouvelle boite est prête", "green");
        print("Une nouvelle boite est prête \n");
        newBoite.buttonActive = false;
      }
    }
}


