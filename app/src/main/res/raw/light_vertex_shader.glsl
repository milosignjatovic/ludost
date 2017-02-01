// http://stackoverflow.com/questions/29505901/diffuse-shader-for-opengl-es-2-0-light-changes-with-camera-movement-vuforia-on

//#version 120
//
//void main() {
//
//}

attribute vec4 vertexPosition;
attribute vec3 vertexNormal;

uniform mat4 modelViewProjectionMatrix;
uniform mat4 modelViewMatrix;
uniform mat4 normalMatrix;

// lighting
uniform vec4 uLightPosition;
uniform vec3 uLightColor;

// material
uniform vec3 uMatAmbient;
uniform vec3 uMatDiffuse;

// pass to fragment shader
varying vec3 vNormalEyespace;
varying vec3 vVertexEyespace;
varying vec4 vLightPositionEyespace;
varying vec3 vNormal;
varying vec4 vVertex;

void main() {
    // we can just take vec3() of a vec4 and it will take the first 3 entries
    vNormalEyespace = vec3(normalMatrix * vec4(vertexNormal, 1.0));
    vNormal = vertexNormal;
    vVertexEyespace = vec3(modelViewMatrix * vertexPosition);
    vVertex = vertexPosition;

    // light position
    vLightPositionEyespace = modelViewMatrix * uLightPosition;

    gl_Position = modelViewProjectionMatrix * vertexPosition;
}
