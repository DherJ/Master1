  

class Plateau {
  
    float xPlateau;        // coordonnées en X pour le plateau
    float yPlateau;        // coordonnées en Y pour le plateau
    float largeurPlateau;
    float hauteurPlateau;
    
    Verin v3;       // vérin pour pousser les caisses sur les tapis
    Verin v21;      // vérin pour lever le plateau au niveau 2
    Verin v22;      // vérin pour lever le plateau au niveau 1
    Verin v1;       // vérin pour mettre une Piece sur le plateau
    
    Capteur c20;    // capteur pour indiquer la présence du plateau au niveau 0
    Capteur c21;    // capteur pour indiquer la présence du plateau au niveau 1
    Capteur c22;    // capteur pour indiquer la présence du plateau au niveau 2
    Capteur c0;     // capteur pour indiquer la présence d'une pièce au niveau 0
    Capteur c1;     // capteur pour indiquer la présence d'une pièce au niveau 1
    Capteur c2;     // capteur pour indiquer la présence d'une pièce au niveau 2

    Tapis niveau0;
    Tapis niveau1;
    Tapis niveau2;
    
    Piece piece;    // Piece positionnée sur le plateau
    
    boolean stop = false;
    
    boolean position_init = true;
            
    boolean bool_plateau_niveau_0 = true;
    boolean bool_plateau_niveau_1 = false;
    boolean bool_plateau_niveau_2 = false;
    boolean bool_piece_niveau_0 = false;
    boolean bool_piece_niveau_1 = false;
    boolean bool_piece_niveau_2 = false;
    boolean bool_monter_niveau_1 = false;
    boolean bool_monter_niveau_2 = false;
    boolean bool_descendre = false;
    boolean bool_possede_piece = false;
    
    Plateau() {}
    
    Plateau(float _xPlateau, float _yPlateau, float _largeurPlateau, float _hauteurPlateau) {
      xPlateau = _xPlateau;
      yPlateau = _yPlateau;
      largeurPlateau = _largeurPlateau;
      hauteurPlateau = _hauteurPlateau;
    }
    
    void setVerinV3(Verin v) {
      this.v3 = v;
    }
    
    void setVerinV22(Verin v) {
      this.v22 = v;
    }
    
    void setVerinV21(Verin v) {
      this.v21 = v;
    }
    
    void setVerinV1(Verin v) {
      this.v1 = v;
    }
    
    void setCapteurC20(Capteur c) {
      this.c20 = c;
    }
    
    void setCapteurC21(Capteur c) {
      this.c21 = c;
    }
    
    void setCapteurC22(Capteur c) {
      this.c22 = c;
    }
    
    void setCapteurC0(Capteur c) {
      this.c0 = c;
    }
    
    void setCapteurC1(Capteur c) {
      this.c1 = c;
    }
    
    void setCapteurC2(Capteur c) {
      this.c2 = c;
    }
    
    void setNiveau0(Tapis t) {
      this.niveau0 = t;
    }
    
    void setNiveau1(Tapis t) {
      this.niveau1 = t;
    }
    
    void setNiveau2(Tapis t) {
      this.niveau2 = t;
    }
    
    void display() {
      fill(128, 128, 128);
      rect(xPlateau, yPlateau, largeurPlateau, hauteurPlateau);
      this.v3.yBase = this.yPlateau - this.v3.hauteurBase;
      this.v3.yTige = this.v3.yBase + 20;
      
      if(this.piece != null) {
        this.piece.display();
      }
    }

    void allouerPiece(Piece b) {
        this.piece = b;
        this.v3.setPiece(this.piece);
        this.bool_possede_piece = true;
    }

    void desallouerPiece() {
        this.bool_possede_piece = false;
        this.v3.piece = null;
        this.piece = null;
    }
    
    void corrigerCoordonneePlateau() {
        this.v21.largeurTige = this.v21.largeurTigeInit;
        this.v22.largeurTige = this.v22.largeurTigeInit;
        this.yPlateau = this.v22.yTige - this.v22.largeurTige - this.hauteurPlateau;
    }
    
