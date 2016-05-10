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
  
  Piece piece;           // pièce qui sera poussée par le vérin
  Plateau plateau;       // plateau qui sera poussé par le vérin
  
  /* Variables locales */
  boolean bool_rent = false;
  boolean bool_sort = true;
  boolean touche = false;
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
  
  void setVitesseTigeRent(float vitesse) {
    this.vitesseTigeEntr = vitesse;
  }
  
  void setVitesseTigeSort(float vitesse) {
    this.vitesseTigeSort = vitesse;
  }
  
  void setPiece(Piece piece) {
    this.piece = piece;
  }
  
  void setPlateau(Plateau _plateau) {
    this.plateau = _plateau;
  }
  
  // Dessin du vérin
  void display() {                                
    noStroke();
    beginShape();
    if(direction == "droite") {
      fill(couleurBase);
      rect(xBase, yBase, largeurBase, hauteurBase);    // dessin de la base du vérin
      fill(couleurTige);
      rect(xTige, yTige, largeurTige, hauteurTige);    // dessin de la tige du vérin
      rect(xTige + largeurTige, yBase, 10, hauteurBase);
    } else if(direction == "gauche") {
        fill(couleurBase);
        rect(xBase, yBase, -largeurBase, hauteurBase);    // dessin de la base du vérin
        fill(couleurTige);
        rect(xTige, yTige, -largeurTige, hauteurTige);    // dessin de la tige du vérin  
        rect(xTige - largeurTige, yBase, 10, hauteurBase);
      }
      else if(direction == "haut") {
        fill(couleurBase);
        rect(xBase, yBase, hauteurBase, -largeurBase);    // dessin de la base du vérin
        fill(couleurTige);
        rect(xTige, yTige, hauteurTige, -largeurTige);    // dessin de la tige du vérin
        rect(xBase, yTige - largeurTige, hauteurBase, 10);
      }
      else if(direction == "bas") {
        fill(couleurBase);
        rect(xBase, yBase, hauteurBase, largeurBase);    // dessin de la base du vérin
        fill(couleurTige);
        rect(xTige, yTige, hauteurTige, largeurTige);    // dessin de la tige du vérin 
        rect(xBase, yTige + largeurTige, hauteurBase, 10);
      }
      endShape();
      if(this.piece != null) {
        this.piece.display();
      }
  }
  
  void checkCollisions() {
        if((this.direction == "droite") && (this.piece != null)) {
          if(((this.xTige + this.largeurTige) == this.piece.xPiece) && (this.bool_sort == true)) {
            touche = true;
          } else if(this.bool_rent == true) {
                    touche = false;
                  }
          if(touche == true) {
             this.piece.xPiece = this.largeurTige + this.xTige;
          } else {
              this.piece.xPiece = this.piece.xPiece;
          }
       }
       if((this.direction == "gauche") && (this.piece != null)) {
            if(((this.xTige - this.largeurTige) == (this.piece.xPiece + this.piece.largeurPiece)) && (this.bool_sort == true)) {
                touche = true;
            } else if(this.bool_rent == true) {
                    touche = false;
                }
            if(touche == true) {
               this.piece.xPiece = this.xTige - this.largeurTige - this.piece.largeurPiece;
            } else {
               this.piece. xPiece = this.piece.xPiece;
            }
        }
        if((this.direction == "haut") && (this.plateau != null)) {
              if(((this.yTige - this.largeurTige) == (this.plateau.yPlateau + this.plateau.hauteurPlateau)) && (this.bool_sort == true)) {
                touche = true;
              } else if(this.bool_rent == true) {
                        touche = false;
                    }
              if(touche == true) {
                 this.plateau.yPlateau = this.yTige - this.largeurTige - this.plateau.hauteurPlateau;
              } else {
                  this.plateau.yPlateau = this.plateau.yPlateau;
              }
          } 
          if((this.direction == "bas") && (this.piece != null)) {
                if(((this.xTige + this.largeurTige) == this.piece.yPiece) && (this.bool_sort == true)) {
                  touche = true;
                } else if(this.bool_rent == true) {
                  touche = false;
                }
                if(touche == true) {
                   this.piece.yPiece = this.largeurTige + this.yTige;
                } else {
                    this.piece.yPiece = this.piece.yPiece;
                }
           }  
  }
  
  boolean isVerinSorti() {
    return (largeurTige > deplMaxTige);
  }
  
  boolean isVerinRentre() {
    return (xTige + largeurTige < minXVerinRent);
  }
  
  void sortirTige() {
    if(bool_sort == true) {
      largeurTige = largeurTige + vitesseTigeSort;
      checkCollisions();
    }
    if(largeurTige > deplMaxTige) {
      bool_sort = false;
      bool_rent = true;
    }
  }
  
  void rentrerTige() {
    if(bool_rent == true) {
      largeurTige = largeurTige - vitesseTigeEntr;
      if(this.plateau != null) {
        this.plateau.yPlateau = this.yTige - this.largeurTige - 20;
      }
    }
    if(xTige + largeurTige < minXVerinRent) {
      bool_rent = false;
      bool_sort = true;
    }
  }
}
