#version 330

in vec2 passCoords_of_Texture;
in vec4 ic;
in float kek;
out vec4 fragColor;

uniform sampler2D tex;

void main(void)
{
//    fragColor =  vec4(texture(tex,passCoords_of_Texture)*0.7 + (texture(tex,passCoords_of_Texture)* kek * vec4(1,1,1,1)));
    fragColor =  vec4(texture(tex,passCoords_of_Texture))*kek;
}