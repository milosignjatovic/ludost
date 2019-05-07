package com.ignja.gl.object;

import android.graphics.Bitmap;

import com.ignja.gl.util.Shared;
import com.ignja.gl.util.Utils;
import com.ignja.ludost.R;
import com.ignja.ludost.logic.Game;

import java.util.ArrayList;

/**
 * Created by milos on 5/5/19.
 */
public class Scene extends AbstractObject {

    private ArrayList<AbstractObject> objects = new ArrayList<>();

    private float[] camera;

    private ArrayList<AbstractObject> clickedObjects = new ArrayList<>();

    private Game game;

    public void setGame(Game game) {
        this.game = game;
        this.game.setParent(this);
        this.game.setScene(this);
        this.objects.add(game);
    }

    public Game getGame() {
        return game;
    }

    /**
     * Adds AbstractObject to Scene. Object3d's must be added to Scene in order to be rendered
     * Returns always true.
     */
    public void addChild(AbstractObject $object) {
        if (objects.contains($object)) return;
        objects.add($object);
        $object.setParent(this);
        $object.setScene(this);
    }

    public void handleClickEvent(int screenWidth, int screenHeight, float touchX, float touchY, float[] viewMatrix, float[] projectionMatrix, float hAngle) {
        this.game.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
    }

    public void initScene() {
        Bitmap b = Utils.makeBitmapFromResourceId(Shared.context(), R.drawable.stonetexture);
        Shared.textureManager().addTextureId(b, "stonetexture", false);
        b.recycle();
    }

}
