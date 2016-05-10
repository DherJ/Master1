//#version 130

//in vec3 position;
//in vec3 color;

//out vec3 fColor;

//void main() {
//  vec4 clipPosition=vec4(position,1);

//  fColor=color;

//  gl_Position=clipPosition;
//}

#version 130

in vec3 position; // Eye Coordinates
//in vec3 color;
in vec3 normal;
out vec3 fColor;
uniform mat4 projection;
uniform mat4 transform;
uniform vec3 lightPosition;

void main() {
    //vec4 eyePosition=vec4(position,1); // passage en coordonnées homogènes
    //vec4 clipPosition=projection*eyePosition; // transformation par la matrice de projection
    vec4 eyePosition=vec4(position,1);
    vec4 clipPosition=projection*transform*eyePosition;
    vec3 N, L;
    L=lightPosition-position;
    N=normal;

    N=normalize(N);
    L=normalize(L);
    float intensity = max(dot(N,L),0.0);

    //fColor=color;
    //fColor=normal;
    fColor=vec3(intensity,intensity,intensity);
    gl_Position=clipPosition; // gl_Position doit être donné en clip coordinates
}
