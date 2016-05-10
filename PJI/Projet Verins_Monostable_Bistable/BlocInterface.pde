
class BlocInterface {
  
  int xBloc;
  int yBloc;
  int largeurBloc;
  int hauteurBloc;
  
  String name;
  String text;
  color couleurBG;
  color couleurText;
  
  ArrayList<Button> boutons;
  
  ArrayList<CommandeActionneurs> cmd;
  
  int espaceY = 10;
  int xButtonDefault;
  
  BlocInterface(String _name, int x, int y, int largeur, int hauteur) {
    this.name = _name;
    this.xBloc = x;
    this.yBloc = y;
    this.largeurBloc = largeur;
    this.hauteurBloc = hauteur;
    boutons = new ArrayList<Button>();
    cmd = new ArrayList<CommandeActionneurs>();
    xButtonDefault = (this.xBloc + (this.largeurBloc / 2)) - 35;
  }
  
  String getName() {
    return this.name;
  }
  
  public void addButton(Button bouton) {
    this.boutons.add(bouton);
  }
  
  void setText(String _text) {
    this.text = _text;
  }
  
  void setColorBG(color _couleur) {
    this.couleurBG = _couleur;
  }
  
  void setColorText(color _couleur) {
    this.couleurText = _couleur;
  }
  
  void setCommandeActionneurs(CommandeActionneurs _cmd) {
    this.cmd.add( _cmd);
  }
  
  void display() {
    fill(couleurBG);
    stroke(0, 0, 0);
    rect(xBloc, yBloc, largeurBloc, hauteurBloc);
    fill(couleurText);
    textSize(10);
    text(this.text, this.xBloc+10, this.yBloc+20);
    if(this.boutons != null) {
      int yBase = this.yBloc + 30;
      for(Button bouton : boutons) {
        if(bouton != null) {
          bouton.setX(xButtonDefault);
          bouton.setY(yBase + espaceY);
          bouton.display();
          espaceY += 40;
        }
      }
      espaceY = 0;
    }
    if(cmd != null) {
      for(CommandeActionneurs c : cmd) {
        c.display();
      }
    }
  }
}
