
class Tapis {

  float xTapis;
  float yTapis;
  float largeurTapis;
  float hauteurTapis;
  Piece piece;

  boolean possede_Piece;

  Tapis (float _xTapis, float _yTapis, float _largeurTapis, float _hauteurTapis) {
    xTapis = _xTapis;
    yTapis = _yTapis;
    largeurTapis = _largeurTapis;
    hauteurTapis = _hauteurTapis;
  }

  /*
  void allouerPiece(Piece _p) {
   this.piece = _p;
   this.possede_Piece = true;
   }
   */

  void ajouterPiece(float x, float y, float largeur, float hauteur) {
    this.piece = new Piece(x, y, largeur, hauteur);
    this.possede_Piece = true;
  }

  void allouerPiece(Piece p) {
    this.piece = new Piece(p.x, p.y, p.largeur, p.hauteur);
    this.possede_Piece = true;
  }

  void desallouerPiece(Piece _p) {
    this.possede_Piece = false;
    this.piece = null;
  }

  void display() {
    noStroke();
    fill(#464B4D);
    rect(xTapis, yTapis, largeurTapis, hauteurTapis);  // base du tapis
    ellipse(xTapis, (yTapis + (yTapis + hauteurTapis)) / 2, (yTapis + (yTapis + hauteurTapis)) / 2 - yTapis, 
    ((yTapis + (yTapis + hauteurTapis)) / 2 - yTapis) *2);                   // cercle gauche
    ellipse(xTapis + largeurTapis, (yTapis + (yTapis + hauteurTapis)) / 2, (yTapis + (yTapis + hauteurTapis)) / 2 - yTapis, 
    ((yTapis + (yTapis + hauteurTapis)) / 2 - yTapis) *2);                   // cercle droit

    if (this.piece != null) {
      this.piece.display();
    }
  }

  void moove() {
  }
}

