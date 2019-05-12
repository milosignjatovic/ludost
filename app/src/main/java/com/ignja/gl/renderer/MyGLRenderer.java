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
import com.ignja.gl.vo.Light;
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

    public static final int NUM_GLLIGHTS = 8;

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

    private Scene _scene;

    private GL10 _gl;

    public MyGLRenderer() {
        Shared.textureManager(new TextureManager());
    }

    public MyGLRenderer(Scene scene) {
        _scene = scene;
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

        reset();

        Board board = new Board();
        Player player1 = new Player(board, Color.RED_DARK, 0);
        Player player2 = new Player(board, Color.GREEN_DARK, 1);
        Player player3 = new Player(board, Color.ORANGE, 2);
        Player player4 = new Player(board, Color.PINK, 3);
        Player[] playerArray = new Player[] {player1, player2, player3, player4};
        //this._scene = new Scene();
        this._scene.initScene();
        Game game = new Game(board, playerArray);
        this._scene.setGame(game);

        Light _light = new Light();
        _light.position.setAll(0, 0, +3);
        _light.diffuse.setAll(255, 255, 255, 255);
        _light.ambient.setAll(0, 0, 0, 0);
        _light.specular.setAll(0, 0, 0, 0);
        _light.emissive.setAll(0, 0, 0, 0);
        _scene.lights().add(_light);

        // Set the camera position (View matrix)
//        Matrix.setLookAtM(mViewMatrix, 0,
//                0f, -8f, 6f, //eye
//                0f, 0f, 0f, // center
//                0f, -1.0f, 0.0f // eye vertical
//        );

        // TODO Simple light
        //drawSetupLights();
    }

    protected void drawSetupLights()
    {
        // GL_LIGHTS enabled/disabled based on enabledDirty list
        for (int glIndex = 0; glIndex < NUM_GLLIGHTS; glIndex++)
        {
            if (_scene.lights().glIndexEnabledDirty()[glIndex] == true)
            {
                if (_scene.lights().glIndexEnabled()[glIndex] == true)
                {
                    _gl.glEnable(GL10.GL_LIGHT0 + glIndex);

                    // make light's properties dirty to force update
                    // _scene.lights().getLightByGlIndex(glIndex).setAllDirty();
                }
                else
                {
                    _gl.glDisable(GL10.GL_LIGHT0 + glIndex);
                }

                _scene.lights().glIndexEnabledDirty()[glIndex] = false; // clear dirtyflag
            }
        }

        // Lights' properties

        Light[] lights = _scene.lights().toArray();
        for (int i = 0; i < lights.length; i++)
        {
            Light light = lights[i];

//            if (light.isDirty()) // .. something has changed
//            {
                // Check all of Light's properties for dirty

                int glLightId = GL10.GL_LIGHT0 + _scene.lights().getGlIndexByLight(light);

//                if (light.position.isDirty())
//                {
//                    light.commitPositionAndTypeBuffer();
//                    _gl.glLightfv(glLightId, GL10.GL_POSITION, light._positionAndTypeBuffer);
//                    light.position.clearDirtyFlag();
//                }
//                if (light.ambient.isDirty())
//                {
//                    light.ambient.commitToFloatBuffer();
//                    _gl.glLightfv(glLightId, GL10.GL_AMBIENT, light.ambient.floatBuffer());
//                    light.ambient.clearDirtyFlag();
//                }
//                if (light.diffuse.isDirty())
//                {
//                    light.diffuse.commitToFloatBuffer();
//                    _gl.glLightfv(glLightId, GL10.GL_DIFFUSE, light.diffuse.floatBuffer());
//                    light.diffuse.clearDirtyFlag();
//                }
//                if (light.specular.isDirty())
//                {
//                    light.specular.commitToFloatBuffer();
//                    _gl.glLightfv(glLightId, GL10.GL_SPECULAR, light.specular.floatBuffer());
//                    light.specular.clearDirtyFlag();
//                }
//                if (light.emissive.isDirty())
//                {
//                    light.emissive.commitToFloatBuffer();
//                    _gl.glLightfv(glLightId, GL10.GL_EMISSION, light.emissive.floatBuffer());
//                    light.emissive.clearDirtyFlag();
//                }
//
//                if (light.direction.isDirty())
//                {
//                    light.direction.commitToFloatBuffer();
//                    _gl.glLightfv(glLightId, GL10.GL_SPOT_DIRECTION, light.direction.floatBuffer());
//                    light.direction.clearDirtyFlag();
//                }
//                if (light._spotCutoffAngle.isDirty())
//                {
//                    _gl.glLightf(glLightId, GL10.GL_SPOT_CUTOFF, light._spotCutoffAngle.get());
//                }
//                if (light._spotExponent.isDirty())
//                {
//                    _gl.glLightf(glLightId, GL10.GL_SPOT_EXPONENT, light._spotExponent.get());
//                }

//                if (light._isVisible.isDirty())
//                {
                    if (light.isVisible()) {
                        _gl.glEnable(glLightId);
                    } else {
                        _gl.glDisable(glLightId);
                    }
//                    light._isVisible.clearDirtyFlag();
//                }

//                if (light._attenuation.isDirty())
//                {
                    //_gl.glLightf(glLightId, GL10.GL_CONSTANT_ATTENUATION, light._attenuation.x);
                    //_gl.glLightf(glLightId, GL10.GL_LINEAR_ATTENUATION, light._attenuation.y);
                    //_gl.glLightf(glLightId, GL10.GL_QUADRATIC_ATTENUATION, light._attenuation.z);
//                }

//                light.clearDirtyFlag();
//            }
        }
    }

    private boolean touch = false;
    private float touchX = 0f;
    private float touchY = 0f;

    @Override
    public void onDrawFrame(GL10 gl) {
        setGl(gl);
        drawSetup();
        // TODO Move to object model transformation
        // Calculate the projection and view transformation
        //Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);


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

        this._scene.getGame().draw(mMVPMatrix, glProgram, mViewMatrix, mProjectionMatrix);

        if (touch) {
            this._scene.handleClickEvent(screenWidth, screenHeight, touchX, touchY, mViewMatrix, mProjectionMatrix, hAngle + angle);
            touch = false;
        }
    }



    private void drawSetup() {
        if (_scene.backgroundColor() != null) {
            GLES30.glClearColor(
                    _scene.backgroundColor().r / 255f,
                    _scene.backgroundColor().g / 255f,
                    _scene.backgroundColor().b / 255f,
                    _scene.backgroundColor().a / 255f
            );
        }

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

    private void reset() {
        // Reset TextureManager
        Shared.textureManager().reset();

        // Do OpenGL settings which we are using as defaults, or which we will not be changing on-draw

        // Explicit depth settings
        _gl.glEnable(GL10.GL_DEPTH_TEST);
        _gl.glClearDepthf(1.0f);
        _gl.glDepthFunc(GL10.GL_LESS);
        _gl.glDepthRangef(0,1f);
        _gl.glDepthMask(true);

        // Alpha enabled
        _gl.glEnable(GL10.GL_BLEND);
        _gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        // "Transparency is best implemented using glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        // with primitives sorted from farthest to nearest."

        // Texture
        _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST); // (OpenGL default is GL_NEAREST_MIPMAP)
        _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR); // (is OpenGL default)

        // CCW frontfaces only, by default
        _gl.glFrontFace(GL10.GL_CCW);
        _gl.glCullFace(GL10.GL_BACK);
        _gl.glEnable(GL10.GL_CULL_FACE);

        // Disable lights by default
        for (int i = GL10.GL_LIGHT0; i < GL10.GL_LIGHT0 + NUM_GLLIGHTS; i++) {
            //_gl.glDisable(i);
        }

        //
        // Scene object init only happens here, when we get GL for the first time
        //
    }

}