package com.ignja.gl.renderer;

import android.opengl.GLES30;
import android.opengl.Matrix;

import com.ignja.gl.object.Object3d;
import com.ignja.gl.renderable.AbstractRenderable;
import com.ignja.gl.util.Shared;

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

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    /**
     * Draw single object into scene
     */
    public void render(Object3d object, float[] mvpMatrix, int glProgram, float[] u_ModelViewMatrix, float[] u_ProjectionMatrix) {
//        // Object transformation on ModelView
//        // TODO Child objects should contain parent transformations ?
//        if (object.parent() != null) {
//            Matrix.translateM(u_ModelViewMatrix, 0,
//                    object.parent.getX(), object.parent.getY(), object.parent.getZ()
//            );
//            // TODO Do not mix ObjectRotation/Position and CameraTransformation
//            // nego rotacija objekta!!! (a to trenutno nemamo)
//            Matrix.rotateM(u_ModelViewMatrix, 0, Shared.renderer().getHAngle(), 0, 0, 1.0f);
//        }
//        Matrix.translateM(u_ModelViewMatrix, 0, object.getX(), object.getY(), object.getZ());
//        Matrix.rotateM(u_ModelViewMatrix, 0, Shared.renderer().getHAngle(), 0, 0, 1.0f);
//        Matrix.scaleM(u_ModelViewMatrix, 0, 1, 1, 1);

        // get handle to vertex shader's vPosition member
        int mPositionHandle = GLES30.glGetAttribLocation(glProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES30.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES30.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                vertexStride, object.object.getVertexBuffer());

        // get handle to fragment shader's vColor member
        int mColorHandle = GLES30.glGetAttribLocation(glProgram, "vColor");

        // Enable a handle to the triangle vertices
        GLES30.glEnableVertexAttribArray(mColorHandle);

        // Prepare the triangle color data
        GLES30.glVertexAttribPointer(
                mColorHandle, COORDS_PER_COLOR,
                GLES30.GL_FLOAT, false,
                COORDS_PER_COLOR * 4, object.object.getColorBuffer());
        MyGLRenderer.checkGlError("MIK glVertexAttribPointer");



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


        drawObject_textures(object.object);

        // Backface culling
        boolean doubleSidedEnabled = false;
        if (doubleSidedEnabled) {
            Shared.gl().glDisable(GL10.GL_CULL_FACE);
        }
        else {
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
                object.object.getDrawOrder().length,
                GLES30.GL_UNSIGNED_SHORT,
                object.object.getDrawListBuffer());


        // Disable vertex array
        GLES30.glDisableVertexAttribArray(mPositionHandle);

        // Disable color array
        GLES30.glDisableVertexAttribArray(mColorHandle);
    }

    private void drawObject_textures(AbstractRenderable $o)
    {
        // iterate thru object's textures

//        for (int i = 0; i < RenderCaps.maxTextureUnits(); i++)
        //for (int i = 0; i < GL10.GL_MAX_TEXTURE_UNITS; i++)
        //{
            int i = 0;
            //Shared.gl().glActiveTexture(GL10.GL_TEXTURE0);
            //Shared.gl().glClientActiveTexture(GL10.GL_TEXTURE0);

//                if ($o.hasUvs() && $o.texturesEnabled()) {
//
//                $o.vertices().uvs().buffer().position(0);
//                _gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, $o.vertices().uvs().buffer());
//
                //TextureVo textureVo = $o.textures().get(i) : null;
//
                //if (textureVo != null)
                //{
//                    // activate texture
//                    int glId = _textureManager.getGlTextureId(textureVo.textureId);
//                    _gl.glBindTexture(GL10.GL_TEXTURE_2D, glId);
//                    _gl.glEn_glable(GL10.GL_TEXTURE_2D);
//                    _gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//
//                    int minFilterType = _textureManager.hasMipMap(textureVo.textureId) ? GL10.GL_LINEAR_MIPMAP_NEAREST : GL10.GL_NEAREST;
//                    _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilterType);
//                    _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR); // (OpenGL default)
//
//                    // do texture environment settings
//                    for (int j = 0; j < textureVo.textureEnvs.size(); j++)
//                    {
//                        _gl.glTexEnvx(GL10.GL_TEXTURE_ENV, textureVo.textureEnvs.get(j).pname, textureVo.textureEnvs.get(j).param);
//                    }
//
//                    // texture wrapping settings
                    //GLES30.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, (GL10.GL_CLAMP_TO_EDGE));
                    //GLES30.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, (GL10.GL_REPEAT));
//
//                    // texture offset, if any
//                    if (textureVo.offsetU != 0 || textureVo.offsetV != 0)
//                    {
//                        _gl.glMatrixMode(GL10.GL_TEXTURE);
//                        _gl.glLoadIdentity();
//                        _gl.glTranslatef(textureVo.offsetU, textureVo.offsetV, 0);
//                        _gl.glMatrixMode(GL10.GL_MODELVIEW); // .. restore matrixmode
//                    }
//                }
//                else
//                {
//                    _gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
//                    _gl.glDisable(GL10.GL_TEXTURE_2D);
//                    _gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//                }
//            } else {
            //Shared.gl().glBindTexture(GL10.GL_TEXTURE_2D, 1); // glId
            //Shared.gl().glDisable(GL10.GL_TEXTURE_2D);
            //Shared.gl().glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//            }
        //}
    }

}
