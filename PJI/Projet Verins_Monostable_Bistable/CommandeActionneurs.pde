
class CommandeActionneurs {
  
  int x;
  int y;
  
  String text;
  color colorText;
  int textSize;
  
  ArrayList<CommandeActionneur> commandes;
  
  int yBase;
  int espaceX = 75;
  int espaceY = 0;
  
  CommandeActionneurs(int _x, int _y) {
    this.x = _x;
    this.y = _y;
    this.commandes = new ArrayList<CommandeActionneur>();
    yBase = y + 10;
  }
  
  void setText(String _text) {
    this.text = _text;
  }
  
  void setTextSize(int _size) {
    this.textSize = _size;
  }
  
  void setColorText(color _color) {
    this.colorText = _color;
  }
  
  void addCommandeActionneur(CommandeActionneur commande) {
    this.commandes.add(commande);
  }
  
  void removeCommandeActionneur(String nameActionneur) {
    for(CommandeActionneur cmd : commandes) {
      if(cmd.getName().equals(nameActionneur)) {
        commandes.remove(cmd);
        break;
      }
    }
  }
  
  void display() {
    if(this.commandes != null && this.commandes.get(0).arreter != null && this.commandes.get(0).activer != null) {
        if(this.text != null) {
            fill(this.colorText);
            textSize(textSize);
            text(this.text, this.x + 30, this.y - 15);
        }
        this.commandes.get(0).setX(this.x);
        this.commandes.get(0).setY(yBase);
        this.commandes.get(0).display();
        espaceY += 40;
        for(int i = 1; i < commandes.size(); i++) {
          commandes.get(i).setX(this.x);
          commandes.get(i).setY(this.y + espaceY);
          commandes.get(i).display();
          espaceY += 30;
        }
        espaceY = 0;
    } else {
            if(this.text != null) {
              fill(this.colorText);
              textSize(textSize);
              text(this.text, this.x + 30, this.y - 15);
            }
            espaceY += 10;
            for(int i = 0; i < commandes.size() - 1; i++) {
              commandes.get(i).setX(this.x);
              commandes.get(i).setY(this.y + espaceY);
              commandes.get(i).display();
              commandes.get(i + 1).setX(this.x + espaceX);
              commandes.get(i + 1).setY(this.y + espaceY);
              commandes.get(i + 1).display();
              espaceY += 30;
            }
            espaceY = 0;
    }
  }
}
