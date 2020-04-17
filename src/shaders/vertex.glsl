#version 130

in vec3 position;
out vec3 postion;
void main()
{
    gl_Position = vec4(position, 1.0);
}