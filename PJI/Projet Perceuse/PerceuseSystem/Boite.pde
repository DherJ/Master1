
class Boite {
  float xBoite;        // coordonnées en X pour la boite
  float yBoite;        // coordonnées en Y pour la boite
  float largeurBoite;  // largeur de la boite
  float hauteurBoite;  // hauteur de la boite
  color couleurBoite;  // couleur de la boite

  Trou trou_perceuse;   // trou engendré par la perceuse
  Trou trou_fraiseuse;  // trou engendré par la fraiseuse
  
  boolean trouFraiseuse;  // true si trou créé grâce à la fraiseuse, 0 sinon
  boolean trouPerceuse;   // true si trou créé grâce à la perceuse, 0 sinon


  // Constructeur par défaut 
  Boite (float _xBoite, float _yBoite, float _largeurBoite, float _hauteurBoite, 
  color _couleurBoite, float _vitDeplBoite) {
    xBoite = _xBoite;
    yBoite = _yBoite;
    largeurBoite = _largeurBoite;
    hauteurBoite = _hauteurBoite;
    couleurBoite = _couleurBoite;
    
    trouFraiseuse = false;
    trouPerceuse = false;
  }

  // initialise les trous dans la boite  
  void initBoite() {
    this.trou_perceuse = new Trou(0, 0, 0, 0);
    this.trou_fraiseuse = new Trou(0, 0, 0, 0);
    
    this.trouFraiseuse = false;
    this.trouPerceuse = false;
  }

  // Dessine la boite avec ou sans les trous
  void display() {        
    noStroke();
    fill(couleurBoite);
    rect(xBoite, yBoite, largeurBoite, hauteurBoite);    

    if (this.trouPerceuse) {
      fill(255, 255, 255);
      //rect(475.0, 276.0, -15.0, 8.0);
      this.trou_perceuse.display();
    }

    if (this.trouFraiseuse) {
      fill(255, 255, 255);
      //rect(375, 250, 50, 10);
      this.trou_fraiseuse.display();
    }
  }
}

