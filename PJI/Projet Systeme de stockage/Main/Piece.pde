

class Piece {
  
    int id;
    float xPiece;        // coordonnées en X pour la Piece
    float yPiece;        // coordonnées en Y pour la Piece
    float largeurPiece;  // largeur de la Piece
    float hauteurPiece;  // hauteur de la Piece
    color couleurPiece;
    
    Piece() {}
    
    Piece (int _id, float _xPiece, float _yPiece, float _largeurPiece, float _hauteurPiece) {
        this.id = _id;
        this.xPiece = _xPiece;
        this.yPiece = _yPiece;
        this.largeurPiece = _largeurPiece;
        this.hauteurPiece = _hauteurPiece;
        this.couleurPiece = color(#22B149);
    }
    
    Piece (int _id, float _xPiece, float _yPiece, float _largeurPiece, float _hauteurPiece, color _couleurPiece) {
        this.id = _id;
        this.xPiece = _xPiece;
        this.yPiece = _yPiece;
        this.largeurPiece = _largeurPiece;
        this.hauteurPiece = _hauteurPiece;
        this.couleurPiece = _couleurPiece;
    }
    
    Piece (float _xPiece, float _yPiece, float _largeurPiece, float _hauteurPiece, color _couleurPiece) {
        this.xPiece = _xPiece;
        this.yPiece = _yPiece;
        this.largeurPiece = _largeurPiece;
        this.hauteurPiece = _hauteurPiece;
        this.couleurPiece = _couleurPiece;
    }
  
    Piece (float _xPiece, float _yPiece, float _largeurPiece, float _hauteurPiece) {
        this.xPiece = _xPiece;
        this.yPiece = _yPiece;
        this.largeurPiece = _largeurPiece;
        this.hauteurPiece = _hauteurPiece;
        this.couleurPiece = color(#22B149);
    }
    
    void display() {                                
      noStroke();
      fill(couleurPiece);
      rect(xPiece, yPiece, largeurPiece, hauteurPiece);    // dessin de la Piece
    }
    
    void animationChute() {
      
    }
}
