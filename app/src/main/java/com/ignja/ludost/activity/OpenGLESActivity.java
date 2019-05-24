/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ignja.ludost.activity;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.ignja.gl.core.GLActivity;
import com.ignja.gl.core.ISceneController;
import com.ignja.gl.util.Color;
import com.ignja.gl.util.Shared;
import com.ignja.gl.util.Utils;
import com.ignja.gl.vo.Light;
import com.ignja.ludost.R;
import com.ignja.ludost.logic.Game;
import com.ignja.ludost.object.Board;
import com.ignja.ludost.object.Player;

public class OpenGLESActivity extends GLActivity implements ISceneController {

    /**
     * Touch events -> camera transformation
     */
    private float _dx;
    private float _dy;
    private float _rot;

    private float mPreviousX;
    private float mPreviousY;

    private int screenWidth;
    private int screenHeight;

    private float XY_TOUCH_SCALE_FACTOR = 7f;
    private float Z_TOUCH_SCALE_FACTOR = 7f;

    @Override
    public void initScene() {
        super.initScene();
        this.screenWidth = this.getWindow().getDecorView().getWidth();
        this.screenHeight = this.getWindow().getDecorView().getHeight();

        this.TAG = "Lugost Activity (OpenGLESActivity)";

        // Lights
        Light light1 = new Light();
        light1.position.setAll(0, 0, +3);
        light1.diffuse.setAll(255, 255, 255, 255);
        light1.ambient.setAll(0, 0, 0, 0);
        light1.specular.setAll(0, 0, 0, 0);
        light1.emissive.setAll(0, 0, 0, 0);
        scene.lights().add(light1);

        // Textures
        Bitmap diceTexture = Utils.makeBitmapFromResourceId(Shared.context(), R.drawable.dice_texture_white_01);
        Shared.textureManager().addTextureId(diceTexture, "diceTexture", false);
        diceTexture.recycle();

        Bitmap boardTexture = Utils.makeBitmapFromResourceId(Shared.context(), R.drawable.ludo_board_03);
        Shared.textureManager().addTextureId(boardTexture, "boardTexture", false);
        boardTexture.recycle();

        Bitmap pieceTexture = Utils.makeBitmapFromResourceId(Shared.context(), R.drawable.metal_white_texture_01);
        Shared.textureManager().addTextureId(pieceTexture, "pieceTexture", false);
        pieceTexture.recycle();

        // Init Game
        Board board = new Board();
        Player player1 = new Player(board, Color.GREEN_PALE, 0);
        Player player2 = new Player(board, Color.RED, 1);
        Player player3 = new Player(board, Color.BLUE_STEEL, 2);
        Player player4 = new Player(board, Color.YELLOW, 3);
        Player[] playerArray = new Player[] {player1, player2, player3, player4};
        Game game = new Game(board, playerArray);
        scene.setGame(game);
    }

    @Override
    public void updateScene() {
        super.updateScene();

        if (_dx != 0) {
            /**
             * When trackball moves horizontally...
             *
             * Rotate camera around center of the scene in a circle.
             * Its position is 2 units above the boxes, but its target() position
             * remains at the scene origin, so the camera always looks towards the center.
             */

            _rot += - _dx / this.screenWidth;
            scene.camera().position.x = (float)Math.sin(_rot * XY_TOUCH_SCALE_FACTOR) * 8f;
            scene.camera().position.y = (float)Math.cos(_rot * XY_TOUCH_SCALE_FACTOR) * -8f; // 8f = distance from center
            _dx = 0;
        }

        if (_dy != 0) {
            scene.camera().position.z = Math.max(3f, Math.min(14f, scene.camera().position.z + _dy / Z_TOUCH_SCALE_FACTOR));
            _dy = 0;
        }
        scene.camera().position.setAll(
                scene.camera().position.x,
                scene.camera().position.y,
                scene.camera().position.z);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleClickEvent(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                _dx = (x - mPreviousX);
                _dy = (y - mPreviousY);
                if (y < this.getWindow().getDecorView().getHeight() / 2) {
                    _dx = _dx * -1;
                }
                break;
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    private void handleClickEvent(float x, float y) {
        Shared.renderer().handleClickEvent(x, y);
    }

}