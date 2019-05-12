package com.ignja.gl.core;

import android.graphics.Bitmap;

import com.ignja.gl.object.IObject3dContainer;
import com.ignja.gl.object.Object3d;
import com.ignja.gl.object.Object3dContainer;
import com.ignja.gl.util.Shared;
import com.ignja.gl.util.Utils;
import com.ignja.gl.vo.Color4;
import com.ignja.ludost.R;
import com.ignja.ludost.logic.Game;

import java.util.ArrayList;

/**
 * Created by milos on 5/5/19.
 */
public class Scene implements IObject3dContainer {

    private ManagedLightList _lights;
    private Color4 _backgroundColor;

    private ArrayList<Object3d> _children = new ArrayList<>();

    private float[] camera;

    private ArrayList<Object3d> clickedObjects = new ArrayList<>();

    private Game game;

    private ISceneController _sceneController;

    public Scene(ISceneController sceneController) {
        _sceneController = sceneController;
        _lights = new ManagedLightList();
    }

    /**
     * Resets Scene to default settings.
     * Removes and clears any attached Object3ds.
     * Resets light list.
     */
    public void reset() {
        clearChildren(this);

        _children = new ArrayList<Object3d>();
        _lights = new ManagedLightList();
        _backgroundColor = new Color4(0,0,0,255);

        //_camera = new CameraVo();


        //lightingEnabled(true);
    }

    public Color4 backgroundColor() {
        return _backgroundColor;
    }


    void init() {
        this.reset();
        //_sceneController.initScene();
        //_sceneController.getInitSceneHandler().post(_sceneController.getInitSceneRunnable());
    }

    void update()
    {
        _sceneController.updateScene();
        _sceneController.getUpdateSceneHandler().post(_sceneController.getUpdateSceneRunnable());
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    /**
     * Adds Object3d to Scene. Object3d's must be added to Scene in order to be rendered
     * Returns always true.
     */
    public void addChild(Object3d $object) {
        if (_children.contains($object)) return;
        _children.add($object);
    }

    public void handleClickEvent(int screenWidth, int screenHeight, float touchX, float touchY, float[] viewMatrix, float[] projectionMatrix, float hAngle) {
        this.game.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
    }

    public void initScene() {
        Bitmap b = Utils.makeBitmapFromResourceId(Shared.context(), R.drawable.stonetexture);
        Shared.textureManager().addTextureId(b, "stonetexture", false);
        b.recycle();
    }

    public void addChildAt(Object3d $o, int $index)
    {
        if (_children.contains($o)) return;

        _children.add($index, $o);
    }

    /**
     * Removes Object3d from Scene.
     * Returns false if unsuccessful
     */
    public boolean removeChild(Object3d $o)
    {
        $o.parent(null);
        $o.scene(null);
        return _children.remove($o);
    }

    public Object3d removeChildAt(int $index)
    {
        Object3d o = _children.remove($index);

        if (o != null) {
            o.parent(null);
            o.scene(null);
        }
        return o;
    }

    public Object3d getChildAt(int $index)
    {
        return _children.get($index);
    }

    /**
     * TODO: Use better lookup
     */
    public Object3d getChildByName(String $name)
    {
        for (int i = 0; i < _children.size(); i++)
        {
            if (_children.get(0).name() == $name) return _children.get(0);
        }
        return null;
    }

    public int getChildIndexOf(Object3d $o)
    {
        return _children.indexOf($o);
    }

    public int numChildren()
    {
        return _children.size();
    }

    /**
     * Lights used by the Scene
     */
    public ManagedLightList lights()
    {
        return _lights;
    }

    /**
     * Used by Renderer
     */
    ArrayList<Object3d> children() /*package-private*/
    {
        return _children;
    }

    private void clearChildren(IObject3dContainer $c)
    {
        for (int i = $c.numChildren() - 1; i >= 0; i--)
        {
            Object3d o = $c.getChildAt(i);
            o.clear();

            if (o instanceof Object3dContainer)
            {
                clearChildren((Object3dContainer)o);
            }
        }
    }

}
