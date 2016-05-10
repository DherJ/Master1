
class Trou {
  float xTrou;    // coordonnées x du trou
  float yTrou;    // coordonnées y du trou
  float widthTrou;  // largeur du trou
  float heightTrou;  // hauteur du trou

  // Constructeur par défaut avec coordonnées et dimension
  Trou(float _x, float _y, float _width, float _height) {
    xTrou = _x;
    yTrou = _y;
    widthTrou = _width;
    heightTrou = _height;
  }
  
  // Affiche le trou
  void display() {                                
    noStroke();
    fill(255, 255, 255);
    rect(xTrou, yTrou, widthTrou, heightTrou);
  }

}
