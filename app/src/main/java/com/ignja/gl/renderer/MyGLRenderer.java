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
package com.ignja.gl.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import com.ignja.gl.core.TextureManager;
import com.ignja.ludost.R;

import com.ignja.gl.core.Scene;
import com.ignja.ludost.object.Board;
import com.ignja.ludost.logic.Game;
import com.ignja.ludost.object.Player;
import com.ignja.gl.util.Color;
import com.ignja.gl.util.LoggerConfig;
import com.ignja.gl.util.ShaderHelper;
import com.ignja.gl.util.Shared;
import com.ignja.gl.util.TextResourceReader;

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

    public static float nearZ = 2.0f;
    public static float farZ = 20.0f;

    private static final String TAG = "MyGLRenderer";

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

    private final float[] mModelMatrix = new float[16];

    private int screenWidth;
    private int screenHeight;

    /**
     * Horizontal angle
     */
    private float hAngle;

    private int glProgram;

    private Scene scene;

    private GL10 _gl;

    public MyGLRenderer() {
        Shared.textureManager(new TextureManager());
    }

    private void setGl(GL10 $gl) {
        _gl = $gl;
        Shared.gl($gl);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        setGl(gl);

        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(Shared.context(), R.raw.vertex_shader);
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(Shared.context(), R.raw.fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        glProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(glProgram);
        }

        GLES30.glUseProgram(glProgram);

        // Set the background frame color
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        GLES30.glDisable(GLES30.GL_CULL_FACE);

        Board board = new Board();
        Player player1 = new Player(board, Color.RED_DARK, 0);
        Player player2 = new Player(board, Color.GREEN_DARK, 1);
        Player player3 = new Player(board, Color.ORANGE, 2);
        Player player4 = new Player(board, Color.PINK, 3);
        Player[] playerArray = new Player[] {player1, player2, player3, player4};
        this.scene = new Scene();
        this.scene.initScene();
        Game game = new Game(board, playerArray);
        this.scene.setGame(game);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0,
                0f, -8f, 6f, //eye
                0f, 0f, 0f, // center
                0f, -1.0f, 0.0f // eye vertical
        );

    }

    private boolean touch = false;
    private float touchX = 0f;
    private float touchY = 0f;

    @Override
    public void onDrawFrame(GL10 gl) {
        float[] scratch = new float[16];

        clearScene();

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);


        // Draw square
        //this.draw(blueSquare, mMVPMatrix);

        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
//        long time = SystemClock.uptimeMillis() ; // % 4000L;
//        float angle = 0.03f * ((int) time) * 7 / 22;
//        if (LoggerConfig.ON) {
//            Log.i("AUTOROTATE", Float.toString(angle));
//        }
        float angle = 0;
        Matrix.rotateM(mMVPMatrix, 0, hAngle + angle, 0, 0, 1.0f);

        this.scene.getGame().draw(mMVPMatrix, glProgram);

        if (touch) {
            this.scene.handleClickEvent(screenWidth, screenHeight, touchX, touchY, mViewMatrix, mProjectionMatrix, hAngle + angle);
            touch = false;
        }
    }

    private void clearScene() {
        // clear surface on each frame?
//        GLES30.glClearColor(
//                Color.GRAY_DARK[0],
//                Color.GRAY_DARK[1],
//                Color.GRAY_DARK[2],
//                Color.GRAY_DARK[3]
//        );

        // Draw background color
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        setGl(gl);
        screenWidth = width;
        screenHeight = height;
        // Adjust the viewport based on geometry changes, such as screen rotation
        // TODO reset zoom on rotate screen?
        GLES30.glViewport(0, 0, width, height);
        if (LoggerConfig.ON) {
            Log.i("", "W: " + width + ", H: " + height);
        }

        float ratio = (float) width / height;

        Matrix.frustumM(mProjectionMatrix, 0,
                ratio, -ratio, 1, -1, // left, right, bottom, top
                nearZ,
                farZ
        );

    }

    /**
    * Utility method for debugging OpenGL calls. Provide the name of the call
    * just after making it:
    *
    * <pre>
    * mColorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");
    * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
    *
    * If the operation is not successful, the check throws an error.
    *
    * @param glOperation - Name of the OpenGL call to check.
    */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
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

    public void handleClickEvent(float x, float y) {
        touch = true;
        touchX = x;
        touchY =  y;
    }

    public void deleteTexture(int glTextureId) {
        int[] a = new int[1];
        a[0] = glTextureId;
        _gl.glDeleteTextures(1, a, 0);
    }

    /**
     * Used by TextureManager
     */
    public int uploadTextureAndReturnId(Bitmap $bitmap, boolean $generateMipMap) /*package-private*/
    {
        int glTextureId;

        int[] a = new int[1];
        _gl.glGenTextures(1, a, 0); // create a 'texture name' and put it in array element 0
        glTextureId = a[0];
        _gl.glBindTexture(GL10.GL_TEXTURE_2D, glTextureId);

        // TODO Ovaj blok ne radi :/
//        if($generateMipMap && _gl instanceof GL11) {
//            _gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
//        } else {
//            _gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP, GL11.GL_FALSE);
//        }

        // Ovaj radi ;), al ne znam da nacrtam :/ nista
        _gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
        _gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR );

        _gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT );
        _gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT );

        // 'upload' to gpu
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, $bitmap, 0);

        return glTextureId; // 1
    }

}