package com.ignja.gl.renderable;

import com.ignja.core.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Ignja on 27/01/17.

 */

public class AbstractRenderable implements RenderableInterface {

    protected String TAG = "AbstractRenderable";

    private final FloatBuffer vertexBuffer;
    private final FloatBuffer normalsBuffer;
    private final ShortBuffer drawListBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final int COORDS_PER_COLOR = 4;
    public static final int COORDS_PER_TEXTURE_COORD = 2;

    static final int SHORT_SIZE = 2;
    static final int FLOAT_SIZE = 4;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    private final int tetureCoordsStride = COORDS_PER_TEXTURE_COORD * 4; // 4 bytes per vertex

    public final float[] coords;
    private float[] textureCoords;

    private FloatBuffer colorBuffer;

    private FloatBuffer textureCoordsBuffer;

    public final float[] normals;

    private short[] drawOrder;
    private Integer glTextureId;

    // TODO Initialize buffers in renderer, when objects are pushed into scene?
    // TODO Not in the renderables itself

    protected AbstractRenderable(float[] coords, float[] color, short[] drawOrder, float[] normals, float[] texture_coords) {
        this.coords = coords;
        if (color.length/COORDS_PER_COLOR != drawOrder.length) {
            Log.e(TAG, "COLOR ERROR");
        }

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                coords.length * FLOAT_SIZE);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        this.normals = normals;
        ByteBuffer bbNormals = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                normals.length * FLOAT_SIZE);
        bbNormals.order(ByteOrder.nativeOrder());
        normalsBuffer = bbNormals.asFloatBuffer();
        normalsBuffer.put(normals);
        normalsBuffer.position(0);


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

        if (texture_coords.length/COORDS_PER_TEXTURE_COORD != drawOrder.length) {
            Log.e(TAG, "TEXTURE ERROR");
            //throw new RuntimeException("TEXTURE ERROR 22");
        }
        this.textureCoords = texture_coords;
        textureCoordsBuffer = ByteBuffer
                .allocateDirect(texture_coords.length * FLOAT_SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(texture_coords);
        textureCoordsBuffer.position(0);
    }

    public FloatBuffer getColorBuffer() {
        return colorBuffer;
    }

    public FloatBuffer getTextureCoordsBuffer() {
        return textureCoordsBuffer;
    }

    public short[] getDrawOrder() {
        return drawOrder;
    }

    public FloatBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public FloatBuffer getNormalsBuffer() {
        return normalsBuffer;
    }

    public ShortBuffer getDrawListBuffer() {
        return drawListBuffer;
    }

    public void clear() {
        if (this.vertexBuffer != null) this.vertexBuffer.clear();
        if (this.normalsBuffer != null) this.normalsBuffer.clear();
        if (this.colorBuffer != null) this.colorBuffer.clear();
    }

    public void setTextureId(Integer glTextureId) {
        this.glTextureId = glTextureId;
    }
    public int getTextureId() {
        return this.glTextureId;
    }

    public boolean hasTexture() {
        return glTextureId != null;
    }

    public void setTextureCoords(float[] textureCoords) {
        this.textureCoords = textureCoords;
        textureCoordsBuffer = ByteBuffer
                .allocateDirect(textureCoords.length * FLOAT_SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(textureCoords);
        textureCoordsBuffer.position(0);
    }
}
