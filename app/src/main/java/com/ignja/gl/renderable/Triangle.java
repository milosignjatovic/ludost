/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ignja.gl.renderable;

import com.ignja.gl.util.Color;

/**
 * A two-dimensional triangle for use as a drawn renderables in OpenGL ES 2.0.
 */
public class Triangle extends AbstractRenderable {

    public Triangle(float[] coords, float[] color) {
        super(coords, new float[]{
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3]
        }, new short[]{0,1,2}, new float[]{

        });
    }

    public Triangle(float[] coords) {
        super(coords, Color.WHITE, new short[]{0,1,2}, new float[]{
                0f, 0f, 1f,
        });
    }

    public Triangle(float[] a, float[] b, float[] c) {
        super(new float[]{
                a[0], a[1], a[2],
                b[0], b[1], b[2],
                c[0], c[1], c[2]
        }, Color.WHITE, new short[]{0,1,2}, new float[]{
                0f, 0f, 1f,
        });
    }

}
