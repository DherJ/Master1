#version 130


in vec4 fColor;
in vec2 fTexCoord;

out vec4 fragColor;

uniform sampler2D texture;
uniform float coeff;

void main() {
    //fragColor=fColor;
    //fragColor = texture2D(texture, fTexCoord);
    //fragColor=texture2D(texture,fTexCoord)*fColor;
    // coeff = uniform passée par l'application (questions précédentes).
    fragColor=texture2D(texture,fTexCoord)*fColor.b;
    fragColor.g*=(1.0-coeff);
}
