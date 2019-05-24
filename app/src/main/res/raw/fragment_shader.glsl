//#version 120
// Fragment shader program
precision mediump int;
precision mediump float;

// Data coming from the vertex shader
uniform vec3 light_Position;

varying vec3 frag_Position;
varying vec4 frag_Color;
varying vec3 frag_Normal;
varying lowp vec2 frag_TexCoord;

uniform sampler2D texture_sampler;

void main() {

    // x, y z(light height)
    vec3 light_position = vec3(0.0, -6.0, 24.0); // should be uniform, from app
    vec3 lightDirection;
    vec3 vertex_normal;
    float cos_angle;


    // Calculate a vector from the fragment location to the light source
    lightDirection = normalize(light_position - frag_Position);

    // The vertex's normal vector is being interpolated across the primitive
    // which can make it un-normalized. So normalize the vertex's normal vector.
    vertex_normal = normalize(frag_Normal);

    // Calculate the cosine of the angle between the vertex's normal vector
    // and the vector going to the light.
    float diffuseAngle = clamp(dot(vertex_normal, lightDirection), 0.4, 1.0);

    // Scale the color of this fragment based on its angle to the light.
    vec4 myColor = frag_Color;
    gl_FragColor = vec4((texture2D(texture_sampler, frag_TexCoord).rgb * frag_Color.rgb) * diffuseAngle, 1.0);
}
