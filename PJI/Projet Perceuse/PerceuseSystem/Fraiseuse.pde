
class Fraiseuse {
  //Déclaration des paramètres de base de la Fraiseuse
  float xBase;              // coordonnées en X pour la base du Fraiseuse
  float yBase;              // coordonnées en Y pour la base du Fraiseuse
  float largeurBase;        // largeur de la base du Fraiseuse
  float hauteurBase;        // hauteur de la base du Fraiseuse
  color couleurBase;        // couleur de la base de la Fraiseuse

    float xFraise;            // coordonnées en X de la fraise
  float yFraise;            // coordonnées en Y de la fraise
  float largeurFraise;      // largeur du fraise de la Fraiseuse
  float hauteurFraise;      // hauteur du fraise de la Fraiseuse
  color couleurFraise;      // couleur de la base du Fraise

    float vitesseFraiseuse;   //vitesse de déplacement de la Fraiseuse
  boolean bool_aller_fraiseuse;    // déplacement aller de la fraiseuse  
  boolean bool_retour_fraiseuse;    // déplacement retour de la fraiseuse
  boolean bool_automatic;          // mode automatique

  float xMin;    // xMin où peut se déplacer la fraiseuse
  float xMax;    // xMax où peut se déplacer la fraiseuse
  
  float yMin;  // yMin où peut se déplacer la fraiseuse
  float yMax;  // yMax où peut se déplacer la fraiseuse

  Boite boite;
  Capteur capt_origin;
  Capteur capt_max;

  //Constructeur de la Fraiseuse
  Fraiseuse (float _xBase, float _yBase, float _largeurBase, float _hauteurBase, float _largeurFraise, 
  float _hauteurFraise, color _couleurBase, color _couleurFraise) {
    xBase           = _xBase;
    yBase           = _yBase;
    largeurBase     = _largeurBase;
    hauteurBase     = _hauteurBase;
    couleurBase     = _couleurBase;

    largeurFraise    = _largeurFraise;
    hauteurFraise    = _hauteurFraise;
    xFraise          = (xBase + (xBase+largeurBase))/2 - largeurFraise/2;
    yFraise          = yBase + hauteurBase;
    couleurFraise    = _couleurFraise;

    bool_aller_fraiseuse = false;
    bool_retour_fraiseuse = false; 
    bool_automatic = false; 

    this.capt_origin = new Capteur(0, 0);
    this.capt_max = new Capteur(0, 0);
  }

  // Met à jour les coordonnées de la fraise par rapport à la fraiseuse
  void update() {
    this.xFraise = (xBase + (xBase+largeurBase))/2 - largeurFraise/2;
    this.yFraise = yBase + hauteurBase;
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

  // attribue une boite à la fraiseuse
  void attribuerBoite(Boite _boite) {
    this.boite = _boite;
  }

  // attribue le capteur de l'origine de la fraiseuse  
  void attribuerCapteurOrigine(Capteur _capteur) {
    this.capt_origin = _capteur;
  }

  // attribue le capteur de déplacement maximal de la fraiseuse
  void attribuerCapteurMax(Capteur _capteur) {
    this.capt_max = _capteur;
  }

  //Dessin de la fraiseuse
  void display() {                                
    noStroke();
    fill(couleurBase);
    rect(xBase, yBase, largeurBase, hauteurBase);    // dessin de la base de la fraiseuse
    fill(couleurFraise);
    rect(xFraise, yFraise, largeurFraise, hauteurFraise);    // dessin du Fraise de la fraiseuse
  }

  // Contrôle les déplacements de la fraiseuse
  void moove() {
    if (this.capt_max.detect) {
      bool_aller_fraiseuse = false;
    }  

    if (this.capt_origin.detect) { // si la tige arrive à sa position d'origine
      bool_retour_fraiseuse = false;
      if (this.boite.trouFraiseuse && this.bool_automatic) {
        this.bool_automatic = false;
      }
    }

    if (bool_aller_fraiseuse) {
      xBase = xBase + vitesseFraiseuse;
      this.update();
    }

    if (bool_retour_fraiseuse) {
      xBase = xBase - vitesseFraiseuse;
      this.update();
    }

    if (bool_automatic) {
      this.fraisageAutomatique();
    }
  }

  // définit la vitesse de déplacement de la fraiseuse
  void vitesseFraiseuse(float _vitesseFraiseuse) {
    vitesseFraiseuse = _vitesseFraiseuse;
  }

  // true si la fraise est dans la boite, false sinon
  boolean FraiseuseDansBoite() {
    if (xFraise+largeurFraise >= this.boite.xBoite && xFraise+largeurFraise < this.boite.xBoite + this.boite.largeurBoite) {
      return true;
    } else return false;
  }

  // Action de fraisage de la boite
  void FraisageBoite() {
    if (FraiseuseDansBoite()) {
      if (this.capt_max.detect && !this.boite.trouFraiseuse) {

        this.boite.trou_fraiseuse.xTrou = this.boite.xBoite;
        this.boite.trou_fraiseuse.yTrou = this.boite.yBoite;
        this.boite.trou_fraiseuse.widthTrou = this.xMax - this.boite.xBoite;
        this.boite.trou_fraiseuse.heightTrou = this.yFraise + hauteurFraise - this.boite.yBoite;

        this.boite.trouFraiseuse = true;
      }
    }
  }

  // Fraisage automatique de la boite (sans contrôle utilisateur)
  void fraisageAutomatique() {
    if (!this.boite.trouFraiseuse) {
      this.vitesseFraiseuse(2);
      this.bool_aller_fraiseuse = true;
      this.FraisageBoite();
    }

    if (this.boite.trouFraiseuse) {
      this.vitesseFraiseuse(1);
      this.bool_retour_fraiseuse = true;
    }
  }
}

