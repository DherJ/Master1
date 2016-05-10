
class Button {
  String name;    // nom du bouton
  String text;    // texte affiché sur le bouton
  color colorName;  // couleur du texte sur le bouton
  float x;        // coordonnées en X pour le Bouton
  float y;        // coordonnées en Y pour le Bouton
  float largeur;  // largeur du Bouton
  float hauteur;  // hauteur du Bouton
  color colorButton;  // couleur du fond du bouton
  color originalColorButton;  // couleur d'origine du bouton
  boolean buttonActive;  // true si le bouton est activé, false sinon


  // Constructeur par défaut avec nom, coordonnées x/y et largeur/hauteur du bouton
  Button (String _nameButton, float _xButton, float _yButton, float _widthButton, float _heightButton) {
    name = _nameButton;
    x = _xButton;
    y = _yButton;
    largeur = _widthButton;
    hauteur = _heightButton;
    buttonActive = false;
  }

  // Constructeur avec uniquement le nom du bouton
  Button (String _nameButton) {
    this.name = _nameButton;
  }

  // Définit le texte sur le bouton
  void setText(String _text) {
    text = _text;
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

  // Applique la couleur de fond du bouton
  void setColorBG(String colorChoose) {
    if (colorChoose.equals("red")) {
      this.colorButton = color(200, 20, 20);
    } 
    if (colorChoose.equals("green")) {
      this.colorButton = color(0, 200, 45);
    }
    if (colorChoose.equals("blue")) {
      this.colorButton = color(20, 20, 180);
    }
    if (colorChoose.equals("black")) {
      this.colorButton = color(0, 0, 0);
    }
    if (colorChoose.equals("white")) {
      this.colorButton = color(255, 255, 255);
    }
  }

  // Applique la couleur du texte du bouton
  void setColorText(String colorChoose) {
    if (colorChoose.equals("black")) {
      this.colorName = color(0, 0, 0);
    }
    if (colorChoose.equals("white")) {
      this.colorName = color(255, 255, 255);
    }
  }

  void updateColor() {
    if (this.buttonActive == true) {
      this.setColorBG("red");
    } else {
      this.setColorBG("green");
    }
  }

  // Action quand le bouton est activé
  void buttonPressed() {
    if (this.buttonActive == false) {
      this.buttonActive = true;
      this.updateColor();
    } else {
      this.buttonActive = false;
      this.updateColor();
    }
  }

  // Affiche le bouton
  void display() {        
    stroke(0);
    fill(colorButton);
    rect(x, y, largeur, hauteur, 10, 10, 10, 10);    // dessin du Button
    fill(colorName);
    text(text, (this.x+this.largeur/10), (this.y+this.hauteur/3), this.largeur-(this.largeur/10), this.hauteur-(this.hauteur/3));
  }
}

