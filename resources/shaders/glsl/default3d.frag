#version 330 core

in vec4 fragmentNormal;

uniform vec4 COLOR;

out vec4 color;

void main()
{
    color = COLOR * ( 0.55 + 0.45 * ( 0.8 * fragmentNormal.x * fragmentNormal.x * fragmentNormal.x + 0.2 * fragmentNormal.y ) );
}