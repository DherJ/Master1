
class Pince {

  float xBras;          // coordonnées X du bras de la pince
  float yBras;          // coordonnées Y du bras de la pince
  float largeurBras;    // largeur du bras de la pince
  float hauteurBras;    // hauteur du bras de la pince

  float xPince;         // coordonnées X de la pince
  float yPince;         // coordonnés Y de la pince
  float largeurPince;   // largeur de la pince
  float hauteurPince;   // hauteur de la pince

    float hauteurBrasInit; // hauteur initiale du bras de la pince
  float hauteurBrasMax;  // hauteur maximale du bras de la pince

  Piece piece;          // Permet d'attribuer une piece à la pince
  Button button;        // Permet d'attribuer un bouton à la pince

  int numPoste;         // numéro du poste où se trouve la pince
  int numPostePrecedent;  // numéro du poste précédent où se trouvait la pince
  Poste[] listPoste;  // liste des postes vers lesquelles peut se déplacer la pince

    boolean ventouse;           // vrai si ventouse, faux si pince
  boolean bool_pince_ouverte;  // vrai pince ouverte, faux sinon
  boolean bool_objet;          // vrai pince tient un objet, faux sinon
  boolean bool_descente;       // vrai la pince descend, faux si elle remonte

    float vitesse_deplacement;  // vitesse de déplacement G/D de la pince
  float vitesse_descente;

  int steps;    // étapes liste des actions quand on appuie sur un bouton


  // Constructeur par défaut 
  Pince(float _xBras, float _yBras, float _largeurBras, float _hauteurBras, 
  float _largeurPince, float _hauteurPince) {
    this.xBras = _xBras;
    this.yBras = _yBras;

    this.largeurBras = _largeurBras;
    this.hauteurBras = _hauteurBras;
    this.hauteurBrasInit = this.hauteurBras;

    this.largeurPince = _largeurPince;
    this.hauteurPince = _hauteurPince;

    this.xPince = this.xBras+this.largeurBras/2 - this.largeurPince/2;
    this.yPince = this.yBras+this.hauteurBras;

    this.vitesse_descente = 1;
    this.steps = 0;
    this.ventouse = false;
    this.bool_pince_ouverte = true;
  }

  void setPosition(float _xBras, float _yBras) {
    this.xBras = _xBras;
    this.yBras = _yBras;
    this.update();
  }

  void update() {
    this.xPince = this.xBras+this.largeurBras/2 - this.largeurPince/2;
    this.yPince = this.yBras+this.hauteurBras;

    if (this.piece != null) {
      this.piece.x = this.xPince;
      this.piece.y = this.yPince + this.hauteurPince;
    }
  }

  void setHauteurBrasMax(float _hauteurBrasMax) {
    this.hauteurBrasMax = _hauteurBrasMax;
  }

  void setSpeed(float _speed) {
    this.vitesse_deplacement = _speed;
  }

  void setDefaultPoste(Poste poste, int numPoste) {
    this.numPoste = numPoste;
    this.numPostePrecedent = 0;
    this.xBras = poste.capteur.value;
    this.update();
  }

  void attribuerBouton(Button _button) {
    this.button = _button;
  }

  void gestionPince() {
    if (this.button.buttonActive) {
      bool_pince_ouverte = false;
    } else {
      bool_pince_ouverte = true;
    }
  }

  void definirNbPoste(int nbPoste) {
    this.listPoste = new Poste[nbPoste];
  }

  void attribuerPoste(Poste p, int numPoste) {
    this.listPoste[numPoste] = p;
  }


  void display() {
    //dessin de la base 
    noStroke();
    fill(0, 0, 0);
    rect(xBras, yBras, largeurBras, hauteurBras);
    fill(20, 20, 200);
    rect(this.xPince, this.yPince, this.largeurPince, this.hauteurPince);

    //dessin de la pince
    /*
    if (bool_pince_ouverte) {
     quad(this.xPince, this.yPince+10, 
     this.xPince+5, this.yPince+10, 
     this.xPince, this.yPince+20, 
     this.xPince-5, this.yPince+20); 
     quad(this.xPince+this.largeurBras*3-5, this.yPince+10, 
     this.xPince+this.largeurBras*3, this.yPince+10, 
     this.xPince+this.largeurBras*3+5, this.yPince+20, 
     this.xPince+this.largeurBras*3, this.yPince+20);
     } else {
     quad(this.xPince, this.yPince+10, 
     this.xPince+5, this.yPince+10, 
     this.xPince+5, this.yPince+20, 
     this.xPince, this.yPince+20); 
     quad(this.xPince+largeurBras*3-5, this.yPince+10, 
     this.xPince+largeurBras*3, this.yPince+10, 
     this.xPince+largeurBras*3, this.yPince+20, 
     this.xPince+largeurBras*3-5, this.yPince+20);
     }
     */

    if (!this.ventouse) {
      fill(150, 30, 150);
      if (!bool_pince_ouverte) {
        quad(this.xPince-this.hauteurPince, this.yPince, 
        this.xPince, this.yPince, 
        this.xPince, this.yPince+20, 
        this.xPince-this.hauteurPince, this.yPince+20); 
        quad(this.xPince+this.largeurPince, this.yPince, 
        this.xPince+this.hauteurPince+this.largeurPince, this.yPince, 
        this.xPince+this.hauteurPince+this.largeurPince, this.yPince+20, 
        this.xPince+this.largeurPince, this.yPince+20);
      } else {
        quad(this.xPince, this.yPince, 
        this.xPince, this.yPince+this.hauteurPince, 
        this.xPince-20, this.yPince+this.hauteurPince, 
        this.xPince-20, this.yPince); 
        quad(this.xPince+this.largeurPince, this.yPince, 
        this.xPince+this.largeurPince, this.yPince+this.hauteurPince, 
        this.xPince+this.largeurPince+20, this.yPince+this.hauteurPince, 
        this.xPince+this.largeurPince+20, this.yPince);
      }
    }

    if (this.piece != null) {
      this.piece.display();
    }
  }

