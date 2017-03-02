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
package com.ignja.ludost.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.ignja.ludost.R;

import com.ignja.ludost.logic.Board;
import com.ignja.ludost.logic.Game;
import com.ignja.ludost.object.AbstractObject;
import com.ignja.ludost.object.Color;
import com.ignja.ludost.object.Cube;
import com.ignja.ludost.object.Square;
import com.ignja.ludost.object.Triangle;
import com.ignja.ludost.util.LoggerConfig;
import com.ignja.ludost.util.ShaderHelper;
import com.ignja.ludost.util.TextResourceReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";

    private List<AbstractObject> objectsList;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];

    // Matrix used to map coordinates to screen ratio
    // without this matrix, objects are 'skewed'
    // since OpenGL view is [-1, -1, 1, 1] square by default
    // we are re-calculating this in onSurfaceChanged too
    // (phone orientation changed)
    private final float[] mProjectionMatrix = new float[16];

    // Adjust coordinates based on camera position
    private final float[] mViewMatrix = new float[16];

    private final float[] mRotationMatrix = new float[16];

    /** Store the accumulated rotation. */
    private final float[] mAccumulatedRotation = new float[16];

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    /**
     * Horizontal angle
     */
    private float hAngle;

    /**
     * Vertical angle
     */
    private float vAngle;

    private final Context context;

    private int glProgram;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final int COORDS_PER_COLOR = 4;

    public MyGLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        objectsList = new ArrayList<>();

        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.vertex_shader);
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        glProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(glProgram);
        }

        GLES32.glUseProgram(glProgram);

        // Set the background frame color
        GLES32.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        GLES32.glEnable(GLES32.GL_DEPTH_TEST);
        GLES32.glDepthFunc(GLES32.GL_LEQUAL);
        GLES32.glDisable(GLES32.GL_CULL_FACE);

        //addObject(createBlueSquare());
        //addObject(createGreenTriangle());
        addObject(createBlueDarkSquare());
        //addObject(createRedTriangle());
        //addObject(createYellowTriangle());
        addObject(createPinkCube());

        Board board = new Board();
        Game game = new Game(board);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0,
                0f, -4f, -2f, //eye
                0f, 0f, 0f, // center
                0f, 1.0f, 0.0f // eye vertical
        );

    }

    public void addObject(AbstractObject object) {
        objectsList.add(object);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];

        clearScene();

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw square
        //this.draw(blueSquare, mMVPMatrix);

        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
         long time = SystemClock.uptimeMillis() % 4000L;
         float angle = 0.090f * ((int) time);
        angle = 0;

        for (AbstractObject obj : objectsList) {

            // Calculate (apply) TOUCH transformation (rotation)
            Matrix.setRotateM(mRotationMatrix, 0, hAngle + angle, 0, 0, 1.0f);
            Matrix.rotateM(mRotationMatrix, 0, vAngle, 1.0f, 0, 0);
            //Matrix.setRotateM(mRotationMatrix, 0, vAngle, 1.0f, 0, 0);
            // Combine the rotation matrix with the projection and camera view
            // Note that the mMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

            // TODO Calculate objects position and rotation
            // Matrix.translateM(scratch, 0, 0.5f, 0, 0);
            // Matrix.setRotateM(mRotationMatrix, 0, mAngle*2, 0, 0, 1.0f);
            // Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

            // Draw object
            this.draw(obj, scratch);
        }
    }

    private void clearScene() {
        // clear surface on each frame? // todo not needed?
        GLES32.glClearColor(
                Color.GRAY_DARK[0],
                Color.GRAY_DARK[1],
                Color.GRAY_DARK[2],
                Color.GRAY_DARK[3]
        );

        // Draw background color
        GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT | GLES32.GL_DEPTH_BUFFER_BIT);
    }

    private Cube createPinkCube() {
        return new Cube(0.5f, Color.GRAY_LIGHT);
    }

    private AbstractObject createYellowTriangle() {
        float yellowTriangleCoords[] = {
                -1.0f,  0.5f, 2.0f,   // top
                -1.0f, -0.5f, 2.0f,   // bottom left
                -0.5f, 0.5f, 2.0f    // bottom right
        };

        return new Triangle(yellowTriangleCoords, Color.YELLOW);
    }

    private AbstractObject createBlueDarkSquare() {
        return new Square(new float[]{
                -3f, 3f, 0.25f,
                -3f, -3f, 0.25f,
                3f, -3f, 0.25f,
                3f, 3f, 0.25f,
        }, Color.BLUE_DARK);
    }

    private Triangle createRedTriangle() {
        float mi3Coords[] = {
                // in counterclockwise order:
                0.0f,  0.0f, 0.3f,   // top
                0.0f, -1f, 0.3f,   // bottom left
                1f, 0f, 0.3f    // bottom right
        };

        return new Triangle(mi3Coords, Color.RED);
    }

    private AbstractObject createBlueSquare() {
        float squareCoords[] = {
                -0.5f,  0.5f, 0f,   // top left
                -0.5f, -0.5f, 0f,   // bottom left
                0.5f, -0.5f, 0f,   // bottom right
                0.5f,  0.5f, 0f }; // top right
        return new Square(squareCoords, Color.BLUE);
    }

    private AbstractObject createGreenTriangle() {
        float triangleCoords[] = {
                // in counterclockwise order:
                -0.2f,  0f, -1.0f,   // top
                0f, -0.2f, -1.0f,   // bottom left
                0.2f, -0f, -1.0f    // bottom right
        };

        return new Triangle(triangleCoords, Color.GREEN);
    }

    /**
     * Draw single object into scene
     * @param object {@link AbstractObject}
     * @param mvpMatrix float[]
     */
    private void draw(AbstractObject object, float[] mvpMatrix) {
        // get handle to vertex shader's vPosition member
        int mPositionHandle = GLES32.glGetAttribLocation(glProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES32.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES32.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES32.GL_FLOAT, false,
                vertexStride, object.getVertexBuffer());

        // get handle to fragment shader's vColor member
        int mColorHandle = GLES32.glGetAttribLocation(glProgram, "vColor");

        // Enable a handle to the triangle vertices
        GLES32.glEnableVertexAttribArray(mColorHandle);

        // Prepare the triangle color data
        GLES32.glVertexAttribPointer(
                mColorHandle, COORDS_PER_COLOR,
                GLES32.GL_FLOAT, false,
                COORDS_PER_COLOR * 4, object.getColorBuffer());
        MyGLRenderer.checkGlError("MIK glVertexAttribPointer");

        // get handle to shape's transformation matrix
        int mMVPMatrixHandle = GLES32.glGetUniformLocation(glProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES32.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the square
        GLES32.glDrawElements(
                GLES32.GL_TRIANGLES, object.getDrawOrder().length,
                GLES32.GL_UNSIGNED_SHORT, object.getDrawListBuffer());

        // Disable vertex array
        GLES32.glDisableVertexAttribArray(mPositionHandle);

        // Disable color array
        GLES32.glDisableVertexAttribArray(mColorHandle);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes, such as screen rotation
        GLES32.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0,
                -ratio, ratio, -1, 1, // left, right, bottom, top
                2f, // near
                10.0f // far
        );

    }

    /**
    * Utility method for debugging OpenGL calls. Provide the name of the call
    * just after making it:
    *
    * <pre>
    * mColorHandle = GLES32.glGetUniformLocation(mProgram, "vColor");
    * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
    *
    * If the operation is not successful, the check throws an error.
    *
    * @param glOperation - Name of the OpenGL call to check.
    */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES32.glGetError()) != GLES32.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getHAngle() {
        return hAngle;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setHAngle(float angle) {
        hAngle = angle;
    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getVAngle() {
        return vAngle;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setVAngle(float angle) {
        vAngle = angle;
    }

}