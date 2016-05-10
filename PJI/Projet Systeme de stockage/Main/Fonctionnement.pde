
class Fonctionnement {
    
    Console console;
    
    String modeFonctionnement;
  
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

    Plateau plateau; 
    
    Tapis niveau0;
    Tapis niveau1;
    Tapis niveau2;
    
    ArrayList<Piece> pieces;
    Piece pieceLVL0;
    Piece pieceLVL1;
    Piece pieceLVL2;
    Piece piecePlateau;
    int nbPieces = 0;               // nombre de pièces créées dans le système
    int nbPiecesMax = 0;            // nombre de pièce max à créer dans le système
    long lastTime = millis();       // variable pour comparer les temps pour savoir si le temps d'attente est atteint
    long timeToWait;                // temps d'attente entre l'apparition de 2 pièces en mode automatique
    
    boolean stop = false;
    
    boolean position_init_plateau_plateau = true;
            
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
    
    boolean start = true;          // boolean pour indiquer si le système démarre et initialiser une preemière pièce
    boolean initSystem = false;    // boolean pour réinitialiser le système en cas de changement de mode (automatique - manuel)
    
    boolean sortV1 = false;
    boolean sortV21 = false;
    boolean sortV22 = false;
    boolean sortV3 = false;
    
    boolean bool_cas1 = false;
    boolean bool_cas2 = false;
    boolean bool_cas3 = false;
    boolean bool_cas4 = false;
    
    /* interrupteurs */
    Button marche;
    Button arret;

    Button rentrerTigeV1;
    Button sortirTigeV1;
    Button rentrerTigeV21;
    Button sortirTigeV21;
    Button rentrerTigeV22;
    Button sortirTigeV22;
    Button rentrerTigeV3;
    Button sortirTigeV3;
    Button newPiece;
    /* boutons poussoirs */
    Button sortirTigeV1P;
    Button sortirTigeV21P;
    Button sortirTigeV22P;
    Button sortirTigeV3P;

    Fonctionnement() {
      this.pieces = new ArrayList<Piece>();
    }
    
    Fonctionnement(String _mode) {
      this.modeFonctionnement = _mode;
      this.pieces = new ArrayList<Piece> ();
    }
    
    void setConsole(Console _console) {
      this.console = _console;
    }
    
    void setTimeToWait(long time) {
      this.timeToWait = time;
    }
    
    void setNBPiecesMax(int nb) {
      this.nbPiecesMax = nb;
    }
    
    void setModeFonctionnement(String _mode) {
      this.modeFonctionnement = _mode;
    }
    
    void setButtonMarche(Button _marche) {
      this.marche = _marche;
    }
    
    void setButtonArret(Button _arret) {
      this.arret = _arret;
    }
    
    void setButtonRentrerV1(Button _button) {
      this.rentrerTigeV1 = _button;
    }
    
    void setButtonSortirV1(Button _button) {
      this.sortirTigeV1 = _button;
    }
    
    void setButtonRentrerV21(Button _button) {
      this.rentrerTigeV21 = _button;
    }
    
    void setButtonSortirV21(Button _button) {
      this.sortirTigeV21 = _button;
    }
    
    void setButtonRentrerV22(Button _button) {
      this.rentrerTigeV22 = _button;
    }
    
    void setButtonSortirV22(Button _button) {
      this.sortirTigeV22 = _button;
    }
    
    void setButtonRentrerV3(Button _button) {
      this.rentrerTigeV3 = _button;
    }
    
    void setButtonSortirV3(Button _button) {
      this.sortirTigeV3 = _button;
    }
    
    void setButtonNewPiece(Button _button) {
      this.newPiece = _button;
    }
    
    void setButtonSortirV1P(Button _button) {
      this.sortirTigeV1P = _button;
    }
    
    void setButtonSortirV21P(Button _button) {
      this.sortirTigeV21P = _button;
    }
    
    void setButtonSortirV22P(Button _button) {
      this.sortirTigeV22P = _button;
    }
    
