//#version 120

uniform mat4 uMVPMatrix;

attribute vec4 vPosition;
attribute vec4 vColor;

varying vec4 varyingColor;

void main() {
    // The matrix must be included as a modifier of gl_Position.
    // Note that the uMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
    gl_Position = uMVPMatrix * vPosition;
    varyingColor = vColor;
}