  void deplacementGauche() {   
    this.xBras -= this.vitesse_deplacement;
    this.update();
  }

  void deplacementDroite() {
    this.xBras += this.vitesse_deplacement;
    this.update();
  }

  void descente() {
    print("test descente 1 \n");
    this.hauteurBras += this.vitesse_descente;
    
    this.update();
  }

  /*
  void monter() {
   if (this.piece == null) {
   print("test monter1 \n");
   this.hauteurBras -= this.vitesse_deplacement;
   this.yPince -= this.vitesse_deplacement;
   } else {   
   print("test monter2 \n");
   this.hauteurBras -= this.vitesse_deplacement;
   this.yPince -= this.vitesse_deplacement;
   this.piece.y = this.yPince + this.hauteurPince;
   }
   
   if (this.hauteurBras <= this.hauteurBrasInit) {
   this.steps = 3;
   }
   }
   */

  boolean monter() {
    boolean test = false;
    print("test monter1 \n");
    this.hauteurBras -= this.vitesse_deplacement;
    this.update();

    if (this.hauteurBras <= this.hauteurBrasInit) {
      test = true;
    }
    return test;
  }


  void prendrePiece(Piece p) {
    this.piece = new Piece(p.x, p.y, p.largeur, p.hauteur);
    this.bool_objet = true;
  }

  void lacherPiece() {
    this.bool_objet = false;
    this.piece = null;
  }

  boolean collisionPiece(Piece p) {
    if (this.yPince+this.hauteurPince == p.y) {
      return true;
    } else return false;
  }

  //refaire avec la fonction detect et mettre au centre du capteur
  boolean deplacerVersPoste(Poste p) {     
    print("Test deplacerVersPoste \n");
    if (this.xBras > p.capteur.value+p.capteur.range) {
      this.deplacementGauche();
      return false;
    } else if (this.xBras < p.capteur.value-p.capteur.range) { 
      this.deplacementDroite();
      return false;
    } else {
      this.setPosition(p.capteur.value, this.yBras);
      //this.xBras = p.capteur.detectValue + p.capteur.largeurCapteur/2 - this.largeurBras/2;
      //this.xPince = this.xBras + this.largeurBras/2 - this.largeurPince/2;
      return true;
    }
  }

  /*
    void deplacerVersPoste(Poste p) {     
   print("Test deplacerVersPoste \n");
   if (this.xPince+5 > p.capteur.detectValue) {
   this.deplacementGauche();
   } else if (this.xPince+5 < p.capteur.detectValue) { 
   this.deplacementDroite();
   } else {
   this.steps = 1;
   }
   }
   */

  /*
  void chercherPiece(Poste p) {
   if (p.tapis.piece != null && this.yPince+this.hauteurPince < p.tapis.piece.y) {
   print("test chercherPiece 1 \n");
   this.bool_pince_ouverte = true;
   this.bool_descente=true;
   this.descente();
   if (this.collisionPiece(p.tapis.piece)) {
   this.prendrePiece(p.tapis.piece);
   print("test PrendrePiece \n");
   this.bool_descente =! this.bool_descente;
   this.bool_pince_ouverte = false;
   p.tapis.desallouerPiece(p.tapis.piece);
   this.steps = 2;
   }
   }
   }
   */

