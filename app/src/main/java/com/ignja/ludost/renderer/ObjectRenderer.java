package com.ignja.ludost.renderer;

import android.opengl.GLES32;

import com.ignja.ludost.object.AbstractObject;

/**
 * Created by milos on 09/03/17.
 */

public class ObjectRenderer {

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final int COORDS_PER_COLOR = 4;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    /**
     * Draw single object into scene
     * @param object {@link AbstractObject}
     * @param mvpMatrix float[]
     */
    public void render(AbstractObject object, float[] mvpMatrix, int glProgram) {
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

}
