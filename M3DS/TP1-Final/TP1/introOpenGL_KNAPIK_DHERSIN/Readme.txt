KNAPIK Christopher
DHERSIN Jérôme

Question 7 :
Le rôle d'un VBO est un ensemble de données utilisées pour réaliser les fonctions liées au graphisme. 
Le rôle d'un VAO est d'indiquer quelles seront les valeurs des attributs du vertex shader actif lors du tracé : il fait le lien entre les buffers et les attributs des shaders. Il fait le lien entre les attributs et le VBO.
Le rôle d'un Program Shader est composé du Fragment Shader et du Vertex Shader.

Question 10 :
  _trianglePosition = {
      -0.8,-0.5,0.0, // vertex 0
      -0.2,-0.5,0.0, // 1
      -0.5,0.5,0.0,  // 2

      0.2,0.5,0.0,  // 3
      0.8,0.5,0.0,  // 4
      0.5,-0.5,0.0  // 5
   };

   _trianglePosition = {
     -0.8,-0.5,0.0, // vertex 0 anciennement vertex 0
     0.8,0.5,0.0, // 1 anciennement 4
     -0.5,0.5,0.0, // 2 anciennement 2
     
     -0.2,-0.5,0.0, // 3 anciennement 1
     0.5,-0.5,0.0, // 4 anciennement 5
     0.2,0.5,0.0    // 5 anciennement 3
    };

Pour récupérer nos 2 triangles précédents, il faut tracer le sommet 0 3 2 et 5 1 4 

Question 12 :
Les triangles sont identiques mais les couleurs ont changé. Il faut faire la même chose avec les couleurs que pour les positions des sommets.

Question 13 :
Les couleurs sont différentes pour le sommet commun des triangles.
On ne peut pas dissocier le sommet et sa couleur donc la couleur est identique étant donné que le sommet est unique.


Doit contenir :
- ce que vous n'avez pas fait (et pourquoi).
- difficultés rencontrées.
- commentaires éventuels sur le TP (points à éclaircir, longueur du sujet, etc). 

