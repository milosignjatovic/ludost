package com.ignja.ludost.object;

import com.ignja.gl.object.Object3d;
import com.ignja.gl.renderable.Cuboid;
import com.ignja.gl.vo.Number3d;

/**
 * Created by milos on 4/4/17.
 */

public class Piece extends Object3d {

    BoardPosition boardPosition;

    Piece(BoardPosition boardPosition, float[] color) {
        super(new Number3d(boardPosition.getX(), boardPosition.getY(), 0.3f), color);
        this.TAG = "PieceObject";
        this.addRenderable(new Cuboid(0.32f, 0.32f, 0.6f, color), "pieceTexture");
        this.renderables.get(0).setTextureCoords(new float[]{
                0.25f, 0.67f, /* Top - 6 */
                0.0f, 0.67f,
                0.0f, 0.33f,
                0.25f, 0.33f,

                0.5f, 0.67f, /* Bottom - 4 */
                0.25f, 0.67f,
                0.25f, 0.33f,
                0.5f, 0.33f,

                0.75f, 0.67f, /* Left - 1 */
                0.5f, 0.67f,
                0.5f, 0.33f,
                0.75f, 0.33f,

                1.0f, 0.67f, /* Right - 3 */
                0.75f, 0.67f,
                0.75f, 0.33f,
                1.0f, 0.33f,

                0.75f, 1.0f, /* Back - 5 */
                0.5f, 1.0f,
                0.5f, 0.67f,
                0.75f, 0.67f,

                0.75f, 0.33f, /* Front - 2 */
                0.5f, 0.33f,
                0.5f, 0.0f,
                0.75f, 0.0f});
        this.moveTo(boardPosition); // Not needed here?
    }

    public void moveTo(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
        this.position(new Number3d(boardPosition.getX(), boardPosition.getY(), 0.3f));
    }

}
