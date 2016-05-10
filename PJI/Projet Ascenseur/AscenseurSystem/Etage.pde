
class Etage {
  float x;
  float y;
  float largeur;
  float hauteur;
  
  Button button;
  Capteur capteur;
  
  int numEtage;

  Etage (float _x, float _y, float _largeur, float _hauteur) {
    this.x = _x;
    this.y = _y;
    this.largeur = _largeur;
    this.hauteur = _hauteur;
  }

  void attribuerNumEtage (int _numEtage) {
    this.numEtage = _numEtage;
  }
  
  void addButton(String _nameButton, float _xButton, float _yButton, float _widthButton, float _heightButton) {
    this.button = new Button(_nameButton, _xButton, _yButton, _widthButton, _heightButton);
  }

  void addCapteur(float _xCapteur, float _yCapteur, float _largeurCapteur, float _hauteurCapteur) {
    this.capteur = new Capteur(_xCapteur, _yCapteur, _largeurCapteur, _hauteurCapteur);
  }

  void display() {
    fill(140);
    rect(this.x, this.y, this.largeur, this.hauteur);
    
    if(this.button != null) {
      this.button.display();
    }
    
    if(this.capteur != null) {
      this.capteur.display();
    }
  }
  
}

