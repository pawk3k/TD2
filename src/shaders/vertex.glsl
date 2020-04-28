#version 130

in vec3 position;
in vec2 textureCoords;
out vec2 passCoords_of_Texture;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;
uniform mat4 viewMatrix;

void main()
{
    gl_Position =  projectionMatrix  * viewMatrix * worldMatrix  *vec4(position, 1.0);
    passCoords_of_Texture = textureCoords;
}