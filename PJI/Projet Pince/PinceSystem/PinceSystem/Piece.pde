
class Piece {
  float x;
  float y;        
  float largeur;  
  float hauteur; 

  boolean bool_touche = false;

  Piece(float _x, float _y, float _largeur, float _hauteur) {
    this.x = _x;
    this.y = _y;
    this.largeur = _largeur;
    this.hauteur = _hauteur;
  }

  void display() {                                
    noStroke();
    fill(0, 180, 180);
    rect(this.x, this.y, this.largeur, this.hauteur);    // dessin de la boite
  }

}

