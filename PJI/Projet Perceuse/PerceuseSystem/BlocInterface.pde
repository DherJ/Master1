
class BlocInterface {
  
  int xBloc;
  int yBloc;
  int largeurBloc;
  int hauteurBloc;
  
  String name;
  String text;
  color couleurBG;
  color couleurText;
  
  BlocInterface(String _name, int x, int y, int largeur, int hauteur) {
    this.name = _name;
    this.xBloc = x;
    this.yBloc = y;
    this.largeurBloc = largeur;
    this.hauteurBloc = hauteur;
  }
  
  String getName() {
    return this.name;
  }
  
  void setText(String _text) {
    this.text = _text;
  }
  
  void setColorBG(color _couleur) {
    this.couleurBG = _couleur;
  }
  
  void setColorText(color _couleur) {
    this.couleurText = _couleur;
  }
  
  void display() {
    fill(couleurBG);
    stroke(0, 0, 0);
    rect(xBloc, yBloc, largeurBloc, hauteurBloc);
    fill(couleurText);
    text(this.text, this.xBloc+10, this.yBloc+20);
  }
}
