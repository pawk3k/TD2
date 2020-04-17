#version 130

in vec3 position;
uniform mat4 projectionMatrix;

in vec3 colors;
out vec3 inColors;
void main()
{
    gl_Position =  vec4(position, 1.0);
    inColors = vec3(position.x+0.5,0.0,position.y+0.5);
}