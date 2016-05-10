
class Interface {
 
  int xInterface;
  int yInterface;
  int largeurInterface;
  int hauteurInterface;
  
  Console console;
  ArrayList<BlocInterface> blocs;
  
  Interface(int x, int y, int largeur, int hauteur) {
    this.xInterface = x;
    this.yInterface = y;
    this.largeurInterface = largeur;
    this.hauteurInterface = hauteur;
    this.blocs = new ArrayList<BlocInterface>();
  }
  
  void addBlocInterface(BlocInterface bloc) {
    this.blocs.add(bloc);
  }
  
  void removeBlocInterface(String name) {
    for(BlocInterface bloc : blocs) {
      if(bloc.getName().equals(name)) {
        blocs.remove(bloc);
        break;
      }
    }
  }
  
  void setConsole(Console console) {
    this.console = console;
  }
  
  void display() {
    if(this.console != null) {
      this.console.display();
    }
    if(this.blocs != null) {
      for(BlocInterface bloc : blocs) {
        bloc.display();
      }
    }
  }
}
