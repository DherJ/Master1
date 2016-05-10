
class Ascenseur {

  float xCabine;
  float yCabine;
  float largeurCabine;    // largeur de la cabine
  float hauteurCabine;    // hauteur de la cabine

    float xPied;
  float yPied;
  float largeurPied;   // largeur de la pied
  float hauteurPied;   // hauteur de la pied

    Porte porteGauche;
  Porte porteDroite;      

  int numEtage;
  Etage[] listEtage;  


  // Constructeur par défaut 
  Ascenseur(float _xPied, float _yPied, float _largeurPied, float _hauteurPied, float _hauteurCabine) {

    this.xPied = _xPied;
    this.yPied = _yPied;
    this.largeurPied = _largeurPied;
    this.hauteurPied = _hauteurPied;

    this.hauteurCabine = _hauteurCabine;

    this.largeurCabine = this.largeurPied;
    this.xCabine = this.xPied;
    this.yCabine = this.yPied - this.hauteurCabine;
  }
  
  void update() {
    this.yCabine = this.yPied - this.hauteurCabine;
    this.porteGauche.y = this.yCabine;
    this.porteDroite.y = this.yCabine;
  }

  void setPosition(float _x, float _y) {
    this.xPied = _x;
    this.yPied = _y;
    this.update();
  }

  void initPorte(color colorGauche, color colorDroite) {
    this.porteGauche = new Porte(this.xCabine, this.yCabine, this.largeurCabine/2, this.hauteurCabine, colorGauche);
    this.porteDroite = new Porte(this.xCabine+this.largeurCabine/2, this.yCabine, this.largeurCabine/2, this.hauteurCabine, colorDroite);
  }

  void definirNbEtage(int nbEtage) {
    this.listEtage = new Etage[nbEtage];
  }

  void attribuerEtage(Etage _etage, int numEtage) {
    this.listEtage[numEtage] = _etage;
  }

  void setDefaultEtage(Etage _etage) {
    this.yPied = _etage.y;
    this.update();
  }
  
  
  void deplacerVersEtage(Etage _etage) {
    if (this.yPied > _etage.capteur.value) {
      this.monter();
    } else if (this.yPied < _etage.capteur.value) {
      this.descendre();
    } else { 
      this.setPosition(this.xPied, _etage.capteur.value);
      _etage.button.buttonActive = false;
      _etage.button.updateColor();
    }
  }
  
  /*
  void ouverturePorte() {
    if(this.bool_porte_ouverte) {
      if (this.porteGauche.largeur > 0 && this.porteDroite.largeur > 0)
      this.porteGauche.largeur -= 1;
      this.porteDroite.x += 1;
      this.porteDroite.largeur -= 1;
      
    } else {
      
    }
  }
  */

  void monter() {
    this.yPied -= 1;
    this.update();
  }
  
  void descendre() {
    this.yPied += 1;
    this.update();
  }

  void display() {
    // dessin de la cabine
    noFill();
    stroke(0);
    rect(this.xCabine, this.yCabine, this.largeurCabine, this.hauteurCabine);

    // dessin porte gauche et droite    
    this.porteGauche.display();
    this.porteDroite.display();

    // dessin pied
    fill(180, 180, 30);
    rect(this.xCabine, this.yCabine+this.hauteurCabine, this.largeurPied, this.hauteurPied);

    // dessin etage
    if (this.listEtage != null) {
      for (Etage etage : listEtage) {
        etage.display();
      }
    }
  }
}

