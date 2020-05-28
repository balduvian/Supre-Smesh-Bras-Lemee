#version 330 core

uniform sampler2D stars;

uniform vec4 in_color;

in vec2 back_tex_pass;
in vec2 front_tex_pass;

out vec4 color;

void main()
{
    color = texture(stars, back_tex_pass) * 0.5 + texture(stars, front_tex_pass) * 0.75;
}
