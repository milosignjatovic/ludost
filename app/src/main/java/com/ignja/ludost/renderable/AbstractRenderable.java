package com.ignja.ludost.renderable;

import android.util.Log;

import com.ignja.ludost.util.LoggerConfig;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Ignja on 27/01/17.

 */

public class AbstractRenderable implements RenderableInterface {

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final int COORDS_PER_COLOR = 4;

    static final int SHORT_SIZE = 2;
    static final int FLOAT_SIZE = 4;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private FloatBuffer colorBuffer;

    private short[] drawOrder;

    // TODO Initialize buffers in renderer, when objects are pushed into scene?
    // TODO Not in the object itself

    protected AbstractRenderable(float[] coords, float[] color, short[] drawOrder) {
        if (color.length/COORDS_PER_COLOR != drawOrder.length) {
            if (LoggerConfig.ON) {
                Log.e("D", "COLOR ERROR");
                throw new RuntimeException("COLOR ERROR 22");
            }
        }

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                coords.length * FLOAT_SIZE);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        colorBuffer = ByteBuffer
            .allocateDirect(color.length * FLOAT_SIZE) // allocate memory
            .order(ByteOrder.nativeOrder()) // adjust to device buffer storage order
            .asFloatBuffer() // convert to float buffer
            .put(color); // put data
        colorBuffer.position(0); // rewind buffer

        this.drawOrder = drawOrder;

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * SHORT_SIZE);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }

    public FloatBuffer getColorBuffer() {
        return colorBuffer;
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
