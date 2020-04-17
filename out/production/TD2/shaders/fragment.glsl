#version 130
out vec4 fragColor;
in vec3 position;
void main()
{
    fragColor = vec4(vec3(position), 1.0);
}