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

import android.app.Activity;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.ignja.gl.core.GLActivity;
import com.ignja.gl.core.ISceneController;
import com.ignja.gl.core.Scene;
import com.ignja.gl.core.TextureManager;
import com.ignja.gl.renderer.MyGLRenderer;
import com.ignja.gl.util.Color;
import com.ignja.gl.util.Shared;
import com.ignja.gl.util.Utils;
import com.ignja.gl.view.MyGLSurfaceView;
import com.ignja.gl.vo.Light;
import com.ignja.ludost.R;
import com.ignja.ludost.logic.Game;
import com.ignja.ludost.object.Board;
import com.ignja.ludost.object.Player;

public class OpenGLESActivity extends GLActivity implements ISceneController {

    @Override
    public void initScene() {
        super.initScene();

        // Lights
        Light light1 = new Light();
        light1.position.setAll(0, 0, +3);
        light1.diffuse.setAll(255, 255, 255, 255);
        light1.ambient.setAll(0, 0, 0, 0);
        light1.specular.setAll(0, 0, 0, 0);
        light1.emissive.setAll(0, 0, 0, 0);
        scene.lights().add(light1);

        // TODO Camera
        // TODO Textures

        // Init Game
        Board board = new Board();
        Player player1 = new Player(board, Color.RED, 0);
        Player player2 = new Player(board, Color.GREEN, 1);
        Player player3 = new Player(board, Color.YELLOW, 2);
        Player player4 = new Player(board, Color.BLUE, 3);
        Player[] playerArray = new Player[] {player1, player2, player3, player4};

        Game game = new Game(board, playerArray);
        scene.setGame(game);
    }
}