#version 330

in vec2 passCoords_of_Texture;
in vec4 ic;
in float kek;
out vec4 fragColor;

uniform sampler2D tex;

void main(void)
{
    fragColor =  texture(tex,passCoords_of_Texture)*0.2 + texture(tex,passCoords_of_Texture)*kek;
}