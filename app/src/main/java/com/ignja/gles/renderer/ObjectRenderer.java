package com.ignja.gles.renderer;

import android.opengl.GLES30;

import com.ignja.gles.renderable.AbstractRenderable;

/**
 * Created by Ignja on 09/03/17.
 *
 */

public class ObjectRenderer {

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final int COORDS_PER_COLOR = 4;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    /**
     * Draw single object into scene
     * @param object {@link AbstractRenderable}
     * @param mvpMatrix float[]
     */
    public void render(AbstractRenderable object, float[] mvpMatrix, int glProgram) {
        // get handle to vertex shader's vPosition member
        int mPositionHandle = GLES30.glGetAttribLocation(glProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES30.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES30.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                vertexStride, object.getVertexBuffer());

        // get handle to fragment shader's vColor member
        int mColorHandle = GLES30.glGetAttribLocation(glProgram, "vColor");

        // Enable a handle to the triangle vertices
        GLES30.glEnableVertexAttribArray(mColorHandle);

        // Prepare the triangle color data
        GLES30.glVertexAttribPointer(
                mColorHandle, COORDS_PER_COLOR,
                GLES30.GL_FLOAT, false,
                COORDS_PER_COLOR * 4, object.getColorBuffer());
        MyGLRenderer.checkGlError("MIK glVertexAttribPointer");

        // get handle to shape's transformation matrix
        int mMVPMatrixHandle = GLES30.glGetUniformLocation(glProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the square
        GLES30.glDrawElements(
                GLES30.GL_TRIANGLES, object.getDrawOrder().length,
                GLES30.GL_UNSIGNED_SHORT, object.getDrawListBuffer());

        // Disable vertex array
        GLES30.glDisableVertexAttribArray(mPositionHandle);

        // Disable color array
        GLES30.glDisableVertexAttribArray(mColorHandle);
    }

}