    void monterNiveau1() {
        this.v21.sortirTige();
        this.v22.yBase = this.v21.yTige - this.v21.largeurTige;
        this.v22.yTige = this.v22.yBase - this.v22.largeurBase;
        this.yPlateau = this.v22.yTige - this.v22.largeurTige - this.hauteurPlateau;
        if(this.piece != null) {
          this.piece.yPiece = this.yPlateau - this.piece.hauteurPiece;
        }
        if(this.v22.largeurTige > this.v22.deplMaxTige) {
          this.bool_monter_niveau_1 = false;
        }
    }
    
    void monterNiveau2() {
        this.v21.sortirTige();
        this.v22.yBase = this.v21.yTige - this.v21.largeurTige;
        this.v22.yTige = this.v22.yBase - this.v22.largeurBase;
        this.yPlateau = this.v22.yTige - this.v22.largeurTige - this.hauteurPlateau;
        if(this.piece != null) {
          this.piece.yPiece = this.yPlateau - this.piece.hauteurPiece;
        }
        if(this.v21.largeurTige > this.v21.deplMaxTige) {
          this.v22.sortirTige();
        }
        if(this.v21.largeurTige > this.v21.deplMaxTige && this.v22.largeurTige > this.v22.deplMaxTige) {
          this.bool_monter_niveau_2 = false;
        }
    }
    
    void descendreNiveau0() {
        if(bool_plateau_niveau_1) {
          this.v21.rentrerTige();
          this.v22.yBase = this.v21.yTige - this.v21.largeurTige;
          this.v22.yTige = this.v22.yBase - this.v22.largeurBase;
          this.yPlateau = this.v22.yTige - this.v22.largeurTige - this.hauteurPlateau;
          if(((this.v21.xTige + this.v21.largeurTige) < this.v21.minXVerinRent) && ((this.v22.xTige + this.v22.largeurTige) < this.v22.minXVerinRent)) {
            this.bool_descendre = false;
            this.position_init = true;
            this.bool_plateau_niveau_1 = false;
          }
        }
        if(bool_plateau_niveau_2) {
          this.v21.rentrerTige();
          this.v22.yBase = this.v21.yTige - this.v21.largeurTige;
          this.v22.yTige = this.v22.yBase - this.v22.largeurBase;
          this.v22.rentrerTige();
          this.yPlateau = this.v22.yTige - this.v22.largeurTige - this.hauteurPlateau;
          if(((v21.xTige + v21.largeurTige) < v21.minXVerinRent) && ((v22.xTige + v22.largeurTige) < v22.minXVerinRent)) {
            this.bool_descendre = false;
            this.position_init = true;
            this.bool_plateau_niveau_2 = false;
          }
        }
        if (this.v21.bool_sort && this.v22.bool_sort && (this.yPlateau != this.c20.yCapteur)) {
            corrigerCoordonneePlateau();
        }
    }
    
    void mettrePieceSurPlateau() {
          this.v1.setPiece(this.c0.pieceDetected);
          this.v1.sortirTige();
          this.v1.rentrerTige();
          /*
          if((this.Piece != null) && 
              ((this.Piece.xPiece + this.Piece.largeurPiece) < (this.xPlateau + this.largeurPlateau - 20)) && ((v1.xTige + v1.largeurTige) < v1.minXVerinRent)) {
          */
          if((v1.xTige + v1.largeurTige) < v1.minXVerinRent) {
            this.allouerPiece(this.c0.pieceDetected);
            this.v1.piece = null;
            this.bool_piece_niveau_0 = false;
            this.niveau0.piece = null;
            this.c0.pieceDetected = null;
          }
    }
    
    void pousserPieceNiveau1() {
        if(this.piece != null) {
          this.v3.sortirTige();
          this.v3.rentrerTige();
        }
        if((this.piece != null) && ((this.v3.xTige + this.v3.largeurTige) < this.v3.minXVerinRent) && 
           (this.piece.xPiece > (this.c1.xCapteur + this.c1.largeurCapteur))) {
          this.v3.piece = null;
          this.niveau1.allouerPiece(this.piece);
          this.desallouerPiece();
          this.bool_descendre = true;
          this.bool_monter_niveau_1 = false;
          this.bool_piece_niveau_1 = true;
        }
    }
    
