//#version 120

uniform mat4 uMVPMatrix;

uniform highp mat4 u_ModelViewMatrix;
uniform highp mat4 u_ProjectionMatrix;

attribute vec4 vPosition;
attribute vec4 vColor;

attribute vec4 a_Position;
attribute vec2 a_TexCoord;
attribute vec3 a_Normal;

varying vec4 varyingColor;

varying lowp vec4 frag_Color;
varying lowp vec2 frag_TexCoord;
varying lowp vec3 frag_Normal;
varying lowp vec3 frag_Position;

void main() {
    // The matrix must be included as a modifier of gl_Position.
    // Note that the uMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
    gl_Position = uMVPMatrix * vPosition;

    //frag_TexCoord = a_TexCoord;
    frag_Position = vec3(gl_Position);
    frag_Color = vColor;
    frag_Normal = vec3(uMVPMatrix * vec4(a_Normal, 1.0));

}