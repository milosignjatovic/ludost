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
package com.ignja.ludost.renderable;

/**
 * A two-dimensional triangle for use as a drawn object in OpenGL ES 2.0.
 */
public class Triangle extends AbstractRenderable {

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Triangle(float[] coords, float[] color) {
        super(coords, new float[]{
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3]
        }, new short[]{0,1,2});
    }

}