    void pousserPieceNiveau2() {
        if(this.piece != null) {
          this.v3.sortirTige();
          this.v3.rentrerTige();
        }
        this.niveau2.allouerPiece(this.piece);
        if(((v3.xTige + v3.largeurTige) < v3.minXVerinRent) && 
            (this.piece.xPiece > (this.c2.xCapteur + this.c2.largeurCapteur))) {
          this.v3.piece = null;
          this.niveau2.allouerPiece(this.piece);
          this.desallouerPiece();
          this.bool_descendre = true;
          this.bool_monter_niveau_2 = false;
          this.bool_piece_niveau_2 = true;
        }
    }
    
    void run() {
        if(!stop) {
          if(this.yPlateau != this.c20.yCapteur) {
            this.position_init = false;
          }
          if((this.yPlateau < this.c20.yCapteur) && !this.position_init && !this.bool_monter_niveau_1 && !this.bool_monter_niveau_2 && !bool_descendre) {
            corrigerCoordonneePlateau();
          }
          // détection de la présence d'une pièce au niveau 0
          if(this.c0.detectPiece == true) {
            this.bool_piece_niveau_0 = true;
          }
          // détection de la présence d'une pièce au niveau 1
          if(this.c1.detectPiece == true || (this.niveau1.piece != null)) {
            this.bool_piece_niveau_1 = true;
          }
          // détection de la présence d'une pièce au niveau 2
          if(this.c2.detectPiece == true || (this.niveau2.piece != null)) {
            this.bool_piece_niveau_2 = true;
          }
          // détection du plateau au niveau 0
          if(this.c20.detectPlateau) {
            bool_plateau_niveau_0 = true;
          }
          // détection du plateau au niveau 1
          if(this.c21.detectPlateau) {
            bool_plateau_niveau_1 = true;
          }
          // détection du plateau au niveau 2
          if(this.c22.detectPlateau) {
            bool_plateau_niveau_2 = true;
          }
          // si il y a une pièce au niveau 0 et le plateau est au niveau 0 alors on charge la pièce sur le plateau
          if((this.bool_piece_niveau_0 == true) && (this.bool_plateau_niveau_0 == true) && !this.bool_possede_piece) {
            mettrePieceSurPlateau();
          }
          // si une Piece est présente au niveau 2 et qu'il n'y en a pas au niveau 1
          if((this.piece != null) && (this.bool_plateau_niveau_0 == true) && ((this.v1.xTige + this.v1.largeurTige) < v1.minXVerinRent) && 
              (this.niveau1.piece == null) && (this.niveau2.piece != null)) {
            this.bool_monter_niveau_1 = true;
            // si il n'y a pas de Piece au niveau 1 ni au niveau 2
          } else if((this.piece != null) && (this.bool_plateau_niveau_0 == true) && ((this.v1.xTige + this.v1.largeurTige) < v1.minXVerinRent) && 
                    (this.niveau1.piece == null) && (this.niveau2.piece == null)) {
                    this.bool_monter_niveau_1 = true;
            // si il y a une Piece au niveau 1 et une Piece au niveau 2
          } else if((this.piece != null) && (this.bool_plateau_niveau_0 == true) && ((this.v1.xTige + this.v1.largeurTige) < v1.minXVerinRent) && 
                     (this.niveau1.piece != null) && (this.niveau2.piece != null)) {
                    this.bool_monter_niveau_1 = true;
           // si une Piece est présente au niveau 1
          } else if((this.piece != null) && (this.bool_plateau_niveau_0 == true) && ((this.v1.xTige + this.v1.largeurTige) < v1.minXVerinRent) && 
                  (this.niveau2.piece == null) && (this.niveau1.piece != null) && !this.bool_monter_niveau_1) {
                    this.bool_monter_niveau_2 = true;
          }
          if(this.bool_monter_niveau_1 == true) {
            monterNiveau1();
          }
          if(this.bool_monter_niveau_2 == true) {
            monterNiveau2();
          }
          if(this.bool_plateau_niveau_1 && (this.niveau1.piece == null)) {
            pousserPieceNiveau1();
          }
          if(this.bool_plateau_niveau_2 && (this.niveau2.piece == null)) {
            pousserPieceNiveau2();
          }
          if(this.niveau1.piece != null) {
            this.niveau1.run();
          }
          if(this.niveau2.piece != null) {
            this.niveau2.run();
          }
          if(this.bool_descendre) {
            descendreNiveau0();
          }
        }
    }
}
