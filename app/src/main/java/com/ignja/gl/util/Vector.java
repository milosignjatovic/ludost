package com.ignja.gl.util;

/**
 * Created by milos on 5/4/19.
 */
public class Vector {

    /**
     * dot product (3D) which allows vector operations in arguments
     */
    public static float dot(float[] u,float[] v) {
        return ((u[X] * v[X]) + (u[Y] * v[Y]) + (u[Z] * v[Z]));
    }
    public static float[] minus(float[] u, float[] v){
        return new float[]{u[X]-v[X],u[Y]-v[Y],u[Z]-v[Z]};
    }
    public static float[] addition(float[] u, float[] v){
        return new float[]{u[X]+v[X],u[Y]+v[Y],u[Z]+v[Z]};
    }

    /**
     * Scalar product
     */
    public static float[] scalarProduct(float r, float[] u){
        return new float[]{u[X]*r,u[Y]*r,u[Z]*r};
    }
    /**
     * Cross product
     */
    public static float[] crossProduct(float[] u, float[] v){
        return new float[]{(u[Y]*v[Z]) - (u[Z]*v[Y]),(u[Z]*v[X]) - (u[X]*v[Z]),(u[X]*v[Y]) - (u[Y]*v[X])};
    }

    /**
     * Magnitude or length
     */
    public static float length(float[] u){
        return (float) Math.abs(Math.sqrt((u[X] *u[X]) + (u[Y] *u[Y]) + (u[Z] *u[Z])));
    }

    public static final int X = 0;

    public static final int Y = 1;

    public static final int Z = 2;
}
