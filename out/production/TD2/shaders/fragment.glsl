#version 130

in vec3 myPos;
out vec4 fragColor;

void main(void)
{
    fragColor = vec4( normalize(myPos).y > 0.1f ? vec3(0.0f, 0.0f, 0.0f) : vec3(0.0f, 1.0f, 0.93f), 1.0);
}
