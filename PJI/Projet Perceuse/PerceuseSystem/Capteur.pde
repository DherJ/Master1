
class Capteur {

  String nameCapteur;      // nom du capteur
  float xCapteur;          // coordonnées X du capteur
  float yCapteur;          // coordonnées Y du capteur
  float largeurCapteur;    // largeur du capteur
  float hauteurCapteur;    // hauteur du capteur

  boolean detect;          // 1 si le capteur detecte, 0 sinon
  float value;             // valeur à detecter
  int range;               // écart de détection de la valeur (ex: range=5, on detecte la valeur à + ou -5 de value)
 
  color colorCapteur;      // couleur du capteur


  // Constructeur par défaut du capteur
  Capteur(String _nameCapteur, float _xCapteur, float _yCapteur, float _largeurCapteur, float _hauteurCapteur) {
    this.nameCapteur = _nameCapteur;
    this.xCapteur = _xCapteur;
    this.yCapteur = _yCapteur;
    this.largeurCapteur = _largeurCapteur;
    this.hauteurCapteur = _hauteurCapteur;
  }
  
  // Constructeur sans taille du capteur
  Capteur(String _nameCapteur, float _xCapteur, float _yCapteur) {
    this.nameCapteur = _nameCapteur;
    this.xCapteur = _xCapteur;
    this.yCapteur = _yCapteur;
  }
  
  // Constructeur sans nom ni taille de capteur
  Capteur(float _xCapteur, float _yCapteur) {
    this.xCapteur = _xCapteur;
    this.yCapteur = _yCapteur;
  }

  void setPosition(float _x, float _y) {
    this.xCapteur = _x;
    this.yCapteur = _y;
  }

  void setSize(float _largeur, float _hauteur) {
    this.largeurCapteur = _largeur;
    this.hauteurCapteur = _hauteur;
  }
  
  void setValue(float _value, int _range) {
    this.value = _value;
    this.range = _range;
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
    fill(0, 0, 0);
    text("Capteur " +this.nameCapteur, this.xCapteur-80, this.yCapteur+10);

    fill(colorCapteur);
    rect(xCapteur, yCapteur, largeurCapteur, hauteurCapteur);
  }


  void detect(float currentValue) {
    if (currentValue >= this.value-this.range && currentValue <= this.value+this.range) {
      detect = true;
      this.changeColor();
    } else {
      detect = false;
      this.changeColor();
    }
  }

  /*
  void detectSuperior(float currentValue, float maxValue) {

    if (currentValue >= maxValue) {
      detect = true;
      this.changeColor();
    } else {
      detect = false;
      this.changeColor();
    }
  }

  void detectInferior(float currentValue, float maxValue) {
    if (currentValue <= maxValue) {
      detect = true;
      this.changeColor();
    } else {
      detect = false;
      this.changeColor();
    }
  }
  */

  void changeColor() {
    if (detect) { 
      this.setColor("green");
    } else { 
      this.setColor("red");
    }
  }
}