    void setButtonSortirV3P(Button _button) {
      this.sortirTigeV3P = _button;
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
    
    void setPlateau(Plateau _plateau) {
      this.plateau = _plateau;
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
    
    String getModeFonctionnement() {
      return this.modeFonctionnement;
    }
    
    Verin getVerinV3() {
     return this.v3;
    }
    
    Verin getVerinV22() {
      return this.v22;
    }
    
    Verin getVerinV21() {
      return this.v21;
    }
    
    Verin getVerinV1() {
      return this.v1;
    }
    
    Capteur getCapteurC20() {
      return this.c20;
    }
    
    Capteur getCapteurC21() {
      return this.c21;
    }
    
    Capteur getCapteurC22() {
      return this.c22;
    }
    
    Capteur getCapteurC0() {
      return this.c0;
    }
    
    Capteur getCapteurC1() {
      return this.c1;
    }
    
    Capteur getCapteurC2() {
      return this.c2;
    }
    
    Plateau getPlateau() {
      return this.plateau;
    }

    Tapis getNiveau0() {
      return this.niveau0;
    }
    
    Tapis getNiveau1() {
      return this.niveau1;
    }
    
    Tapis getNiveau2() {
      return this.niveau2;
    }
    
    void reinitialiserSysteme() {
      nbPieces = 0;
      bool_plateau_niveau_0 = true;
      bool_plateau_niveau_1 = false;
      bool_plateau_niveau_2 = false;
      bool_piece_niveau_0 = false;
      bool_piece_niveau_1 = false;
      bool_piece_niveau_2 = false;
      bool_monter_niveau_1 = false;
      bool_monter_niveau_2 = false;
      bool_descendre = false;
      bool_possede_piece = false;
      start = true;
      initSystem = false;
      this.v1.piece = null;
      this.niveau0.desallouerPiece();
      this.niveau1.desallouerPiece();
      this.niveau2.desallouerPiece();
      this.plateau.desallouerPiece();
      pieces.clear();
      this.c0.setListPieces(pieces);
      this.c1.setListPieces(pieces);
      this.c2.setListPieces(pieces);
      if(this.v1.isVerinSorti()) {
        this.v1.bool_rent = true;
        this.v1.bool_sort = false;
        this.v1.rentrerTige();
      }
      if(this.v21.isVerinSorti()) {
        this.v21.bool_rent = true;
        this.v21.bool_sort = false;
        this.v21.rentrerTige();
      }
      if(this.v3.isVerinSorti()) {
        this.v3.bool_rent = true;
        this.v3.bool_sort = false;
        this.v3.rentrerTige();
      }
      this.v22.yBase = this.v21.yTige - this.v21.largeurTige;
      this.v22.yTige = this.v22.yBase - this.v22.largeurBase;
      if(this.v22.isVerinSorti()) {
        this.v22.bool_rent = true;
        this.v22.bool_sort = false;
        this.v22.rentrerTige();
      }
      if(this.v21.isVerinRentre() && this.v22.isVerinRentre()) {
        bool_descendre = false;
        position_init_plateau_plateau = true;
        bool_plateau_niveau_1 = false;
        bool_plateau_niveau_2 = false;
      }
      this.plateau.yPlateau = this.v22.yTige - this.v22.largeurTige - this.plateau.hauteurPlateau;
    }
    
    void corrigerCoordonneePlateau() {
        this.plateau.yPlateau = this.niveau0.yTapis;
        this.v21.largeurTige = this.v21.largeurTigeInit;
        this.v22.largeurTige = this.v22.largeurTigeInit + 2;
    }
    
    void monterNiveau1() {
        position_init_plateau_plateau = false;
        this.v21.sortirTige();
        this.v22.yBase = this.v21.yTige - this.v21.largeurTige;
        this.v22.yTige = this.v22.yBase - this.v22.largeurBase;
        this.plateau.yPlateau = this.v22.yTige - this.v22.largeurTige - this.plateau.hauteurPlateau;
        if(this.plateau.piece != null) {
          this.plateau.piece.yPiece = this.plateau.yPlateau - this.plateau.piece.hauteurPiece;
        }
        if(this.v21.isVerinSorti()) {
          bool_monter_niveau_1 = false;
          bool_plateau_niveau_1 = true;
          bool_plateau_niveau_0 = false;
          bool_plateau_niveau_2 = false;
        }
    }
    
    void monterNiveau2() {
        position_init_plateau_plateau = false;
        this.v21.sortirTige();
        this.v22.yBase = this.v21.yTige - this.v21.largeurTige;
        this.v22.yTige = this.v22.yBase - this.v22.largeurBase;
        this.plateau.yPlateau = this.v22.yTige - this.v22.largeurTige - this.plateau.hauteurPlateau;
        if(this.plateau.piece != null) {
          this.plateau.piece.yPiece = this.plateau.yPlateau - this.plateau.piece.hauteurPiece;
        }
        if(this.v21.isVerinSorti()) {
          this.v22.sortirTige();
        }
        if(this.v21.isVerinSorti() && this.v22.isVerinSorti()) {
          bool_monter_niveau_2 = false;
          bool_plateau_niveau_2 = true;
          bool_plateau_niveau_0 = false;
          bool_plateau_niveau_1 = false;
        }
    }
    
    void descendreNiveau0() {
        if(bool_plateau_niveau_1) {
          this.v21.rentrerTige();
          this.v22.yBase = this.v21.yTige - this.v21.largeurTige;
          this.v22.yTige = this.v22.yBase - this.v22.largeurBase;
          this.plateau.yPlateau = this.v22.yTige - this.v22.largeurTige - this.plateau.hauteurPlateau;
          if(this.v21.isVerinRentre() && this.v22.isVerinRentre()) {
            bool_descendre = false;
            position_init_plateau_plateau = true;
            bool_plateau_niveau_1 = false;
            bool_plateau_niveau_0 = true;
            this.v21.largeurTige = this.v21.largeurTigeInit;
          }
        } else if(bool_plateau_niveau_2) {
          this.v21.rentrerTige();
          this.v22.yBase = this.v21.yTige - this.v21.largeurTige;
          this.v22.yTige = this.v22.yBase - this.v22.largeurBase;
          this.v22.rentrerTige();
          this.plateau.yPlateau = this.v22.yTige - this.v22.largeurTige - this.plateau.hauteurPlateau;
          if(this.v21.isVerinRentre() && this.v22.isVerinRentre()) {
            bool_descendre = false;
            position_init_plateau_plateau = true;
            bool_plateau_niveau_0 = true;
            bool_plateau_niveau_1 = false;
            bool_plateau_niveau_2 = false;
            this.v21.largeurTige = this.v21.largeurTigeInit;
            this.v22.largeurTige = this.v22.largeurTigeInit;
          }
        }
        if (this.v21.isVerinRentre() && this.v21.isVerinRentre() && (this.plateau.yPlateau != this.c20.yCapteur)) {  
          corrigerCoordonneePlateau();
        }
    }
    
    void mettrePieceSurPlateau() {
          this.v1.setPiece(this.c0.pieceDetected);
          this.v1.sortirTige();
          this.v1.rentrerTige();
          // quand la tige du vérin est rentrée
          if(this.v1.isVerinRentre()) {
            this.plateau.allouerPiece(this.c0.pieceDetected);
            this.v1.piece = null;
            bool_piece_niveau_0 = false;
            this.niveau0.piece = null;
            this.c0.pieceDetected = null;
            this.c0.detectPiece = false;
          }
    }
    
    void pousserPieceNiveau1() {
        if(this.plateau.piece != null) {
          this.v3.sortirTige();
          this.v3.rentrerTige();
        }
        if((this.plateau.piece != null) && this.v3.isVerinRentre() && (this.plateau.piece.xPiece > (this.c1.xCapteur + this.c1.largeurCapteur))) {
          this.v3.piece = null;
          this.niveau1.allouerPiece(this.plateau.piece);
          this.plateau.desallouerPiece();
          bool_descendre = true;
          bool_monter_niveau_1 = false;
          bool_piece_niveau_1 = true;
        }
    }
    
    void pousserPieceNiveau2() {
        if(this.plateau.piece != null) {
          this.v3.sortirTige();
          this.v3.rentrerTige();
        }
        this.niveau2.allouerPiece(this.plateau.piece);
        if(this.v3.isVerinRentre() && (this.plateau.piece.xPiece > (this.c2.xCapteur + this.c2.largeurCapteur))) {
          this.v3.piece = null;
          this.niveau2.allouerPiece(this.plateau.piece);
          this.plateau.desallouerPiece();
          bool_descendre = true;
          bool_monter_niveau_2 = false;
          bool_piece_niveau_2 = true;
        }
    }
    
    Piece createPieceNiveau0() {
        Piece p = new Piece(this.niveau0.xTapis + 20, this.niveau0.yTapis - 60, 100, 60, color(#22B149));
        this.pieces.add(p);
        this.c0.setListPieces(this.pieces);
        this.c1.setListPieces(this.pieces);
        this.c2.setListPieces(this.pieces);
        bool_piece_niveau_0 = true;
        this.v1.piece = p;
        this.c0.pieceDetected = p;
        this.c0.detectPiece = true;
        this.niveau0.allouerPiece(p);
        return p;
    }
    
    void createPieceNiveau1() {
        Piece p = new Piece(this.niveau1.xTapis + 20, this.niveau1.yTapis - 60, 100, 60, color(#22B149));
        this.pieces.add(p);
        bool_piece_niveau_1 = true;
        this.niveau1.allouerPiece(p);
        this.c0.setListPieces(this.pieces);
        this.c1.setListPieces(this.pieces);
        this.c2.setListPieces(this.pieces);
        this.c1.pieceDetected = p;
        this.c1.detectPiece = true;
    }
    
    void createPieceNiveau2() {
        Piece p = new Piece(this.niveau2.xTapis + 20, this.niveau2.yTapis - 60, 100, 60, color(#22B149));
        this.pieces.add(p);
        bool_piece_niveau_2 = true;
        this.niveau2.allouerPiece(p);
        this.c0.setListPieces(this.pieces);
        this.c1.setListPieces(this.pieces);
        this.c2.setListPieces(this.pieces);
        this.c2.pieceDetected = p;
        this.c2.detectPiece = true;
    }
    
    // Simulation automatique 1 : aucune pièce au niveau 1 ni au niveau 2
    void simulCas1Auto() {
      Piece p = new Piece();
      if(!stop) {          // tant que la pièce créée n'est pas chargée au niveau 1
        bool_monter_niveau_1 = true;
        if(this.niveau0.piece == null && this.plateau.piece == null && this.niveau1.piece == null && !this.c0.detectPiece) {
          p = createPieceNiveau0();  // création de la pièce à charger
        }
        if(this.c0.detectPiece) {
          bool_piece_niveau_0 = true;
        }
        if(this.c20.detectPlateau) {
          bool_plateau_niveau_0 = true;
        }
        if(bool_piece_niveau_0 && bool_plateau_niveau_0 && !this.plateau.bool_possede_piece) {
          mettrePieceSurPlateau();
          if(this.v1.isVerinRentre()) {
            bool_piece_niveau_0 = false;
          }
        }
        if(bool_monter_niveau_1 && this.v1.isVerinRentre() && this.niveau1.piece == null) {
          monterNiveau1();
        }
        if(this.v21.isVerinSorti() && bool_plateau_niveau_1) {
          pousserPieceNiveau1();
        }
        if(bool_descendre) {
          descendreNiveau0();
        }
        if(this.niveau1.piece != null) {
          this.niveau1.run();
          if(this.niveau1.stop) { 
            stop = true;
            this.console.setText("chargement terminé", "green");
          }
        }
      }
    }
    
    // Simulation automatique 2 : une pièce est présente au niveau 1 mais pas au niveau 2
    void simulCas2Auto() {
      Piece p = new Piece();
      if(!stop) {
        if(this.niveau0.piece == null && this.niveau1.piece == null) {
          createPieceNiveau1();
          bool_piece_niveau_1 = true;
          p = createPieceNiveau0();
          bool_piece_niveau_0 = true;
        }
        if(this.c0.detectPiece) {
          bool_piece_niveau_0 = true;
          bool_piece_niveau_1 = true;
          bool_plateau_niveau_0 = true;
        }
        bool_monter_niveau_2 = true;
        if(bool_piece_niveau_0 && bool_plateau_niveau_0 && !this.plateau.bool_possede_piece) {
          mettrePieceSurPlateau();
          if(this.v1.isVerinSorti()) {
            this.v1.piece = null;
          }
        }
        if(bool_monter_niveau_2 && this.v1.isVerinRentre() && this.niveau2.piece == null) {
          monterNiveau2();
        }
        if(this.v21.isVerinSorti() && this.v22.isVerinSorti() && bool_plateau_niveau_2) {
          pousserPieceNiveau2();
        }
        if(bool_descendre) {
          descendreNiveau0();
        }
        if(this.niveau1.piece != null) {
          this.niveau1.run();
        }
        if(this.niveau2.piece != null) {
          this.niveau2.run();
          if(this.niveau2.stop && this.v21.isVerinRentre() && this.v22.isVerinRentre()){ 
            stop = true;
            this.console.setText("chargement terminé", "green");
          }
        }
      }
    }
    
    // Simulation automatique 3 : une pièce est présente au niveau 2 mais pas au niveau 1
    void simulCas3Auto() {
      Piece p = new Piece();
      if(!stop) {
        if(this.niveau0.piece == null && this.niveau2.piece == null) {
          createPieceNiveau2();
          bool_piece_niveau_2 = true;
          p = createPieceNiveau0();
          bool_piece_niveau_0 = true;
        }
        if(this.c0.detectPiece) {
          bool_piece_niveau_0 = true;
          bool_piece_niveau_2 = true;
          bool_plateau_niveau_0 = true;
        }
        bool_monter_niveau_1 = true;
        if(bool_piece_niveau_0 && bool_plateau_niveau_0 && !this.plateau.bool_possede_piece) {
          mettrePieceSurPlateau();
          if(this.v1.isVerinSorti()) {
            this.v1.piece = null;
          }
        }
        if(bool_monter_niveau_1 && this.v1.isVerinRentre() && this.niveau1.piece == null) {
          monterNiveau1();
        }
        if(this.v21.isVerinSorti() && bool_plateau_niveau_1) {
          pousserPieceNiveau1();
        }
        if(bool_descendre) {
          descendreNiveau0();
        }
        if(this.niveau1.piece != null) {
          this.niveau1.run();
          if(this.niveau1.stop && this.v21.isVerinRentre()) { 
            stop = true;
            this.console.setText("chargement terminé", "green");
          }
        }
        if(this.niveau2.piece != null) {
          this.niveau2.run();
        }
      }
    }
    
    // Simulation automatique 4 : une pièce est présente au niveau 1 et au niveau 2
    void simulCas4Auto() {
      Piece p = new Piece();
      if(!stop) {
        if(this.niveau0.piece == null && this.niveau1.piece == null && this.niveau2.piece == null) {
          createPieceNiveau1();
          createPieceNiveau2();
          bool_piece_niveau_1 = true;
          bool_piece_niveau_2 = true;
          p = createPieceNiveau0();
          bool_piece_niveau_0 = true;
        }
        if(this.c0.detectPiece) {
          bool_piece_niveau_0 = true;
          bool_piece_niveau_1 = true;
          bool_piece_niveau_2 = true;
          bool_plateau_niveau_0 = true;
        }
        bool_monter_niveau_1 = true;
        if(bool_piece_niveau_0 && bool_plateau_niveau_0 && !this.plateau.bool_possede_piece) {
          mettrePieceSurPlateau();
          if(this.v1.isVerinSorti()) {
            this.v1.piece = null;
          }
        }
        if(bool_monter_niveau_1 && this.v1.isVerinRentre() && this.plateau.piece != null) {
          monterNiveau1();
        }
        if(this.v21.isVerinSorti() && bool_plateau_niveau_1 && this.niveau1.piece == null) {
          pousserPieceNiveau1();
        }
        if(bool_descendre) {
          descendreNiveau0();
        }
        if(this.niveau1.piece != null) {
          this.niveau1.run();
          if(this.niveau1.stop) {
            this.niveau1.desallouerPiece();
            if(this.plateau.piece == null) {
              this.console.setText("chargement terminé", "green");
              stop = true;
            }
          } 
        }
        if(this.niveau2.piece != null) {
          this.niveau2.run();
        }
      }
    }
    
    void run() {
      if(this.modeFonctionnement.indexOf("a") == 0 || this.modeFonctionnement.indexOf("A") == 0) {
        //automatic();
        if(bool_cas1) {
          simulCas1Auto();
        }
        if(bool_cas2) {
          simulCas2Auto();
        }
        if(bool_cas3) {
          simulCas3Auto();
        }
        if(bool_cas4) {
          simulCas4Auto();
        }
      }
      if(this.modeFonctionnement.indexOf("m") == 0 || this.modeFonctionnement.indexOf("M") == 0) {
        manuel();
      }
      if(initSystem) {
        reinitialiserSysteme();
      }
    }
    
    void automatic() {
        if(start) {
           createPieceNiveau0();
           this.pieces.get(0).display();
           start = false;
        }
        if(!stop) {
          for(Piece piece : pieces) {
            if((piece.xPiece >= (this.niveau1.xTapis + this.niveau1.largeurTapis - 20)) && ((piece.yPiece + piece.hauteurPiece) == this.niveau1.yTapis) ||
               (piece.xPiece >= (this.niveau2.xTapis + this.niveau2.largeurTapis - 20)) && ((piece.yPiece + piece.hauteurPiece) == this.niveau2.yTapis)) {
              piece.animationChute();
              pieces.remove(piece);
            } else {
              piece.display();
            }
          } 
          if((this.plateau.yPlateau != this.c20.yCapteur) && position_init_plateau_plateau && !bool_monter_niveau_1 && !bool_monter_niveau_2 && !bool_descendre) {
          //if((this.plateau.yPlateau != this.c20.yCapteur) && position_init_plateau_plateau && !bool_descendre && bool_plateau_niveau_0) {
            //corrigerCoordonneePlateau();
          }
          // détection de la présence d'une pièce au niveau 0
          if(this.c0.detectPiece || (this.niveau0.piece != null)) {
            bool_piece_niveau_0 = true;
          }
          // détection de la présence d'une pièce au niveau 1
          if(this.niveau1.piece != null) {
            bool_piece_niveau_1 = true;
          } else bool_piece_niveau_1 = false;
          // détection de la présence d'une pièce au niveau 2
          if(this.niveau2.piece != null) {
            bool_piece_niveau_2 = true;
          } else bool_piece_niveau_2 = false;
          // détection du plateau au niveau 0
          if(this.c20.detectPlateau) {
          //if(this.v21.isVerinRentre() && this.v22.isVerinRentre()) {
            bool_plateau_niveau_0 = true;
            //bool_plateau_niveau_1 = false;
            //bool_plateau_niveau_2 = false;
          } //else bool_plateau_niveau_0 = false;
          // détection du plateau au niveau 1
          if(this.c21.detectPlateau) {
          //if(this.v21.isVerinSorti() && this.v22.isVerinRentre()) {
            bool_plateau_niveau_1 = true;
            //bool_plateau_niveau_0 = false;
            //bool_plateau_niveau_2 = false;
          } //else bool_plateau_niveau_1 = false;
          // détection du plateau au niveau 2
          if(this.c22.detectPlateau) {
          //if(this.v21.isVerinSorti() && this.v22.isVerinSorti()) {
            bool_plateau_niveau_2 = true;
            //bool_plateau_niveau_0 = false;
            //bool_plateau_niveau_1 = false;
          } //else  bool_plateau_niveau_2 = false;
          // si il y a une pièce au niveau 0 et le plateau est au niveau 0 alors on charge la pièce sur le plateau
          if(bool_piece_niveau_0 && bool_plateau_niveau_0 && !this.plateau.bool_possede_piece) {
            mettrePieceSurPlateau();
          }
          // si une Piece est présente au niveau 2 et qu'il n'y en a pas au niveau 1
          if((this.plateau.piece != null) && bool_plateau_niveau_0 && this.v1.isVerinRentre() && (this.niveau1.piece == null) && (this.niveau2.piece != null)) {
            bool_monter_niveau_1 = true;
            bool_monter_niveau_2 = false;
            // si il n'y a pas de Piece au niveau 1 ni au niveau 2
          } else if((this.plateau.piece != null) && bool_plateau_niveau_0 && this.v1.isVerinRentre() && (this.niveau1.piece == null) && (this.niveau2.piece == null)) {
                    bool_monter_niveau_1 = true;
                    bool_monter_niveau_2 = false;
            // si il y a une Piece au niveau 1 et une Piece au niveau 2
          } else if((this.plateau.piece != null) && bool_plateau_niveau_0 && this.v1.isVerinRentre() && (this.niveau1.piece != null) && (this.niveau2.piece != null)) {
                    bool_monter_niveau_1 = true;
                    bool_monter_niveau_2 = false;
           // si une Piece est présente au niveau 1
          } else if((this.plateau.piece != null) && bool_plateau_niveau_0 && this.v1.isVerinRentre() && (this.niveau2.piece == null) && (this.niveau1.piece != null) && !bool_monter_niveau_1) {
                    bool_monter_niveau_2 = true;
                    bool_monter_niveau_1 = false;
          }
          if(bool_monter_niveau_1) {
            monterNiveau1();
          }
          if(bool_monter_niveau_2) {
            monterNiveau2();
          }
          if(bool_plateau_niveau_1 && !bool_piece_niveau_1 && this.v21.isVerinSorti()) {
            pousserPieceNiveau1();
          }
          if(bool_plateau_niveau_2 && !bool_piece_niveau_2) {
            pousserPieceNiveau2();
          }
          if(this.niveau1.piece != null) {
            this.niveau1.run();
            if(this.niveau1.stop) {  // si la pièce est arrivée au bout du tapis
              bool_piece_niveau_1 = false;
              this.niveau1.desallouerPiece();
              nbPieces++;
            }
          }
          if(this.niveau2.piece != null) {
            this.niveau2.run();
            if(this.niveau2.stop) {  // si la pièce est arrivée au bout du tapis
              bool_piece_niveau_2 = false;
              this.niveau2.desallouerPiece();
              nbPieces++;
            }
          }
          if(bool_descendre) {
            descendreNiveau0();
          }
          // si on a dépassé le temps d'attente entre l'apparition de 2 pièces on ajoute une pièce
          if(((millis() - lastTime) > timeToWait) && !bool_piece_niveau_0) {
             createPieceNiveau0();
             lastTime = millis();
          }
          if(nbPieces == nbPiecesMax) {
            stop = true;
          }
        }
    }
    
    void manuel() {
        if(!stop) {
          if((this.c20.detectPlateau() && this.niveau0.piece == null) || (this.c20.detectPlateau() && this.niveau0.piece != null)) {
            this.console.setText("en attente d'une pièce...", "green");
          }
          if(this.plateau.piece != null && this.c20.detectPlateau()) {
            this.console.setText("pièce chargée sur le plateau", "green");
          }
          if(this.c21.detectPlateau()) {
            this.console.setText("plateau niveau 1", "green");
          }
          if(this.c22.detectPlateau()) {
            this.console.setText("plateau niveau 2", "green");
          }
          if(sortV1) {  // charger la pièce sur le plateau
            v1.sortirTige();
            if(this.v1.piece != null) {
              this.console.setText("chargement de la pièce sur le plateau ...", "green");
            }
            if(this.v1.isVerinSorti()) {
              this.plateau.allouerPiece(this.c0.pieceDetected);
              this.v1.piece = null;
              this.niveau0.piece = null;
            }
          } else {
            v1.rentrerTige();
            if(this.v1.isVerinRentre()) {
              this.plateau.allouerPiece(this.c0.pieceDetected);
              this.niveau0.desallouerPiece();
            }
          }
          if(sortV21) {  // monter le plateau au niveau 1
            v21.sortirTige();
            this.console.setText("montée du plateau au niveau 1", "green");
            this.v22.yBase = this.v21.yTige - this.v21.largeurTige;
            this.v22.yTige = this.v22.yBase - this.v22.largeurBase;
            this.plateau.yPlateau = this.v22.yTige - this.v22.largeurTige - this.plateau.hauteurPlateau;
            if(this.plateau.piece != null) {
              this.plateau.piece.yPiece = this.plateau.yPlateau - this.plateau.piece.hauteurPiece;
            }
          } else {
            v21.rentrerTige();
            this.v22.yBase = this.v21.yTige - this.v21.largeurTige;
            this.v22.yTige = this.v22.yBase - this.v22.largeurBase;
            this.plateau.yPlateau = this.v22.yTige - this.v22.largeurTige - this.plateau.hauteurPlateau;
          }
          if(sortV22) {  // monter le plateau au niveau 2
            v22.sortirTige();
            this.console.setText("montée du plateau au niveau 2", "green");
          } else {
            v22.rentrerTige();
          }
          if(sortV3) {  // pousser les plièces sur les différents niveaux 1 & 2
            v3.sortirTige();
            if(this.c21.detectPlateau()) {
              this.console.setText("chargement de la pièce au niveau 1", "green");
            } else if(this.c22.detectPlateau()) {
              this.console.setText("chargement de la pièce au niveau 2", "green");
            }
            if(this.v3.isVerinSorti()) {
              if(this.c21.detectPlateau()) {
                this.v3.piece = null;
                this.niveau1.allouerPiece(this.c1.pieceDetected);
                this.plateau.desallouerPiece();
              } else if(this.c22.detectPlateau()) {
                  this.v3.piece = null;
                  this.niveau2.allouerPiece(this.c2.pieceDetected);
                  this.plateau.desallouerPiece();
              }
            }
          } else {
            v3.rentrerTige();
          }
          if(this.niveau1.piece != null) {
            this.niveau1.run();
            this.console.setText("éjection de la pièce au niveau 1...", "green");
            if(this.niveau1.stop) {
              this.niveau1.desallouerPiece();
            }
          }
          if(this.niveau2.piece != null) {
            this.niveau2.run();
            this.console.setText("éjection de la pièce au niveau 2...", "green");
            if(this.niveau2.stop) {
              this.niveau2.desallouerPiece();
            }
          }
        }
    }
    
    void mouseClicked(int button) { 
      if(button == LEFT) {
        if(automatic.overButton()) {   
          this.setModeFonctionnement("automatic");
          start = true;
        }
        if(cas1Auto.overButton()) {
          this.setModeFonctionnement("automatic");
          reinitialiserSysteme();
          this.console.setText("simulation 1 : aucune pièces présente, chargement au niveau 1...", "green");
          bool_cas1 = true;
          bool_cas2 = false;
          bool_cas3 = false;
          bool_cas4 = false;
          stop = false;
        }
        if(cas2Auto.overButton()) {
          this.setModeFonctionnement("automatic");
          reinitialiserSysteme();
          this.console.setText("simulation 2 : 1 pièce présente au niveau 1, chargement au niveau 2...", "green");
          bool_cas1 = false;
          bool_cas2 = true;
          bool_cas3 = false;
          bool_cas4 = false;
          stop = false;
        }
        if(cas3Auto.overButton()) {
          this.setModeFonctionnement("automatic");
          reinitialiserSysteme();
          this.console.setText("simulation 3 : 1 pièce présente au niveau 2, chargement au niveau 1...", "green");
          bool_cas1 = false;
          bool_cas2 = false;
          bool_cas3 = true;
          bool_cas4 = false;
          stop = false;
        }
        if(cas4Auto.overButton()) {
          this.setModeFonctionnement("automatic");
          reinitialiserSysteme();
          this.console.setText("simulation 4 : 1 pièce présente au niveau 1 et au niveau 2, en attente au niveau 1...", "green");
          bool_cas1 = false;
          bool_cas2 = false;
          bool_cas3 = false;
          bool_cas4 = true;
          stop = false;
        }
        if (manuel.overButton()) {
          this.setModeFonctionnement("manuel");
          initSystem = true;
        }
        if("manuel".equals(this.modeFonctionnement)) {  
          if (newPiece.overButton()) {
              Piece p = createPieceNiveau0(); 
              this.pieces.add(p);  
              this.c0.setListPieces(this.pieces);
              this.c1.setListPieces(this.pieces);
              this.c2.setListPieces(this.pieces); 
              this.v1.piece = p;
              this.niveau0.allouerPiece(p);
              this.console.setText("pièce arrivée au niveau 0", "green");
          }
          if (rentrerTigeV1.overButton()) {
            v1.bool_rent = true;
            v1.bool_sort = false;
            sortV1 = false;
          }
          if (sortirTigeV1.overButton()) {
            v1.bool_rent = false;
            v1.bool_sort = true;
            sortV1 = true;
          }
          if (rentrerTigeV21.overButton()) {
            v21.bool_rent = true;
            v21.bool_sort = false;
            sortV21 = false;
          }
          if (sortirTigeV21.overButton()) {
            v21.bool_rent = false;
            v21.bool_sort = true;
            sortV21 = true;
          }
          if (rentrerTigeV22.overButton()) {
            v22.bool_rent = true;
            v22.bool_sort = false;
            sortV22 = false;
          }
          if (sortirTigeV22.overButton()) {
            v22.bool_rent = false;
            v22.bool_sort = true;
            sortV22 = true;
          }
          if (rentrerTigeV3.overButton()) {
            v3.bool_rent = true;
            v3.bool_sort = false;
            sortV3 = false;
          }
          if (sortirTigeV3.overButton()) {
            v3.bool_rent = false;
            v3.bool_sort = true;
            sortV3 = true;
          }
        }
      }
    }

   void mousePressed(int button) {
    if(button == LEFT && "manuel".equals(this.modeFonctionnement)) {
      if (sortirTigeV1P.overButton())
        sortV1 = true;
      if (sortirTigeV21P.overButton())
        sortV21 = true;
      if (sortirTigeV22P.overButton())
        sortV22 = true;
      if (sortirTigeV3P.overButton())
        sortV3 = true;
    }
    if(button == RIGHT) {
      if (sortirTigeV1.overButton()) {
        sortirTigeV1.drag = true;
      }
      if (sortirTigeV21.overButton()) {
        sortirTigeV21.drag = true;
      }
      if (sortirTigeV22.overButton()) {
        sortirTigeV22.drag = true;
      }
      if (sortirTigeV3.overButton()) {
        sortirTigeV3.drag = true;
      }
      if (rentrerTigeV1.overButton()) {
        rentrerTigeV1.drag = true;
      }
      if (rentrerTigeV21.overButton()) {
        rentrerTigeV21.drag = true;
      }
      if (rentrerTigeV22.overButton()) {
        rentrerTigeV22.drag = true;
      }
      if (rentrerTigeV3.overButton()) {
        rentrerTigeV3.drag = true;
      }
      if (sortirTigeV1P.overButton()) {
        sortirTigeV1P.drag = true;
      }
      if (sortirTigeV21P.overButton()) {
        sortirTigeV21P.drag = true;
      }
      if (sortirTigeV22P.overButton()) {
        sortirTigeV22P.drag = true;
      }
      if (sortirTigeV3P.overButton()) {
        sortirTigeV3P.drag = true;
      }
      if(marche.overButton()) {
        marche.drag = true;
      }
      if(arret.overButton()) {
        arret.drag = true;
      }
    }
  }

  void mouseReleased(int button) {
    if(button == RIGHT) {
      if (sortirTigeV1.overButton()) {
        sortirTigeV1.drag = false;
      }
      if (sortirTigeV21.overButton()) {
        sortirTigeV21.drag = false;
      }
      if (sortirTigeV22.overButton()) {
        sortirTigeV22.drag = false;
      }
      if (sortirTigeV3.overButton()) {
        sortirTigeV3.drag = false;
      }
      if (rentrerTigeV1.overButton()) {
        rentrerTigeV1.drag = false;
      }
      if (rentrerTigeV21.overButton()) {
        rentrerTigeV21.drag = false;
      }
      if (rentrerTigeV22.overButton()) {
        rentrerTigeV22.drag = false;
      }
      if (rentrerTigeV3.overButton()) {
        rentrerTigeV3.drag = false;
      }
      if (sortirTigeV1P.overButton()) {
        sortirTigeV1P.drag = false;
      }
      if (sortirTigeV21P.overButton()) {
        sortirTigeV21P.drag = false;
      }
      if (sortirTigeV22P.overButton()) {
        sortirTigeV22P.drag = false;
      }
      if (sortirTigeV3P.overButton()) {
        sortirTigeV3P.drag = false;
      }
      if(marche.overButton()) {
        marche.drag = false;
      }
      if(arret.overButton()) {
        arret.drag = false;
      }
    }
    if(button == LEFT) {
      if (sortirTigeV1P.overButton()) {
        sortV1 = false;
        v1.bool_rent = true;
      }
      if (sortirTigeV21P.overButton()) {
        sortV21 = false;
        v21.bool_rent = true;
      }
      if (sortirTigeV22P.overButton()) {
        sortV22 = false;
        v22.bool_rent = true;
      }
      if (sortirTigeV3P.overButton()) {
        sortV3 = false;
        v3.bool_rent = true;
      }
    }
  }
}
