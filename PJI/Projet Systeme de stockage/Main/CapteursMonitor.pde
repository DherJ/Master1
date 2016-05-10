
class CapteursMonitor {
  
  int x;
  int y;
  
  ArrayList<Capteur> capteurs;
  
  int espaceY = 0;
  
  CapteursMonitor(int _x, int _y) {
    this.x = _x;
    this.y = _y;
    this.capteurs = new ArrayList<Capteur>();
  }
  
  void addCapteur(Capteur _capteur) {
    this.capteurs.add(_capteur);
  }
  
  void removeCapteur(String nameCapteur) {
    for(Capteur capteur : capteurs) {
      if(capteur.getName().equals(nameCapteur)) {
        capteurs.remove(capteur);
        break;
      }
    }
  }
  
  void display() {
    for(Capteur capteur : capteurs) {
      fill(0, 0, 0);
      text(capteur.getName(), this.x, this.y + espaceY);
      if(capteur.detectPiece() || capteur.detectPlateau()) {
        fill(0, 255, 0);
      }
      else fill(255, 0, 0);
      rect(this.x + 30, this.y + espaceY - 15, 20, 20);
      if(capteur.detectPiece()) {
              fill(0, 0, 0);
              text("Détection pièce", this.x + 60, this.y + espaceY);
      } else if(capteur.detectPlateau()) {
              fill(0, 0, 0);
              text("Détection plateau", this.x + 60, this.y + espaceY);
      }
      espaceY += 30;
    }
    espaceY = 0;
  }
}
