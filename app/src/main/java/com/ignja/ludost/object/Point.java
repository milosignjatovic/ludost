package com.ignja.ludost.object;

/**
 * Created by Ignja on 4/4/17.
 *
 * Represents point in space
 *
 */

public class Point {

    float x;

    float y;

    float z;

    public Point() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0f;
    }

    public Point(float x, float y, float z) {
        this(x, y);
        this.z = z;
    }


}
