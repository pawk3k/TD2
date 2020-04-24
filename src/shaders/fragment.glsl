#version 130

out vec4 fragColor;
uniform vec3 myColor;

void main(void)
{
    fragColor = vec4( myColor, 1.0);
}
