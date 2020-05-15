#version 330

in vec2 passCoords_of_Texture;
in vec4 ic;
in vec4 light_col;
in float intensity;
in float attenuation;
in float point_amount_of_light;
in float sun_amount_of_light;
out vec4 fragColor;

uniform sampler2D tex;

void main(void)
{
//    fragColor =  vec4(texture(tex,passCoords_of_Texture)*0.7 + (texture(tex,passCoords_of_Texture)* kek * vec4(1,1,1,1)));
//    fragColor =  vec4(texture(tex,passCoords_of_Texture))*light_col*intensity*(sun_amount_of_light + point_amount_of_light*attenuation);
    fragColor =  vec4(texture(tex,passCoords_of_Texture));
}