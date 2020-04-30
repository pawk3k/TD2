
#version 330

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 textureCoords;
out vec2 passCoords_of_Texture;
uniform mat4 M;
uniform mat4 P;
uniform mat4 V;

out vec3 modelColor;

void main()
{
    gl_Position =   P * V * M * vec4(aPos.xyz, 1.0);
    passCoords_of_Texture = textureCoords;
}