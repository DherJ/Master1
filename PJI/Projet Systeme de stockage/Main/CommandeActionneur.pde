
class CommandeActionneur {
  
  String nameActionneur;
  int textSize;
  int x;
  int y;
  Button activer;
  Button arreter;
  
  color colorText;
  
  float xButtonArreter = 0;
  float xButtonActiver = 0;
  
  CommandeActionneur(String _nameActionneur, color _colorText, int _textSize) {
    this.nameActionneur = _nameActionneur;
    this.colorText = _colorText;
    this.textSize = _textSize;
  }
  
  String getName() {
    return this.nameActionneur;
  }
  
  void setX(int _x) {
    this.x = _x;
  }
  
  void setY(int _y) {
    this.y = _y;
  }
  
  void setXButtonArreter(float _x) {
    this.arreter.x = _x;
  }
  
  void setYButtonArreter(float _y) {
    this.arreter.y = _y;
  }
  
  void setXButtonActiver(float _x) {
    this.activer.x = _x;
  }
  
  void setYButtonActiver(float _y) {
    this.activer.y = _y;
  }
  
  void setColorText(color _colorText) {
    this.colorText = _colorText;
  }
  
  void setButtonArreter(Button _arreter) {
    this.arreter = _arreter;
  }
  
  void setButtonActiver(Button _activer) {
    this.activer = _activer;
  }
  
  void display() {
    if(this.arreter != null && this.activer != null) {
        fill(this.colorText);
        textSize(textSize);
        text(this.nameActionneur, this.x, this.y);
        xButtonArreter = this.x + 20;
        setXButtonArreter(xButtonArreter);
        setYButtonArreter(this.y - 13);
        this.arreter.display();
        xButtonActiver = this.arreter.x + this.arreter.largeur + 20;
        setXButtonActiver(xButtonActiver);
        setYButtonActiver(this.y - 13);
        this.activer.display();
    } else if(this.arreter == null) {
              fill(this.colorText);
              textSize(textSize);
              text(this.nameActionneur, this.x, this.y);
              xButtonActiver = this.x + 20;
              setXButtonActiver(xButtonActiver);
              setYButtonActiver(this.y - 13);
              this.activer.display();
     } else if(this.activer == null) {
                      fill(this.colorText);
                      textSize(textSize);
                      text(this.nameActionneur, this.x, this.y);
                      xButtonArreter = this.x + 20;
                      setXButtonArreter(xButtonArreter);
                      setYButtonArreter(this.y - 13);
                      this.arreter.display();
     }
    }
}
