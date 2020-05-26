package com.ignja.gl.core;

import android.app.Activity;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.ignja.core.util.Log;
import com.ignja.gl.renderer.MyGLRenderer;
import com.ignja.gl.util.Shared;
import com.ignja.gl.view.MyGLSurfaceView;
import com.ignja.ludost.R;

/**
 * Created by milos on 5/12/19.
 */
public abstract class GLActivity extends Activity implements ISceneController {

    public Scene scene;
    private GLSurfaceView mGLView;

    protected String TAG = "GLActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title bar (GL in full screen)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this GLActivity
        // Shared class is used to store singletons
        Shared.context(this);
        scene = new Scene(this);
        Shared.renderer(new MyGLRenderer(scene));
        mGLView = new MyGLSurfaceView(this);
        // Render the view only when there is a change in the drawing data
        //mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mGLView.setRenderer(Shared.renderer());
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        onCreateSetContentView();
        addSettingsButton();
    }

    protected void addSettingsButton() {
        ImageButton settingsButton = new ImageButton(this);
        settingsButton.setImageResource(R.drawable.ic_settings);
        settingsButton.setAlpha(1.0f);
        settingsButton.setBackgroundColor(Color.TRANSPARENT);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Button", "Show settings screen");
            }
        });
        addContentView(settingsButton, new LinearLayout.LayoutParams(100, 100));
    }

    /**
     * Instantiation of Object3D's, setting their properties, and adding Object3D's
     * to the scene should be done here. Or any point thereafter.
     *
     * Note that this method is always called after GLCanvas is created, which occurs
     * not only on Activity.onCreate(), but on Activity.onResume() as well.
     * It is the user's responsibility to build the logic to restore state on-resume.
     */
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
    public Runnable getUpdateSceneRunnable() {
        return null;
    }

    /**
     * Separated out for easier overriding...
     */
    protected void onCreateSetContentView()
    {
        setContentView(mGLView);
    }

}
