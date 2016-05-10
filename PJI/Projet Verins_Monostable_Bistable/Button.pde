
class Button {
  String name;
  String text;
  String type;
  color colorName;
  color colorFilled;
  float x;        // coordonnées en X pour le Bouton
  float y;        // coordonnées en Y pour le Bouton
  float largeur;  // largeur du Bouton
  float hauteur;  // hauteur du Bouton
  color colorButton;
  color originalColorButton;
  boolean buttonActive;
  boolean buttonSelected;
  String colorChoose;
  
  int tailleText = 10;
 
  boolean drag;
  
  Button() {}
  
  Button (String _name) {
    this.name = _name;
    this.largeur = 70;
    this.hauteur = 20;
  }
  
  Button (String _nameButton, float _xButton, float _yButton, float _widthButton, float _heightButton) {
    name = _nameButton;
    x = _xButton;
    y = _yButton;
    largeur = _widthButton;
    hauteur = _heightButton;
    buttonActive = false;
  }

  void setX(int _x) {
    this.x = _x;
  }
  
  void setY(int _y) {
    this.y = _y;
  }
  
  void setText(String _text) {
    this.text = _text;  
  }
  
  void setTypeButton(String _type) {
    this.type = _type;
  }
  
  void setTailleText(int _taille) {
    this.tailleText = _taille;
  }
  
  void setColor(color _color) {
    this.colorButton = _color;
  }
  
  void setColorFilled(color _color) {
    this.colorFilled = _color;
  }
  
  // Définit la position du bouton
  void setPosition(float _x, float _y) {
    x = _x;
    y = _y;
  }
  
  // Définit la taille du bouton
  void setSize(float _width, float _height) {
    largeur = _width;
    hauteur = _height;
  }
  
  void setColorBG(String colorChoose) {
    this.colorChoose = colorChoose;
    if (colorChoose.equals("red")) {
      this.colorButton = color(255, 0, 0);
    } 
    if (colorChoose.equals("green")) {
      this.colorButton = color(0, 255, 0);
    }
    if (colorChoose.equals("blue")) {
      this.colorButton = color(#00A4E8);
    }
    if (colorChoose.equals("black")) {
      this.colorButton = color(0, 0, 0);
    }
    if (colorChoose.equals("white")) {
      this.colorButton = color(255, 255, 255);
    }
    if (colorChoose.equals("grey")) {
      this.colorButton = color(#A9A2A2);
    }
    if (colorChoose.equals("orange")) {
      this.colorButton = color(#FF7E27);
    }
  }

  void setColorText(String colorChoose) {
    if (colorChoose.equals("black")) {
      this.colorName = color(0, 0, 0);
    }
    if (colorChoose.equals("white")) {
      this.colorName = color(255, 255, 255);
    }
  }

  void buttonFocused() {
    if(this.colorChoose != null) {
      if ( (mouseX >= x) && (mouseX <= x+largeur) && 
           (mouseY >= y) && (mouseY <= y+hauteur) && this.colorChoose.equals("red")) {
        fill(#FF272E);
      } else if ( (mouseX >= x) && (mouseX <= x+largeur) && 
           (mouseY >= y) && (mouseY <= y+hauteur) && this.colorChoose.equals("green")) {
            fill(#36FC37);
      } else if ( (mouseX >= x) && (mouseX <= x+largeur) && 
           (mouseY >= y) && (mouseY <= y+hauteur) && this.colorChoose.equals("blue")) {
            fill(#00E2E8);
      } else if ( (mouseX >= x) && (mouseX <= x+largeur) && 
           (mouseY >= y) && (mouseY <= y+hauteur) && this.colorChoose.equals("grey")) {
        fill(#CAC4C4);
      } else if ( (mouseX >= x) && (mouseX <= x+largeur) && 
           (mouseY >= y) && (mouseY <= y+hauteur) && this.colorChoose.equals("orange")) {
        fill(#F88F48);
      } else {
        fill(colorButton);
      }
    } else {
              if ( (mouseX >= x) && (mouseX <= x+largeur) && (mouseY >= y) && (mouseY <= y+hauteur)) {
                 fill(this.colorFilled);
              } else {
                fill(colorButton);
             }
    }  
  }
  
  void display() {        
    noFill();
    rect(x, y, largeur, hauteur, 10, 10, 10, 10);
    buttonFocused();
    rect(x, y, largeur, hauteur, 10, 10, 10, 10);    // dessin du Button
    fill(colorName);
    if(this.text != null) {
      textSize(tailleText);
      text(text, (this.x+this.largeur/10), (this.y+this.hauteur/3), this.largeur-(this.largeur/10), this.hauteur-(this.hauteur/3));
    }
    if(this.drag) {
      dragButton();
    }
  }
  
  boolean overButton() {
    if ( (mouseX >= x) && (mouseX <= x+largeur) && (mouseY >= y) && (mouseY <= y+hauteur))
          return true;
    else  return false;
  }
  
  boolean buttonClicked() {
    if(overButton() && (mouseButton == LEFT))
         return true;
    else return false;
  }
  
  void dragButton() {
     this.x = mouseX - this.largeur / 2;
     this.y = mouseY - this.hauteur / 2;
  }
}
