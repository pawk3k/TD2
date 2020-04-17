#version 130
out vec4 fragColor;
in vec3 inColors;
void main(void)
{
    fragColor = vec4(inColors, 1.0);

}