
class Slider {
 
    int largeur, hauteur;    // width and height of bar
    float xSlider, ySlider;       // x and y position of bar
    float xCursor, newXCursor;    // x position of slider
    float xCursorMin, xCursorMax; // max and min values of slider
    int loose;                      // how loose/heavy
    boolean overSliderSlider;           // is the mouse overSliderSlider the slider?
    boolean locked;
    float ratio;
  
    String name;
    float value;
    float minValue;
    float maxValue;
    
    color sliderBGColor;
    color cursorColor;
   
    Slider (String _name, float _xSlider, float _ySlider, int _largeur, int _hauteur, int l) {
      this.name = _name;
      this.largeur = _largeur;
      this.hauteur = _hauteur;
      int widthtoheight = _largeur - _hauteur;
      //this.ratio = (float)_largeur / (float)widthtoheight;
      this.xSlider = _xSlider;
      this.ySlider = _ySlider - this.hauteur/2;
      this.xCursor = this.xSlider + this.largeur/2 - this.hauteur/2;
      this.newXCursor = this.xCursor;
      this.xCursorMin = this.xSlider;
      this.xCursorMax = this.xSlider + this.largeur - this.hauteur;
      this.loose = l;
   }
   
   void setMinValue(float value) {
     this.minValue = value;
   }
   
   void setMaxValue(float value) {
     this.maxValue = value;
   }
   
   void setSliderBGColor(color _color) {
     this.sliderBGColor = _color;
   }
   
   void setCursorColor(color _color) {
     this.cursorColor = _color;
   }
   
   void setDefaultValue(float _value) {
     this.value = _value;
   }
   
   void update() {
     if(this.xCursor == this.xSlider) {
       this.value = this.minValue;
     } else if((this.xCursor + this.hauteur) == (this.xSlider + this.largeur)) {
       this.value = this.maxValue;
     } else {
       float scale = (maxValue - minValue) / (xSlider + largeur);
       this.value = scale * (xCursor + xSlider);
     }
    if (overSliderSliderEvent()) {
      overSliderSlider = true;
    } else {
      overSliderSlider = false;
    }
    if (mousePressed && overSliderSlider) {
      locked = true;
    }
    if (!mousePressed) {
      locked = false;
    }
    if (locked) {
      newXCursor = constrain(mouseX-hauteur/2, xCursorMin, xCursorMax);
    }
    if (abs(newXCursor - xCursor) > 1) {
      xCursor = xCursor + (newXCursor-xCursor)/loose;
    }
  }
  
  float constrain(float val, float minv, float maxv) {
    return min(max(val, minv), maxv);
  }

  boolean overSliderSliderEvent() {
    if (mouseX > xSlider && mouseX < xSlider+largeur &&
       mouseY > ySlider && mouseY < ySlider+hauteur) {
      return true;
    } else {
      return false;
    }
  }

  void display() {
    this.update();
    text(this.name, this.xSlider + this.largeur + 10, this.ySlider + (this.hauteur / 2) + 3);
    text(this.getValue(), this.xCursor, this.ySlider + this.hauteur + 10);
    noStroke();
    fill(this.sliderBGColor);
    rect(xSlider, ySlider, largeur, hauteur);
    if (overSliderSlider || locked) {
      fill(0, 0, 0);
    } else {
      fill(this.cursorColor);
    }
    rect(xCursor, ySlider, hauteur, hauteur);
  }

  float getValue() {
    return this.value; 
  }
}
