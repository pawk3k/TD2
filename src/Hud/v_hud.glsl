#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoords;
layout (location = 2) in vec3 normal;

out vec2 outTexCoord;

uniform mat4 M;
uniform mat4 P;
uniform mat4 V;
void main()
{
    gl_Position =  P * M * vec4(position.xz,position.y, 1.0);
    outTexCoord = textureCoords;
}
