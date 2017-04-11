package com.ignja.ludost.util;

import android.opengl.GLES30;
import android.util.Log;

/**
 * Created by milos on 01/02/17.
 */

public class ShaderHelper {

    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GLES30.GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GLES30.GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = GLES30.glCreateShader(type);
        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new shader.");
            }
            return 0;
        }
        GLES30.glShaderSource(shaderObjectId, shaderCode);
        GLES30.glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        GLES30.glGetShaderiv(shaderObjectId, GLES30.GL_COMPILE_STATUS, compileStatus, 0);

        if (LoggerConfig.ON) {
            // Print the shader info log to the Android log output.
            Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
                    + GLES30.glGetShaderInfoLog(shaderObjectId));
        }

        if (compileStatus[0] == 0) {
        // If it failed, delete the shader object.
            GLES30.glDeleteShader(shaderObjectId);
            if (LoggerConfig.ON) {
                Log.w(TAG, "Compilation of shader failed.");
            }
            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId = GLES30.glCreateProgram();

        if (programObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new program");
            }
            return 0;
        }

        GLES30.glAttachShader(programObjectId, vertexShaderId);
        GLES30.glAttachShader(programObjectId, fragmentShaderId);

        GLES30.glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        GLES30.glGetProgramiv(programObjectId, GLES30.GL_LINK_STATUS, linkStatus, 0);

        if (LoggerConfig.ON) {
        // Print the program info log to the Android log output.
            Log.v(TAG, "Results of linking program:\n"
                    + GLES30.glGetProgramInfoLog(programObjectId));
        }

        if (linkStatus[0] == 0) {
            // If it failed, delete the program object.
            GLES30.glDeleteProgram(programObjectId);
            if (LoggerConfig.ON) {
                Log.w(TAG, "Linking of program failed.");
            }
            return 0;
        }


        return programObjectId;

    }

    public static boolean validateProgram(int programObjectId) {
        GLES30.glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        GLES30.glGetProgramiv(programObjectId, GLES30.GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Results of validating program: " + validateStatus[0]
                + "\nLog:" + GLES30.glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }

}
