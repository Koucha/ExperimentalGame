#version 330 core

layout(location = 0) in vec3 vertexPosition;

uniform mat4 PVM;

out vec4 fragmentColor;

void main()
{
    gl_Position = PVM * vec4(vertexPosition, 1);
}