
class Console {
  
  int x;
  int y;
  int largeur;
  int hauteur;
  
  String text;
  color colorText;
  
  
  Console(int _x, int _y, int _largeur, int _hauteur) {
    this.x = _x;
    this.y = _y;
    this.largeur = _largeur;
    this.hauteur = _hauteur; 
  }

  void setText(String _text, String colorText) {
    this.text = _text;
    this.setColorText(colorText);
  }
  
  void setColorText(String colorText) {
    if(colorText.equals("red")) {
      this.colorText = color(200, 20, 20);
    }
    if(colorText.equals("yellow")) {
      this.colorText = color(200, 200, 20);
    }
    if(colorText.equals("green")) {
      this.colorText = color(20, 190, 20);
    }
  }

  void display() {
    stroke(0, 0, 0);
    fill(0);
    rect(this.x, this.y, this.largeur, this.hauteur);
    if(this.text != null) {
      noStroke();
      fill(colorText);
      textSize(10);
      text(this.text, this.x+10, this.y+20); 
    }
  } 
}
