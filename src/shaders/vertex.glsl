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
uniform Light point_sun;
uniform Light global_sun;

out vec4 light_col;
out float point_amount_of_light;
out float sun_amount_of_light;
out float intensity;
out vec3 modelColor;
out float attenuation ;
out vec4 ic;
void main()
{

    // Lp is light point that has attenuatiom
    vec4 lp = point_sun.position;

    vec4 from_light = lp - vec4(aPos.xyz,1.0);
    vec3 dir = -lp.xyz;

    float len = length(from_light);

    attenuation = clamp(10.0/len,0.0,1.0);
//    attenuation = 1;

    vec4 to_light_p = normalize(V * lp - V * M * vec4(aPos.xyz,1.0));    //Vector to light point
    vec4 to_light_g = normalize(V * vec4(dir.xyz,1.0) - V * M * vec4(aPos.xyz,1.0));    //Vector to light global sun

    vec4 n = normalize(V * M * vec4(normal.xyz,1.0)); //Normalized normal vector

    float p_amount_of_light = clamp(dot(n,to_light_p),0.2,1);// Amount of light for certein point
    float g_amount_of_light = clamp(dot(n,to_light_g),0.2,1);// Amount of light for global sun

    light_col = point_sun.color;
    intensity = point_sun.intensity;

    ic = vec4(1.0);
    point_amount_of_light = p_amount_of_light;
    sun_amount_of_light = g_amount_of_light;
    gl_Position =   P * V * M * vec4(aPos.xyz, 1.0);
    passCoords_of_Texture = textureCoords;
}