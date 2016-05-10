
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
  
  int espaceX = 0;
  int espaceY = 10;
  int xButtonDefault;
  int xStart;
  boolean bool_positionButtonFixe;
  
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
  
  void setPositionBoutonFixe(boolean bool) {
    this.bool_positionButtonFixe = bool;
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
    if(!this.bool_positionButtonFixe) {
      if((this.boutons != null) && (this.boutons.size() < 5)) {
        int yBase = this.yBloc + 30;
        for(Button bouton : boutons) {
          if(bouton != null) {
            bouton.setX(xButtonDefault);
            bouton.setY(yBase + espaceY);
            bouton.display();
            espaceY += 30;
          }
        }
        espaceY = 0;
      } else if(this.boutons != null) {
            int yBase = this.yBloc + 30;
            int xBase = this.xBloc + 20;
            for(int i = 0; i < this.boutons.size() - 1; i = i + 2) {
              if(this.boutons.get(i) != null) {
                this.boutons.get(i).setX(xBase + espaceX);
                this.boutons.get(i).setY(yBase + espaceY);
                this.boutons.get(i).display();
                espaceX = xBase + 60;
                this.boutons.get(i + 1).setX(xBase + espaceX);
                this.boutons.get(i + 1).setY(yBase + espaceY);
                this.boutons.get(i + 1).display();
                espaceX = 0;
                espaceY += 30;
              }
            }
            espaceY = 0;
            espaceX = 0;
          }
      if(cmd != null) {
        for(CommandeActionneurs c : cmd) {
          c.display();
        }
      }
    } else {
      for(Button bouton : this.boutons) {
        bouton.display();
      }
    }
  }
}
