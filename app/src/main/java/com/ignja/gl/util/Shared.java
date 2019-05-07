package com.ignja.gl.util;

import android.app.Activity;

import com.ignja.gl.core.TextureManager;
import com.ignja.gl.renderer.MyGLRenderer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by milos on 5/5/19.
 * Holds static references to TextureManager, Renderer, and the application Context.
 */
public class Shared {
        private static Activity context;
        private static MyGLRenderer renderer;
        private static TextureManager _textureManager;

        private static GL10 _gl;


        public static Activity context()
        {
            return context;
        }
        public static void context(Activity $c)
        {
            context = $c;
        }

        public static MyGLRenderer renderer()
        {
            return renderer;
        }
        public static void renderer(MyGLRenderer $renderer)
        {
            renderer = $renderer;
        }

        /**
         * You must access the TextureManager instance through this accessor
         */
        public static TextureManager textureManager()
        {
            return _textureManager;
        }

        public static void textureManager(TextureManager $bm)
        {
            _textureManager = $bm;
        }

    public static GL10 gl()
    {
        return _gl;
    }
    public static void gl(GL10 $gl)
    {
        _gl = $gl;
    }

}
