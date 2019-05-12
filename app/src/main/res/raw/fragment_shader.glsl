//#version 120
// Fragment shader program
precision mediump int;
precision mediump float;

// Data coming from the vertex shader
varying vec3 light_Position;
varying vec3 frag_Position;
varying vec4 frag_Color;
varying vec3 frag_Normal;

void main() {

    // x, y z(light height)
    vec3 light_position = vec3(0.0, -3.0, 10.0); // should be uniform, from app
    vec3 to_light;
    vec3 vertex_normal;
    float cos_angle;


    // Calculate a vector from the fragment location to the light source
    to_light = light_position - frag_Position;
    to_light = normalize(to_light);

    // The vertex's normal vector is being interpolated across the primitive
    // which can make it un-normalized. So normalize the vertex's normal vector.
    vertex_normal = normalize(frag_Normal);

    // Calculate the cosine of the angle between the vertex's normal vector
    // and the vector going to the light.
    cos_angle = dot(vertex_normal, to_light);
    cos_angle = clamp(cos_angle, 0.6, 1.0);

    // Scale the color of this fragment based on its angle to the light.
    gl_FragColor = vec4(vec3(frag_Color) * cos_angle, frag_Color.a);
}