  boolean chercherPiece(Poste p) {
    boolean test = false;
    print("Test chercherPiece y Tapis : " +p.tapis.piece.y+ "\n");
    print("Test chercherPiece pince : " +(this.yPince+this.hauteurPince)+ "\n");
    print("Test chercherPiece piece présente : " +p.tapis.piece+ "\n");

    if (p.tapis.piece != null && this.yPince+this.hauteurPince < p.tapis.piece.y) {
      print("test chercherPiece 1 \n");
      this.bool_pince_ouverte = true;
      this.bool_descente=true;
      this.descente();

      if (this.collisionPiece(p.tapis.piece)) {
        this.prendrePiece(p.tapis.piece);
        print("test PrendrePiece \n");
        this.bool_descente =! this.bool_descente;
        this.bool_pince_ouverte = false;
        p.tapis.desallouerPiece(p.tapis.piece);
        test = true;
      }
    }
    return test;
  }

  /*
  void libererPiece(Poste p) {
   if (!p.tapis.possede_Piece && this.yPince+this.hauteurPince < this.yPince+this.hauteurBrasMax) {
   print("test libererPiece 1 \n");
   this.bool_descente=true;
   this.descente();
   }
   if (!p.tapis.possede_Piece && this.yPince+this.hauteurPince+this.piece.hauteur >= p.tapis.yTapis) {
   print("test libererPiece 2 \n");
   p.tapis.allouerPiece(this.piece);
   this.lacherPiece();
   this.bool_descente =! this.bool_descente;
   this.bool_pince_ouverte = false;
   this.steps = 2;
   }
   }
   */

  boolean libererPiece(Poste p) {
    boolean test = false;

    if (!p.tapis.possede_Piece && this.yPince+this.hauteurPince < this.yPince+this.hauteurBrasMax) {
      print("test libererPiece 1 \n");
      this.bool_descente = true;
      this.descente();
    }
    if (!p.tapis.possede_Piece && this.yPince+this.hauteurPince+this.piece.hauteur >= p.tapis.yTapis) {
      print("test libererPiece 2 \n");
      p.tapis.allouerPiece(this.piece);
      this.lacherPiece();
      this.bool_descente =! this.bool_descente;
      this.bool_pince_ouverte = true;
      test = true;
    }
    return test;
  }


  void actionPiece(Poste poste_dest) {
    if (this.steps == 0) { 
      //this.deplacerVersPoste(this.listPoste[numPoste]);
      if (this.deplacerVersPoste(this.listPoste[numPoste])) {
        this.steps = 1;
      }
    }

    if (this.steps == 1) {
      if (this.piece == null) {
        if (poste_dest.tapis.piece == null) {
          print("test ChercherPiece \n");
          //this.chercherPiece(this.listPoste[numPoste]);
          if (this.chercherPiece(this.listPoste[numPoste])) {
            this.steps = 2;
          }
        } else { 
          print("Poste déjà occupé ! \n"); 
          poste_dest.button.buttonActive = false;
          poste_dest.button.setColorBG("green");
          this.steps = 0;
        }
      } else {
        print("test libererPiece \n");
        //this.libererPiece(poste_dest);
        if (this.libererPiece(poste_dest)) {
          this.steps = 2;
        }
      }
    }

    if (this.steps == 2) {      
      print("test steps 2 \n");
      //this.monter();
      if (this.monter()) {
        this.steps = 3;
      }
    }

    if (this.steps == 3) {
      print("test steps 3 \n");
      //this.deplacerVersPoste(poste_dest);
      if (this.deplacerVersPoste(poste_dest)) {
        this.numPostePrecedent = numPoste;
        this.steps = 4;
      }
    }

    if (this.steps == 4) {
      print("test steps 4 \n");
      //this.libererPiece(poste_dest);
      if (this.libererPiece(poste_dest)) {
        this.steps = 5;
      }
    }

    if (this.steps == 5) {
      print("test steps 4 \n");
      //this.libererPiece(poste_dest);
      if (this.monter()) {
        this.steps = 6;
      }
    }

    if (this.steps == 6) {
      this.numPoste = poste_dest.numPoste;
      poste_dest.button.buttonActive = false;
      poste_dest.button.setColorBG("green");
      this.steps = 0;
    }
  }


  /*  
   void actionPiece(Poste p) {
   if (this.steps == 0) {
   print("test steps0 \n");
   this.deplacerVersPoste(p);
   }
   
   if (this.steps == 1) {
   if (this.piece == null) {
   if (p.tapis.piece != null) {
   print("test ChercherPiece \n");
   this.chercherPiece(p);
   } else { 
   print("Pas de piece à livrer ou à aller chercher sur ce poste ! \n"); 
   p.button.buttonActive = false;
   this.steps = 0;
   }
   } else {
   print("test libererPiece \n");
   this.libererPiece(p);
   }
   }
   
   if (this.steps == 2) {      
   print("test steps 2 \n");
   this.monter();
   }
   
   if (this.steps == 3) {
   print("test steps 3 \n");
   p.button.buttonActive = false;
   this.steps = 0;
   }
   }
   */
}

