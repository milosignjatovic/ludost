package com.ignja.gl.object;

import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;

import com.ignja.gl.core.FacesBufferedList;
import com.ignja.gl.core.Scene;
import com.ignja.gl.core.TextureList;
import com.ignja.gl.core.TextureVo;
import com.ignja.gl.core.Vertices;
import com.ignja.gl.renderable.AbstractRenderable;
import com.ignja.gl.renderable.Triangle;
import com.ignja.gl.renderer.ObjectRenderer;
import com.ignja.gl.util.Color;
import com.ignja.gl.util.LoggerConfig;
import com.ignja.gl.util.Utils;
import com.ignja.gl.vo.Number3d;

import java.util.Arrays;

/**
 * Created by Ignja on 4/4/17.
 *
 */

public abstract class Object3d {

    protected String TAG = "Object3d";

    private Scene scene;

    private IObject3dContainer parent;

    private Number3d position = new Number3d(0,0,0);
    private Number3d rotation = new Number3d(0,0,0);
    private Number3d scale = new Number3d(1,1,1);

    /**
     * Renderable. TODO ArrayList? (more than one renderable in single object)
     */
    public AbstractRenderable object;

    protected boolean isClicked = false;

    protected float[] intersectionPoint;

    protected float distance;

    /**
     * Object name
     */
    protected String name;

    protected float[] color;

    protected TextureList _textures;

    protected Vertices _vertices;

    protected FacesBufferedList _faces;

    public Object3d() {
        this.color = Color.ORANGE;
        this.position = new Number3d();
        this.name = "";
    }

    public Object3d(float[] color) {
        this.color = color;
        this.position = new Number3d();
        this.name = "";
        _textures = new TextureList();
    }

    public Object3d(Number3d position, float[] color) {
        this(color);
        this.position = position;
    }

    public Object3d(Number3d position) {
        this(position, Color.RED_DARK);
        this.position = position;
    }

    Object3d(AbstractRenderable renderable, Number3d position, float[] color) {
        this(position, color);
        this.object = renderable;
    }

    /**
     * Maximum number of vertices and faces must be specified at instantiation.
     */
    public Object3d(int $maxVertices, int $maxFaces)
    {
        _vertices = new Vertices($maxVertices, true,true,true);
        _faces = new FacesBufferedList($maxFaces);
        _textures = new TextureList();
    }

    /**
     * Adds three arguments
     */
    public Object3d(int $maxVertices, int $maxFaces, Boolean $useUvs, Boolean $useNormals, Boolean $useVertexColors)
    {
        _vertices = new Vertices($maxVertices, $useUvs,$useNormals,$useVertexColors);
        _faces = new FacesBufferedList($maxFaces);
        _textures = new TextureList();
    }

    /**
     * This constructor is convenient for cloning purposes
     */
    public Object3d(Vertices $vertices, FacesBufferedList $faces, TextureList $textures)
    {
        _vertices = $vertices;
        _faces = $faces;
        _textures = $textures;
    }

    public float getX() {
        return this.position.x;
    }

    public float getY() {
        return this.position.y;
    }

    public float getZ() {
        return this.position.z;
    }

    public void draw(float[] mvpMatrix, int glProgram) {
        ObjectRenderer objectRenderer = new ObjectRenderer();
        if (this.object != null && this.position != null) {
            Matrix.translateM(mvpMatrix, 0, getX(), getY(), getZ());
            objectRenderer.render(this.object, mvpMatrix, glProgram);
            Matrix.translateM(mvpMatrix, 0, -getX(), -getY(), -getZ());
        }
    }

