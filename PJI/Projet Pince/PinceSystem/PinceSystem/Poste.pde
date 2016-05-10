
class Poste {
  Button button;
  Capteur capteur;
  Tapis tapis;
  int numPoste;

  Poste (Button _button, Capteur _capteur, Tapis _tapis) {
    this.button = _button;
    this.capteur = _capteur;
    this.tapis = _tapis;
  }
  
  void setNumPoste(int numPoste){
    this.numPoste = numPoste;
  }
  
}

