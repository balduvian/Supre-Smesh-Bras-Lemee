#version 330 core

layout (location = 0) in vec3 vertex;
layout (location = 1) in vec2 tex_coord;

uniform mat4 model;
uniform mat4 projection;

uniform vec4 back_tex_modif;
uniform vec4 front_tex_modif;

out vec2 back_tex_pass;
out vec2 front_tex_pass;

void main()
{
    back_tex_pass = (tex_coord * back_tex_modif.xy) + back_tex_modif.zw;

    front_tex_pass = (tex_coord * front_tex_modif.xy) + front_tex_modif.zw;

    gl_Position = (projection * model) * vec4(vertex, 1);
}
