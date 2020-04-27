#version 130

in vec4 position;
uniform mat4 M;

out vec3 myPos;
void main()
{
    gl_Position =  vec4( position * M );
    myPos = position.xyz;
}
