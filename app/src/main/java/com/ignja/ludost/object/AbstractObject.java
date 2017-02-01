package com.ignja.ludost.object;

import android.opengl.GLES20;

import com.ignja.ludost.renderer.MyGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by milos on 27/01/17.
 */

public class AbstractObject implements ObjectInterface {

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;

    private final int mProgram;

    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // The matrix must be included as a modifier of gl_Position.
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private float[] coords;

    private float[] color;

    private short[] drawOrder;

    // TODO Initialize buffers in renderer, when objects are pushed into scene?
    // TODO Not in the object itself

    protected AbstractObject(float[] coords, float[] color, short[] drawOrder) {
        this.coords = coords;
        this.color = color;
        this.drawOrder = drawOrder;
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                coords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        // prepare shaders and OpenGL program
        int vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
    }

    public float[] getColor() {
        return color;
    }

    public short[] getDrawOrder() {
        return drawOrder;
    }

    public FloatBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public ShortBuffer getDrawListBuffer() {
        return drawListBuffer;
    }

}
