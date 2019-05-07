package com.ignja.ludost.util;

import com.ignja.gles.renderable.Triangle;

import java.util.Arrays;

/**
 * Created by milos on 5/5/19.
 */
public class Utils {

    /**
     * Anything that avoids division overflow
     *
     */
    private static final float SMALL_NUM =  0.00000001f;

    /**
     * intersectRayAndTriangle(): intersect a ray with a 3D triangle
     *
     * Input:  a ray R, and a triangle T
     * Output: *I = intersection point (when it exists)
     * Return: -1 = triangle is degenerate (a segment or point)
     *  0 = disjoint (no intersect)
     *  1 = intersect in unique point I1
     *  2 = are in the same plane
     */
    public static int intersectRayAndTriangle(float[] near, float[] far, Triangle T, float[] I) {
        float[] u, v, n; // triangle vectors
        float[] dir, w0, w; // ray vectors
        float r, a, b; // params to calc ray-plane intersect


        float[] V0 = new float[]{T.coords[0], T.coords[1], T.coords[2]};
        float[] V1 = new float[]{T.coords[3], T.coords[4], T.coords[5]};
        float[] V2 = new float[]{T.coords[6], T.coords[7], T.coords[8]};

        // get triangle edge vectors and plane normal
        u =  Vector.minus(V1, V0);
        v =  Vector.minus(V2, V0);
        n =  Vector.crossProduct(u, v); // cross product

        if (Arrays.equals(n, new float[]{0.0f,0.0f,0.0f})){ // triangle is degenerate
            return -1; // do not deal with this case
        }
        dir =  Vector.minus(near, far); // ray direction vector
        w0 = Vector.minus( far , V0);
        a = - Vector.dot(n,w0);
        b =  Vector.dot(n,dir);
        if (Math.abs(b) < SMALL_NUM) { // ray is parallel to triangle plane
            if (a == 0){  // ray lies in triangle plane
                return 0; // TODO ... is it Hit or Not? Currently it is not
            }else{
                return 0; // ray disjoint from plane
            }
        }

        // get intersect point of ray with triangle plane
        r = a / b;
        if (r < 0.0f){                   // ray goes away from triangle
            return 0;                  // => no intersect
        }
        // for a segment, also test if (r > 1.0) => no intersect
        float[] tempI =  Vector.addition(far,  Vector.scalarProduct(r, dir));           // intersect point of ray and plane
        I[0] = tempI[0];
        I[1] = tempI[1];
        I[2] = tempI[2];

        // is I inside T?
        float    uu, uv, vv, wu, wv, D;
        uu =  Vector.dot(u,u);
        uv =  Vector.dot(u,v);
        vv =  Vector.dot(v,v);
        w =  Vector.minus(I, V0);
        wu =  Vector.dot(w,u);
        wv = Vector.dot(w,v);
        D = (uv * uv) - (uu * vv);

        // get and test parametric coords
        float s, t;
        s = ((uv * wv) - (vv * wu)) / D;
        if (s < 0.0f || s > 1.0f)        // I is outside T
            return 0;
        t = (uv * wu - uu * wv) / D;
        if (t < 0.0f || (s + t) > 1.0f)  // I is outside T
            return 0;

        return 1;                      // I is in T
    }

}
