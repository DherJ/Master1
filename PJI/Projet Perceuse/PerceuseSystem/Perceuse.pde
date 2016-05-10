
class Perceuse {
  //Déclaration des paramètres de base de la perceuse
  float xBase;      // coordonnées en X pour la base du perceuse
  float yBase;      // coordonnées en Y pour la base du perceuse
  float largeurBase;    // largeur de la base du perceuse
  float hauteurBase;    // hauteur de la base du perceuse
  color couleurBase;     // couleur de la base de la perceuse

    float xForet;          // coordonnées en X du foret
  float yForet;          // coordonnées en Y du foret
  float largeurForet;    // largeur du foret de la perceuse
  float hauteurForet;    // hauteur du foret de la perceuse
  color couleurForet;     // couleur de la base du foret

    String direction;  // direction de la perceuse

  float vitessePercee;  //vitesse de déplacement de la perceuse
  boolean bool_aller_perceuse;  // déplacement aller de la perceuse
  boolean bool_retour_perceuse;  // déplacement retour de la perceuse
  boolean bool_automatic;    // mode automatique

  float xMin;    // xMin où peut se déplacer la fraiseuse
  float xMax;    // xMax où peut se déplacer la fraiseuse
  
  float yMin;  // yMin où peut se déplacer la fraiseuse
  float yMax;  // yMax où peut se déplacer la fraiseuse

  Boite boite;
  Capteur capt_origin;
  Capteur capt_max;

  //Constructeur de la perceuse
  Perceuse (float _xBase, float _yBase, float _largeurBase, float _hauteurBase, float _largeurForet, 
  float _hauteurForet, color _couleurBase, color _couleurForet) {
    xBase           = _xBase;
    yBase           = _yBase;
    largeurBase     = _largeurBase;
    hauteurBase     = _hauteurBase;
    couleurBase     = _couleurBase;
    
    largeurForet    = _largeurForet;
    hauteurForet    = _hauteurForet;  
    // xForet et yForet correct uniquement pour la direction gauche
    xForet          = xBase - hauteurBase;
    yForet          = (yBase + (yBase+largeurBase))/2 - largeurForet/2;
    couleurForet    = _couleurForet;
    
    this.capt_origin = new Capteur(0, 0);
    this.capt_max = new Capteur(0, 0);
    
    bool_aller_perceuse = false;  
    bool_retour_perceuse = false;
    bool_automatic = false;
  }
  
  // Met à jour les coordonnées du foret par rapport à la perceuse
  void update() {
    this.xForet = xBase - hauteurBase;
    this.yForet = (yBase + (yBase+largeurBase))/2 - largeurForet/2;
  }
  
  // définit xMin et xMax
  void setX(float xMin, float xMax) {
    this.xMin = xMin;
    this.xMax = xMax;
  }
  
  // définit yMin et yMax
  void setY(float yMin, float yMax) {
    this.yMin = yMin;
    this.yMax = yMax;
  }

  // définit l'orientation de la perceuse
  void setDirection(String _direction) {
    this.direction = _direction;
  }

  // attribue une boite à la perceuse
  void attribuerBoite(Boite _boite) {
    this.boite = _boite;
  }

  // attribue le capteur de l'origine de la perceuse  
  void attribuerCapteurOrigine(Capteur _capteur) {
    this.capt_origin = _capteur;
  }

  // attribue le capteur de déplacement maximal de la perceuse
  void attribuerCapteurMax(Capteur _capteur) {
    this.capt_max = _capteur;
  }

  //Dessin de la perceuse
  void display() {                                
    noStroke();
    if (direction == "droite") {
      fill(couleurBase);
      rect(xBase, yBase, largeurBase, hauteurBase);    // dessin de la base de la perceuse
      fill(couleurForet);
      rect(xForet, yForet, largeurForet, hauteurForet);    // dessin du foret de la perceuse
    } else if (direction == "gauche") {
      fill(couleurBase);
      rect(xBase, yBase, -hauteurBase, largeurBase);    
      fill(couleurForet);
      rect(xForet, yForet, -hauteurForet, largeurForet);    
    } else if (direction == "haut") {
      fill(couleurBase);
      rect(xBase, yBase, -largeurBase, -largeurBase);    
      fill(couleurForet);
      rect(xForet, yForet, -largeurForet, -hauteurForet);    
    } else if (direction == "bas") {
      fill(couleurBase);
      rect(xBase, yBase, largeurBase, hauteurBase);   
      fill(couleurForet);
      rect(xForet, yForet, largeurForet, hauteurForet);    
    }
  }

  // Contrôle le déplacement de la perceuse
  void moove() {
    if (this.capt_max.detect) {
      bool_aller_perceuse = false;
    } 

    if (this.capt_origin.detect) { // si la base arrive à sa position d'origine
      bool_retour_perceuse = false;
      if(this.boite.trouPerceuse && this.bool_automatic) {
        this.bool_automatic = false;
      }
    }

    if (bool_retour_perceuse) {
      xBase = xBase + vitessePercee;
      this.update();
    }

    if (bool_aller_perceuse) {    
      xBase = xBase - vitessePercee;
      this.update();
    }
    
    if (bool_automatic) {
      this.percageAutomatique();
    }
  }

  // Définit la vitesse de déplacement de la perceuse
  void vitessePercage(float _vitessePercee) {
    vitessePercee = _vitessePercee;
  }

  // true si le foret est dans le boite, false sinon
  boolean PerceuseDansBoite() {
    if (xForet-hauteurForet <= this.boite.xBoite+this.boite.largeurBoite && xForet-hauteurForet > this.boite.xBoite) {
      return true;
    } else return false;
  }

  // Action de perçage de la boite
  void PercageBoite() {

    if (PerceuseDansBoite()) {
      if (this.capt_max.detect && !this.boite.trouPerceuse) {

        this.boite.trou_perceuse.xTrou = this.boite.xBoite + this.boite.largeurBoite;
        this.boite.trou_perceuse.yTrou = yForet;
        this.boite.trou_perceuse.widthTrou = -(this.boite.xBoite + this.boite.largeurBoite - (perceuse.xForet - perceuse.hauteurForet));
        this.boite.trou_perceuse.heightTrou = perceuse.largeurForet;

        this.boite.trouPerceuse = true;
      }
    }
  }
  
  // Perçage automatique de la boite (sans contrôle utilisateur)
  void percageAutomatique() {
    if (!this.boite.trouPerceuse) {
      this.vitessePercage(3);
      this.bool_aller_perceuse = true;
      this.PercageBoite();
    }
    
    if (this.boite.trouPerceuse) {
      this.vitessePercage(1);
      this.bool_retour_perceuse = true;
    }
  }
}

