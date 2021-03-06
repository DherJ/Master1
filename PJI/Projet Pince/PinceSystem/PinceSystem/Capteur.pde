
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

    Pince pince;             // Permet d'attribuer la pince au capteur


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

  // Place le capteur
  void setPosition(float _x, float _y) {
    this.xCapteur = _x;
    this.yCapteur = _y;
  }

  // Définit la taille (largeur et hauteur) du capteur
  void setSize(float _largeur, float _hauteur) {
    this.largeurCapteur = _largeur;
    this.hauteurCapteur = _hauteur;
  }

  // Définit la valeur que le capteur doit détecter
  // Ainsi que l'écart de détection (detect value +/- range
  void setValue(float _value, int _range) {
    this.value = _value;
    this.range = _range;
  }

  // Attribue une pince au capteur
  void setPince(Pince p) {
    this.pince = p;
  }

  // Définit la couleur du capteur
  void setColor(String colorChoose) {
    if (colorChoose.equals("red")) {
      this.colorCapteur = color(255, 0, 0);
    } 
    if (colorChoose.equals("green")) {
      this.colorCapteur = color(0, 255, 0);
    }
  }  

  /*
  boolean detectPince() {
   //print("test detectPince" +this.pince.xBras);
   // if ( (this.pince.xPince+5 >= detectValue-15) && (this.pince.xPince+5 <= detectValue+15) ) {
   if ( (this.xCapteur >= this.pince.xBras) && (this.xCapteur <= this.pince.xBras + this.pince.largeurBras) ||
   (this.xCapteur + this.largeurCapteur >= this.pince.xBras) && (this.xCapteur + this.largeurCapteur <= this.pince.xBras + this.pince.largeurBras) ) {
   this.detect = true;
   return this.detect;
   } else { 
   this.detect = false; 
   return this.detect;
   }
   }
   */

  void display() {
    stroke(0);
    if (detect) { 
      this.setColor("green");
    } else { 
      this.setColor("red");
    }
    fill(this.colorCapteur);
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

  void changeColor() {
    if (detect) { 
      this.setColor("green");
    } else { 
      this.setColor("red");
    }
  }
}

