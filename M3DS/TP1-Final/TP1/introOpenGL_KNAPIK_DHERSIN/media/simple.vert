#version 130


in vec3 position;
in vec4 color;
in vec2 texCoord;

uniform float coeff;

out vec4 fColor;
out vec2 fTexCoord;

void main() {
    fColor = color;
    vec3 newPosition = position*coeff;
    fTexCoord=texCoord;
    gl_Position=vec4(newPosition,1.0);
}
