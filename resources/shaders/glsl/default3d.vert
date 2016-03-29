#version 330 core

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec3 vertexNormal;

uniform mat4 PVM;
uniform mat4 NORM;

out vec4 fragmentNormal;

void main()
{
    fragmentNormal = normalize(NORM * vec4(vertexNormal, 0));
    gl_Position = PVM * vec4(vertexPosition, 1);
}