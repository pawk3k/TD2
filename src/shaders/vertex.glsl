#version 130

in vec3 position;

in vec3 colors;
out vec3 inColors;

uniform mat4 projectionMatrix;

void main()
{
    gl_Position = projectionMatrix  * vec4(position, 1.0);
    inColors = vec3(position.x+0.5,0.0,position.y+0.5);
}