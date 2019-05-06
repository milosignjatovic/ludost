package com.ignja.ludost.object;

import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;

import com.ignja.ludost.core.Scene;
import com.ignja.ludost.renderable.AbstractRenderable;
import com.ignja.ludost.renderable.Triangle;
import com.ignja.ludost.renderer.ObjectRenderer;
import com.ignja.ludost.util.Color;
import com.ignja.ludost.util.Utils;

import java.util.Arrays;

/**
 * Created by Ignja on 4/4/17.
 *
 */

public abstract class AbstractObject {

    protected String TAG = "AbstractObject";

    private float[] mMVMatrix = new float[16];

    /**
     * Object position in space
     */
    private Point point;

    private Scene scene;

    private AbstractObject parent;

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
    private String name;

    private float[] color;

    public AbstractObject() {
        this.color = Color.ORANGE;
        this.point = new Point();
        this.name = "";
    }

    AbstractObject(float[] color) {
        this.color = color;
        this.point = new Point();
        this.name = "";
    }

    AbstractObject(Point point, float[] color) {
        this(color);
        this.point = point;
    }

    AbstractObject(Point point) {
        this(point, Color.RED_DARK);
        this.point = point;
    }

    AbstractObject(AbstractRenderable renderable, Point point, float[] color) {
        this(point, color);
        this.object = renderable;
    }

    protected float getX() {
        return this.point.x;
    }

    protected float getY() {
        return this.point.y;
    }

    protected float getZ() {
        return this.point.z;
    }

    public void draw(float[] mvpMatrix, int glProgram) {
        ObjectRenderer objectRenderer = new ObjectRenderer();
        if (this.object != null) {
            Matrix.translateM(mvpMatrix, 0, getX(), getY(), getZ());
            objectRenderer.render(this.object, mvpMatrix, glProgram);
            Matrix.translateM(mvpMatrix, 0, -getX(), -getY(), -getZ());
        }
    }

    public void handleClickEvent(int screenWidth, int screenHeight, float touchX, float touchY, float[] viewMatrix, float[] projMatrix, float hAngle) {
        this.rayPicking(screenWidth, screenHeight, touchX, touchY, viewMatrix, projMatrix, hAngle);
    }

    private void rayPicking(int viewWidth, int viewHeight, float rx, float ry, float[] viewMatrix, float[] projMatrix, float hAngle) {
        if (this.object != null) {
            float [] near_xyz = unProject(rx, ry, 0, viewMatrix, projMatrix, viewWidth, viewHeight);
            float [] far_xyz = unProject(rx, ry, 1, viewMatrix, projMatrix, viewWidth, viewHeight);
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
                    Log.d(TAG, "HIT " + this
                            + " Intersection point: " + Arrays.toString(intersectionPoint)
                            + ", Distance: " + distance);
                }
            }
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

    public void unclick() {
        this.isClicked = false;
    }

    private float[] unProject( float xTouch, float yTouch, float winz,
                                float[] viewMatrix,
                                float[] projMatrix,
                                int width, int height) {
        int[] viewport = {0, 0, width, height};

        float[] out = new float[3];
        float[] temp = new float[4];
        float[] temp2 = new float[4];
        // get the near and far ords for the click

        float winx = xTouch, winy =(float)viewport[3] - yTouch;

        int result = GLU.gluUnProject(winx, winy, winz, viewMatrix, 0, projMatrix, 0, viewport, 0, temp, 0);

        Matrix.multiplyMV(temp2, 0, viewMatrix, 0, temp, 0);
        if(result == 1){
            out[0] = temp2[0] / temp2[3];
            out[1] = temp2[1] / temp2[3];
            out[2] = temp2[2] / temp2[3];
        }
        return out;
    }

    public void setParent(AbstractObject parent) {
        this.parent = parent;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }
}
