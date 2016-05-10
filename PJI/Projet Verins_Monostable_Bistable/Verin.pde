// Verin (float _xBase, float _yBase, float _largeur, float _hauteur, float _largeurTige, 
//                    float _deplMaxTige, color _couleurBase, color _couleurTige)


class Verin {
  //Déclaration des paramètres de base du vérin
  float xBase;          // coordonnées en X pour la base du vérin
  float yBase;          // coordonnées en Y pour la base du vérin
  float largeurBase;    // largeur de la base du vérin
  float hauteurBase;    // hauteur de la base du vérin
  float xTige;
  float yTige;
  float largeurTige;    // largeur de la tige du vérin
  float hauteurTige;    // hauteur de la tige du vérin
  float deplMaxTige;
  float vitesseTigeSort; //vitesse de la tige quand elle sort de la base
  float vitesseTigeEntr; //vitesse de la tige quand elle rentre dans la base
  color couleurBase;     // couleur de la base du vérin
  color couleurTige;     // couleur de la tige du vérin
  String type;           // type du vérin : bistable ou monostable
  String direction;      // direction dans laquelle la tige va pousser : gauche, droit, haut ou bas
  
  /* Variables locales */
  boolean bool_rent = false;
  boolean bool_sort = true;
  float minXVerinRent;  
  float largeurTigeInit;
  
  Verin() {}
  
  //Constructeur du vérin
  Verin (float _xBase, float _yBase, float _largeurBase, float _hauteurBase, float _largeurTige, 
        float _deplMaxTige , color _couleurBase, color _couleurTige, String _type, String _direction) {
    xBase           = _xBase;
    yBase           = _yBase;
    largeurBase     = _largeurBase;
    hauteurBase     = _hauteurBase;
    /* définition des coordonnées des formes du vérin en fonction de sa direction */
    if(_direction == "droite") {
    xTige = xBase + largeurBase;
    yTige = yBase + 20;
    } else if(_direction == "gauche") {
      xTige = xBase - largeurBase;
      yTige = yBase + 20;
    }
    else if(_direction == "haut") {
      xTige = xBase + 20;
      yTige = yBase - largeurBase;
    }
    else if(_direction == "bas") {
      xTige = xBase + 20;
      yTige = yBase + largeurBase;
    }
    largeurTige     = _largeurTige;
    largeurTigeInit = _largeurTige;
    hauteurTige     = _hauteurBase - 40;
    deplMaxTige     = _deplMaxTige;
    couleurBase     = _couleurBase;
    couleurTige     = _couleurTige;
    vitesseTigeSort = 2; 
    vitesseTigeEntr = 3; 
    minXVerinRent   = xTige + _largeurTige;
    type      = _type;
    direction = _direction;
  }
  
  //Dessin du vérin
  void display() {                                
    noStroke();
    beginShape();
    if(direction == "droite") {
      fill(couleurBase);
      rect(xBase, yBase, largeurBase, hauteurBase);    // dessin de la base du vérin
      fill(couleurTige);
      rect(xTige, yTige, largeurTige, hauteurTige);    // dessin de la tige du vérin
    } else if(direction == "gauche") {
        fill(couleurBase);
        rect(xBase, yBase, -largeurBase, hauteurBase);    // dessin de la base du vérin
        fill(couleurTige);
        rect(xTige, yTige, -largeurTige, hauteurTige);    // dessin de la tige du vérin  
      }
      else if(direction == "haut") {
        fill(couleurBase);
        rect(xBase, yBase, hauteurBase, -largeurBase);    // dessin de la base du vérin
        fill(couleurTige);
        rect(xTige, yTige, hauteurTige, -largeurTige);    // dessin de la tige du vérin
      }
      else if(direction == "bas") {
        fill(couleurBase);
        rect(xBase, yBase, hauteurBase, largeurBase);    // dessin de la base du vérin
        fill(couleurTige);
        rect(xTige, yTige, hauteurTige, largeurTige);    // dessin de la tige du vérin 
      }
      rect(xTige + largeurTige, yBase, 10, hauteurBase);
      endShape();
  }
  
  void sortirTige() {
    if(bool_sort == true) {
      largeurTige = largeurTige + vitesseTigeSort;
    }
    if(largeurTige > deplMaxTige) {
      bool_sort = false;
      bool_rent = true;
    }
  }
  
  void rentrerTige() {
    if(bool_rent == true) {
      largeurTige = largeurTige - vitesseTigeEntr;
    }
    if(xTige + largeurTige < minXVerinRent) {
      bool_rent = false;
      bool_sort = true;
      this.largeurTige = this.largeurTigeInit;
    }
  }
}
