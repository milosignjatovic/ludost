package com.ignja.gl.renderer;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.ignja.gl.object.Object3d;
import com.ignja.gl.renderable.AbstractRenderable;
import com.ignja.gl.util.Shared;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Ignja on 09/03/17.
 *
 */

public class ObjectRenderer {

    private GL10 gl;

    // number of coordinates per vertex in this array
    private static final int COORDS_PER_VERTEX = 3;
    private static final int COORDS_PER_COLOR = 4;
    private static final int COORDS_PER_NORMAL = 3;
    private static final int COORDS_PER_TEXTURE_COORD = 2;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    private final int normalsStride = COORDS_PER_NORMAL * 4; // 4 bytes per vertex
    private final int textureCoordsStride = COORDS_PER_TEXTURE_COORD * 4; // 4 bytes per vertex



    /**
     * Draw single renderables into scene
     */
    public void render(Object3d object, float[] mvpMatrix, int glProgram, float[] u_ModelViewMatrix, float[] u_ProjectionMatrix) {
        for (AbstractRenderable renderable: object.renderables) {

            // get handle to vertex shader's vPosition member
            int mPositionHandle = GLES30.glGetAttribLocation(glProgram, "vPosition");

            // Enable a handle to the triangle vertices
            GLES30.glEnableVertexAttribArray(mPositionHandle);

            // Prepare the triangle coordinate data
            GLES30.glVertexAttribPointer(
                    mPositionHandle, COORDS_PER_VERTEX,
                    GLES30.GL_FLOAT, false,
                    vertexStride, renderable.getVertexBuffer());

            // get handle to fragment shader's vColor member
            int mColorHandle = GLES30.glGetAttribLocation(glProgram, "vColor");

            // Enable a handle to the triangle vertices
            GLES30.glEnableVertexAttribArray(mColorHandle);

            // TODO Revert, Commented because of texture
            // (glsl compiler is optimizing code and removing unused variables,
            // so assignement is causing exeption since there is no variable defined in shader))
            // Prepare the triangle color data
            GLES30.glVertexAttribPointer(
                    mColorHandle, COORDS_PER_COLOR,
                    GLES30.GL_FLOAT, false,
                    COORDS_PER_COLOR * 4, renderable.getColorBuffer());
            MyGLRenderer.checkGlError("MIK glVertexAttribPointer");


            // get handle to vertex shader's vNormal member
//            int mNormalHandle = GLES30.glGetAttribLocation(glProgram, "vNormal");
//            MyGLRenderer.checkGlError("glGetAttribLocation");
//
//            // Enable a handle to the triangle vertices
//            GLES30.glEnableVertexAttribArray(mNormalHandle);
//
//            // Prepare the triangle coordinate data
//            GLES30.glVertexAttribPointer(
//                    mNormalHandle, COORDS_PER_NORMAL,
//                    GLES30.GL_FLOAT, false,
//                    normalsStride, renderable.getNormalsBuffer());
//            MyGLRenderer.checkGlError("glVertexAttribPointer");


            // OLD
            // get handle to shape's transformation matrix
            int mMVPMatrixHandle = GLES30.glGetUniformLocation(glProgram, "uMVPMatrix");
            MyGLRenderer.checkGlError("glGetUniformLocation");

            // Apply the projection and view transformation
            GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
            MyGLRenderer.checkGlError("glUniformMatrix4fv");


            // NEW
            // get handle to shape's model view matrix
            int u_ModelViewMatrixHandle = GLES30.glGetUniformLocation(glProgram, "u_ModelViewMatrix");
            MyGLRenderer.checkGlError("glGetUniformLocation");

            // Apply the model view and view transformation
            GLES30.glUniformMatrix4fv(u_ModelViewMatrixHandle, 1, false, u_ModelViewMatrix, 0);
            MyGLRenderer.checkGlError("glUniformMatrix4fv");


            // get handle to shape's model view matrix
            int u_ProjectionMatrixHandle = GLES30.glGetUniformLocation(glProgram, "u_ProjectionMatrix");
            MyGLRenderer.checkGlError("glGetUniformLocation");

            // Apply the model view and view transformation
            GLES30.glUniformMatrix4fv(u_ProjectionMatrixHandle, 1, false, u_ProjectionMatrix, 0);
            MyGLRenderer.checkGlError("glUniformMatrix4fv");


            //drawObject_textures(renderable);
            int mTextureCoordinateHandle;
            if (renderable.hasTexture()) {
                Shared.gl().glActiveTexture(GLES20.GL_TEXTURE0); // TODO More than one texture on object
                mTextureCoordinateHandle = GLES20.glGetAttribLocation(glProgram, "a_TexCoord");
                GLES20.glVertexAttribPointer(mTextureCoordinateHandle,
                        COORDS_PER_TEXTURE_COORD,
                        GLES20.GL_FLOAT, false,
                        0, renderable.getTextureCoordsBuffer());
                GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);
            }

            // Backface culling
            boolean doubleSidedEnabled = false;
            if (doubleSidedEnabled) {
                Shared.gl().glDisable(GL10.GL_CULL_FACE);
            } else {
                Shared.gl().glEnable(GL10.GL_CULL_FACE);
                Shared.gl().glCullFace(GL10.GL_BACK);
            }

            // calculate camera transformation
            //        Matrix.setLookAtM(u_ModelViewMatrix, 0,
            //                0f, -8f, 6f, //eye
            //                0f, 0f, 0f, // center
            //                0f, -1.0f, 0.0f // eye vertical
            //        );

            // Draw the square
            GLES30.glDrawElements(
                    GLES30.GL_TRIANGLES,
                    renderable.getDrawOrder().length,
                    GLES30.GL_UNSIGNED_SHORT,
                    renderable.getDrawListBuffer());


            // Disable vertex array
            GLES30.glDisableVertexAttribArray(mPositionHandle);

            // Disable color array
            //GLES30.glDisableVertexAttribArray(mColorHandle);

            // Disable normal array
            //GLES30.glDisableVertexAttribArray(mNormalHandle);
        }
    }

    private void drawObject_textures(AbstractRenderable renderable) {
        if (renderable.hasTexture()) {
            String glVersion = Shared.gl().glGetString(GL10.GL_VERSION);
            Shared.gl().glActiveTexture(GLES20.GL_TEXTURE0); // TODO More than one texture on object

            FloatBuffer x = renderable.getTextureCoordsBuffer();
            Shared.gl().glTexCoordPointer(
                    AbstractRenderable.COORDS_PER_TEXTURE_COORD,
                    GL10.GL_FLOAT,
                    textureCoordsStride,
                    x);

//            GLES30.glVertexAttribPointer(
//                    mPositionHandle, COORDS_PER_VERTEX,
//                    GLES30.GL_FLOAT, false,
//                    vertexStride, renderable.getVertexBuffer());

            // Needed because texture is not power of 2 :) (Finally)
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            // Filtering
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            int glid = renderable.getTextureId();
            Shared.gl().glBindTexture(GLES30.GL_TEXTURE_2D, glid);

            Shared.gl().glEnable(GLES30.GL_TEXTURE_2D);
            Shared.gl().glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }
    }

}
