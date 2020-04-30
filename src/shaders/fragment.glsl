#version 330

in vec2 passCoords_of_Texture;

out vec4 fragColor;

uniform sampler2D tex;

void main(void)
{
    fragColor = texture(tex,passCoords_of_Texture);

}