    public void handleClickEvent(int screenWidth, int screenHeight, float touchX, float touchY, float[] viewMatrix, float[] projectionMatrix, float hAngle) {
        this.rayPicking(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
    }

    // TODO Extract from Object?
    private void rayPicking(int viewWidth, int viewHeight, float rx, float ry, float[] viewMatrix, float[] projectionMatrix, float hAngle) {
        if (this.object != null && this.position != null) {
            float [] near_xyz = unProject(rx, ry, 0, viewMatrix, projectionMatrix, viewWidth, viewHeight);
            float [] far_xyz = unProject(rx, ry, 1, viewMatrix, projectionMatrix, viewWidth, viewHeight);
            float[] vertices;
            vertices = this.object.coords;

            float[] tmpMatrix;
            tmpMatrix = viewMatrix.clone();
            Matrix.rotateM(tmpMatrix, 0, hAngle, 0, 0, 1.0f);
            Matrix.translateM(tmpMatrix, 0, getX(), getY(), getZ());
            short[] drawOrder = this.object.getDrawOrder();
            for (int i = 0; i < drawOrder.length/3; i++) {
                float[] resultVector = new float[4];
                float[] inputVector = new float[4];

                // A
                inputVector[0] = vertices[drawOrder[3*i]*3];
                inputVector[1] = vertices[drawOrder[3*i]*3+1];
                inputVector[2] = vertices[drawOrder[3*i]*3+2];
                inputVector[3] = 1;
                Matrix.multiplyMV(resultVector, 0, tmpMatrix, 0, inputVector,0);
                float[] a = new float[3];
                a[0] = resultVector[0]/resultVector[3];
                a[1] = resultVector[1]/resultVector[3];
                a[2] = resultVector[2]/resultVector[3];

                // B
                inputVector[0] = vertices[drawOrder[3*i+1]*3];
                inputVector[1] = vertices[drawOrder[3*i+1]*3+1];
                inputVector[2] = vertices[drawOrder[3*i+1]*3+2];
                inputVector[3] = 1;
                Matrix.multiplyMV(resultVector, 0, tmpMatrix, 0, inputVector,0);
                float[] b = new float[3];
                b[0] = resultVector[0]/resultVector[3];
                b[1] = resultVector[1]/resultVector[3];
                b[2] = resultVector[2]/resultVector[3];

                // C
                inputVector[0] = vertices[drawOrder[3*i+2]*3];
                inputVector[1] = vertices[drawOrder[3*i+2]*3+1];
                inputVector[2] = vertices[drawOrder[3*i+2]*3+2];
                inputVector[3] = 1;
                Matrix.multiplyMV(resultVector, 0, tmpMatrix, 0, inputVector,0);
                float[] c = new float[3];
                c[0] = resultVector[0]/resultVector[3];
                c[1] = resultVector[1]/resultVector[3];
                c[2] = resultVector[2]/resultVector[3];

                Triangle t1 = new Triangle(
                        new float[] {a[0], a[1], a[2]},
                        new float[] {b[0], b[1], b[2]},
                        new float[] {c[0], c[1], c[2]});

                float[] intersectionPoint = new float[3];
                int intersects1 = Utils.intersectRayAndTriangle(near_xyz, far_xyz, t1, intersectionPoint);
                if (intersects1 == 1 || intersects1 == 2) {
                    // TODO Eye object instead of constants?
                    float distance = (float) Math.sqrt(
                        (intersectionPoint[0]-0f)*(intersectionPoint[0]-0f)
                            + (intersectionPoint[1]-8f)*(intersectionPoint[1]-8f)
                            + (intersectionPoint[2]-6f)*(intersectionPoint[2]-6f)
                    );
                    this.click();
                    this.distance = distance;
                    this.intersectionPoint = intersectionPoint;
                    if (LoggerConfig.ON) {
                        Log.d(TAG, "HIT " + this
                                + " Intersection point: " + Arrays.toString(intersectionPoint)
                                + ", Distance: " + distance);
                    }
                }
            }
        } else {
            Log.i(TAG, "Sta je ovo?");
        }

    }

    public float getClickDistance() {
        return this.distance;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void click() {
        this.isClicked = true;
    }

    public void unClick() {
        this.isClicked = false;
    }

    private float[] unProject( float xTouch, float yTouch, float winZ,
                                float[] viewMatrix,
                                float[] projectionMatrix,
                                int width, int height) {
        int[] viewport = {0, 0, width, height};
        float[] out = new float[3];
        float[] temp = new float[4];
        float[] temp2 = new float[4];
        yTouch = (float)viewport[3] - yTouch;
        int result = GLU.gluUnProject(xTouch, yTouch, winZ, viewMatrix, 0, projectionMatrix, 0, viewport, 0, temp, 0);
        Matrix.multiplyMV(temp2, 0, viewMatrix, 0, temp, 0);
        if(result == 1){
            out[0] = temp2[0] / temp2[3];
            out[1] = temp2[1] / temp2[3];
            out[2] = temp2[2] / temp2[3];
        }
        return out;
    }

    public void setParent(IObject3dContainer parent) {
        this.parent = parent;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public IObject3dContainer parent()
    {
        return parent;
    }

    public void parent(IObject3dContainer $container) /*package-private*/
    {
        parent = $container;
    }

    /**
     * Called by Scene
     */
    public void scene(Scene $scene) /*package-private*/
    {
        scene = $scene;
    }
    /**
     * Called by DisplayObjectContainer
     */
    Scene scene() /*package-private*/
    {
        return scene;
    }

    public void addTexture(TextureVo texture) {
        this._textures.add(texture);
    }

    public Number3d position() {
        return position;
    }

    public void position(Number3d position) {
        this.position = position;
    }

    /**
     * X/Y/Z euler rotation of object, using Euler angles.
     * Units should be in degrees, to match OpenGL usage.
     */
    public Number3d rotation()
    {
        return rotation;
    }

    /**
     * X/Y/Z scale of object.
     */
    public Number3d scale()
    {
        return scale;
    }

    public String name() {
        return name;
    }

}
