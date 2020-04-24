#version 130

in vec4 position;
uniform mat4 M;

void main()
{
    gl_Position =  vec4( position * M );
}
