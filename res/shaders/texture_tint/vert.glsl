#version 330 core

layout (location = 0) in vec3 vertex;
layout (location = 1) in vec2 tex_coord;

uniform mat4 model;
uniform mat4 projection;

out vec2 tex_pass;

void main()
{
    tex_pass = tex_coord;
    gl_Position = (projection * model) * vec4(vertex, 1);
}
