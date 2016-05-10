

class Tapis {
  
  ArrayList<Piece> pieces;
  
  Verin v1;
  Piece piece;
  Capteur c0;
  Capteur c1;
  Capteur c2;
  
  float xTapis;
  float yTapis;
  float largeurTapis;
  float hauteurTapis;
  float vitesseTapis;
  String directionFonctionnement;
  
  boolean stop = false;
  boolean possede_Piece = false;
  int niveau_tapis;
  
  Tapis() {}
  
  Tapis (float _xTapis, float _yTapis, float _largeurTapis, float _hauteurTapis, String _directionFonctionnement) {
    xTapis = _xTapis;
    yTapis = _yTapis;
    largeurTapis = _largeurTapis;
    hauteurTapis = _hauteurTapis;
    directionFonctionnement = _directionFonctionnement;
  }
  
  Tapis (float _xTapis, float _yTapis, float _largeurTapis, float _hauteurTapis, float _vitesseTapis, String _directionFonctionnement) {
    xTapis = _xTapis;
    yTapis = _yTapis;
    largeurTapis = _largeurTapis;
    hauteurTapis = _hauteurTapis;
    vitesseTapis = _vitesseTapis;
    directionFonctionnement = _directionFonctionnement;
  }
  
  void setVitesseTapis(float vitesse) {
    this.vitesseTapis = vitesse;
  }
  
  void setNiveauTapis(int niveau) {
    this.niveau_tapis = niveau;
  }
  
  void setListPieces(ArrayList<Piece> pieces) {
    this.pieces = pieces;
  }
  
  void setCapteurC0(Capteur capteur) {
    this.c0 = capteur;
  }
  
  void setCapteurC1(Capteur capteur) {
    this.c1 = capteur;
  }
  
  void setCapteurC2(Capteur capteur) {
    this.c2 = capteur;
  }
  
  void setVerinV1(Verin v) {
    this.v1 = v;
  }

  void allouerPiece(Piece b) {
    this.piece = b;
    this.possede_Piece = true;
  }

  void desallouerPiece() {
    this.possede_Piece = false;
    this.piece = null;
  }
  
  void display() {
    fill(#464B4D);
    rect(xTapis, yTapis, largeurTapis, hauteurTapis);  // base du tapis
    ellipse(xTapis, (yTapis + (yTapis + hauteurTapis)) / 2, (yTapis + (yTapis + hauteurTapis)) / 2 - yTapis,
          ((yTapis + (yTapis + hauteurTapis)) / 2 - yTapis) *2);                   // cercle gauche
    ellipse(xTapis + largeurTapis, (yTapis + (yTapis + hauteurTapis)) / 2, (yTapis + (yTapis + hauteurTapis)) / 2 - yTapis,
          ((yTapis + (yTapis + hauteurTapis)) / 2 - yTapis) *2);                   // cercle droit
    if(this.piece != null) {
      this.piece.display();
    }      
  }
  
  void run() {
    if(this.piece != null) {
      if(!stop) {
          /*
          if(this.directionFonctionnement == "gauche") {
            if(vitesseTapis != 0) {
             this.Piece.xPiece -= vitesseTapis;
            } else {
                this.Piece.xPiece -= 10;
            } 
          }
          */
          this.piece.yPiece = this.yTapis - this.piece.hauteurPiece;
          if(this.directionFonctionnement == "droite") {
             if(vitesseTapis != 0) {
               this.piece.xPiece += vitesseTapis;
            } else {
                this.piece.xPiece += 3;
            }
          }
      }
      /*
      if(this.directionFonctionnement == "gauche") {
           if(this.Piece.xPiece <= this.xTapis) {
               stop = true;
               this.capteur.detect = false;
           } else {
               stop = false;
           }
       }
       */
       if(this.directionFonctionnement == "droite") {
           if(this.piece.xPiece >= (this.xTapis + this.largeurTapis)) {
               stop = true;
           } else {
               stop = false;
           }
      } 
    }
  }
}
