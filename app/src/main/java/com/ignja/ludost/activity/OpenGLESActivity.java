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

import com.ignja.gl.core.ISceneController;
import com.ignja.gl.core.TextureManager;
import com.ignja.gl.renderer.MyGLRenderer;
import com.ignja.gl.util.Shared;
import com.ignja.gl.util.Utils;
import com.ignja.gl.view.MyGLSurfaceView;
import com.ignja.ludost.R;

public class OpenGLESActivity extends Activity implements ISceneController {

    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Title bar (GL in full screen)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        // Shared class is used to store singletons
        Shared.context(this);
        Shared.renderer(new MyGLRenderer());
        mGLView = new MyGLSurfaceView(this);
        // Render the view only when there is a change in the drawing data
        //mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setContentView(mGLView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }

    @Override
    public void initScene() {

    }

    @Override
    public void updateScene() {

    }

    @Override
    public Handler getInitSceneHandler() {
        return null;
    }

    @Override
    public Runnable getInitSceneRunnable() {
        return null;
    }

    @Override
    public Handler getUpdateSceneHandler() {
        return null;
    }

    @Override
    public Runnable getUpdateSceneRunnable() {
        return null;
    }
}