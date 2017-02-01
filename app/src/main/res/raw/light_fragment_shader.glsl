// http://stackoverflow.com/questions/29505901/diffuse-shader-for-opengl-es-2-0-light-changes-with-camera-movement-vuforia-on
//#version 120
//
//void main() {
//
//}

precision highp float; //apparently necessary to force same precision as in vertex shader

//lighting
uniform vec4 uLightPosition;
uniform vec3 uLightColor;

//material
uniform vec3 uMatAmbient;
uniform vec3 uMatDiffuse;

//from vertex shader
varying vec3 vNormalEyespace;
varying vec3 vVertexEyespace;
varying vec4 vLightPositionEyespace;
varying vec3 vNormal;
varying vec4 vVertex;

void main() {
    vec3 normalModel = normalize(vNormal);
    vec3 normalEyespace = normalize(vNormalEyespace);
    vec3 lightDirectionModel = normalize(uLightPosition.xyz - vVertex.xyz);
    vec3 lightDirectionEyespace = normalize(vLightPositionEyespace.xyz - vVertexEyespace.xyz);

    vec3 ambientTerm = uMatAmbient;
    vec3 diffuseTerm = uMatDiffuse * uLightColor;
    // calculate the lambert factor via cosine law
    float diffuseLambert = max(dot(normalEyespace, lightDirectionEyespace), 0.0);
    // Attenuate the light based on distance.
    float distance = length(vLightPositionEyespace.xyz - vVertexEyespace.xyz);
    float diffuseLambertAttenuated = diffuseLambert * (1.0 / (1.0 + (0.01 * distance * distance)));

    diffuseTerm = diffuseLambertAttenuated * diffuseTerm;

    gl_FragColor = vec4(ambientTerm + diffuseTerm, 1.0);
}
