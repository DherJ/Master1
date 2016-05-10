
PFont font;

Verin verin_monostable;
Verin verin_bistable;

Button rentrerTigeV1;  // commande bistable
Button sortirTigeV1;   // commande bistable

Button sortirTigeV2BP; // commande monostable

Interface myInterface;
BlocInterface actionneursBloc;

boolean sortirV1 = false;
boolean sortirV2 = false;

void setup() {
  smooth();       // Lissage des dessins
  size(600, 500); // Taille de la fenêtre
  font = loadFont("ArialMT-9.vlw");
  textFont(font);
  initSystem();
  initInterface(200, 350, 230, 400);
}

void initSystem() {
  verin_bistable = new Verin(200, 100, 80, 30, 30, 100, color(#352D56), color(#7D7D7D), "bistable", "droite"); 
  verin_monostable = new Verin(verin_bistable.xBase, verin_bistable.yBase + verin_bistable.hauteurBase + 30, 
        80, 30, 30, 100, color(#352D56), color(#7D7D7D), "monostable", "droite");
}

void initInterface(int x, int y, int largeur, int hauteur) {
  myInterface = new Interface(x, y, largeur, hauteur);
  actionneursBloc = new BlocInterface("actionneur", myInterface.xInterface, myInterface.yInterface, myInterface.largeurInterface, myInterface.hauteurInterface);
  actionneursBloc.setText("actionneurs");
  actionneursBloc.setColorBG(color(170));
  actionneursBloc.setColorText(color(0, 50, 200));
  sortirTigeV1 = new Button("Sortir V1"); 
  sortirTigeV1.setColorBG("orange");
  sortirTigeV1.originalColorButton = color(#FF7E27);
  sortirTigeV1.setColorText("black");
  sortirTigeV1.setTailleText(8);
  sortirTigeV1.setText("Sortir V1");
  rentrerTigeV1 = new Button("Rentrer V1"); 
  rentrerTigeV1.setColorBG("orange");
  rentrerTigeV1.originalColorButton = color(#FF7E27);
  rentrerTigeV1.setColorText("black");
  rentrerTigeV1.setTailleText(8);
  rentrerTigeV1.setText("Rentrer V1");
  sortirTigeV2BP = new Button("Sortir V2"); 
  sortirTigeV2BP.setColorBG("orange");
  sortirTigeV2BP.originalColorButton = color(#FF7E27);
  sortirTigeV2BP.setColorText("black");
  sortirTigeV2BP.setTailleText(8);
  sortirTigeV2BP.setText("Sortir V2");
  CommandeActionneurs commandeActionneurs = new CommandeActionneurs(actionneursBloc.xBloc + 20, actionneursBloc.yBloc + 50);
  CommandeActionneur v1CMD = new CommandeActionneur("v1", color(0, 0, 0), 10);
  v1CMD.setButtonArreter(rentrerTigeV1);
  v1CMD.setButtonActiver(sortirTigeV1);
  CommandeActionneur v2CMD = new CommandeActionneur("v2", color(0, 0, 0), 10);
  v2CMD.setButtonActiver(sortirTigeV2BP);
  commandeActionneurs.addCommandeActionneur(v1CMD);
  commandeActionneurs.addCommandeActionneur(v2CMD);
  commandeActionneurs.setText("actionneurs");
  actionneursBloc.setCommandeActionneurs(commandeActionneurs);
  myInterface.addBlocInterface(actionneursBloc);
}

void draw() {
  background(255);
  fill(0, 0, 0);
  verin_monostable.display();
  fill(0, 0, 0);
  text("v1 monostable", verin_monostable.xTige - 150, verin_monostable.yBase + (verin_monostable.hauteurBase / 2));
  verin_bistable.display();
  fill(0, 0, 0);
  text("v2 bistable", verin_bistable.xTige - 150, verin_bistable.yBase + (verin_bistable.hauteurBase / 2));
  myInterface.display();
  if(sortirV1) {
    verin_bistable.sortirTige();
  } else {
    verin_bistable.rentrerTige();
    System.out.println("largeur tige rentrée init " + verin_bistable.largeurTigeInit);
    System.out.println("largeur tige rentrée " + verin_bistable.largeurTige);
  }
  if(sortirV2) {
    verin_monostable.sortirTige();
  } else {
    verin_monostable.rentrerTige();
  }
}

void mousePressed() {
  if(sortirTigeV2BP.overButton()) {
    sortirV2 = true;
  }
}

void mouseClicked() {
  if(rentrerTigeV1.overButton()) {
    sortirV1 = false;
    verin_bistable.bool_rent = true;
    verin_bistable.bool_sort = false;
  }
  if(sortirTigeV1.overButton()) {
    sortirV1 = true;
    verin_bistable.bool_rent = false;
    verin_bistable.bool_sort = true;
  }
}

void mouseReleased() {
  if(sortirTigeV2BP.overButton()) {
    sortirV2 = false;
    verin_monostable.bool_rent = true;
  }
}

