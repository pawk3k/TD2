#version 330 core

in vec3 modelColor;
out vec4 FragColor;

void main()
{
   FragColor = vec4(modelColor, 1.0f);
}