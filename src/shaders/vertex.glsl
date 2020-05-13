#version 330

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 textureCoords;
layout (location = 2) in vec3 normal;
out vec2 passCoords_of_Texture;
uniform mat4 M;
uniform mat4 P;
uniform mat4 V;

struct Light{
    float intensity;
    vec4 color;
    vec4 position;
};
uniform Light my_l;

out vec4 light_col;
out float kek;
out vec3 modelColor;
out vec4 ic;
void main()
{
    vec4 lp = my_l.position;
//    vec4 lp = vec4(10,-6,2,0);

    vec4 to_light = normalize(V * lp - V * M * vec4(aPos.xyz,1.0));    //Vector to light
//    vec3 dir = -lp.xyz;
//    vec4 to_light = normalize(vec4(dir,0));
    vec4 n = normalize(V * M * vec4(normal.xyz,1.0)); //Normalized normal vector


    float amount_of_light = clamp(dot(n,to_light),0.2,1);// Amount of light for certein point
    light_col = my_l.color;
    ic = vec4(1.0);
    kek = amount_of_light;
    gl_Position =   P * V * M * vec4(aPos.xyz, 1.0);
    passCoords_of_Texture = textureCoords;
}