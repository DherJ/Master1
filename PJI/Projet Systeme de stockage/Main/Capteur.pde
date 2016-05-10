
class Capteur {
  
  ArrayList <Piece> pieces;
  Piece pieceDetected = new Piece();
  Plateau plateau = new Plateau();
  
  float xCapteur;
  float yCapteur;
  float largeurCapteur;
  float hauteurCapteur;
  String name;
  color colorCapteur;
  
  boolean detectPiece = false;
  boolean detectPlateau = false;
  
  Capteur() {}
  
  Capteur(float _xCapteur, float _yCapteur, float _largeurCapteur, float _hauteurCapteur) {
    this.xCapteur = _xCapteur;
    this.yCapteur = _yCapteur;
    this.largeurCapteur = _largeurCapteur;
    this.hauteurCapteur = _hauteurCapteur;
  }
  
  void setName(String _name) {
    this.name = _name;
  }
  
  String getName() {
    return this.name;
  }
  
  void setListPieces(ArrayList <Piece> pieces) {
    this.pieces = pieces;
  }
  
  void setPlateau(Plateau _plateau) {
    this.plateau = _plateau;
  }
  
  void setColor(String colorChoose) {
    if (colorChoose.equals("red")) {
      this.colorCapteur = color(255, 0, 0);
    } 
    if (colorChoose.equals("green")) {
      this.colorCapteur = color(0, 255, 0);
    }
  }
  
  void display() {
    if (detectPiece() || detectPlateau()) { 
      this.setColor("green");
    } else { 
      this.setColor("red");
    }
    fill(this.colorCapteur);
    rect(xCapteur, yCapteur, largeurCapteur, hauteurCapteur);
  }
  
  boolean detectPiece() {
    if(this.pieces != null) {
      for(Piece piece : pieces) {
        // vérifie si la Piece est à la même hauteur que le capteur ( en x )
        //if((this.yCapteur == (piece.yPiece + piece.hauteurPiece)) && 
        if((this.yCapteur <= (piece.yPiece + piece.hauteurPiece) + 3) && (this.yCapteur >= (piece.yPiece + piece.hauteurPiece) - 3) && 
              // si l'extrémité gauche du capteur est entre les extrémités gauche et droite de la Piece
              (((this.xCapteur <= (piece.xPiece + piece.largeurPiece)) && (this.xCapteur >= piece.xPiece)) ||
              //  si l'extrémité droite du capteur est entre les extrémités gauche et droite de la Piece
              (((this.xCapteur + this.largeurCapteur) <= (piece.xPiece + piece.largeurPiece)) && ((this.xCapteur + this.largeurCapteur) >= piece.xPiece)))) {
                  this.detectPiece = true;
                  this.pieceDetected = piece;
                  break;
        } else {
          this.detectPiece = false;
        }
      }
    }
    return this.detectPiece;
  }
  
  boolean detectPlateau() {
      //if(((this.yCapteur <= (this.plateau.yPlateau + 15)) && (this.yCapteur >= (this.plateau.yPlateau - 15)) && 
      //  (this.yCapteur >= this.plateau.yPlateau)) && (this.plateau != null)) {
      if((this.plateau != null) && 
            ((this.plateau.yPlateau <= (this.yCapteur + this.hauteurCapteur)) && (this.plateau.yPlateau >= this.yCapteur) || 
            ((this.plateau.yPlateau + this.plateau.hauteurPlateau) >= this.yCapteur) && (this.plateau.yPlateau + this.plateau.hauteurPlateau) <= (this.yCapteur + this.hauteurCapteur))) {
          this.detectPlateau = true;
      } else {
        this.detectPlateau = false;
      }
      return this.detectPlateau;
  }
}
