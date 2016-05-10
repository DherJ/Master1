
class Porte {
  float x;
  float y;
  float largeur;
  float hauteur;
  color colorPorte;

  Porte (float _x, float _y, float _largeur, float _hauteur, color _colorPorte) {
    this.x = _x;
    this.y = _y;
    this.largeur = _largeur;
    this.hauteur = _hauteur;
    this.colorPorte = _colorPorte;
  }
  
  void setColor(color _colorPorte) {
    this.colorPorte = _colorPorte;
  }
  
  void display() {
    fill(this.colorPorte);
    rect(this.x, this.y, this.largeur, this.hauteur);
  }
  
}

