package com.ignja.core.util;

/**
 * Created by milos on 01/02/17.
 */

public final class Log {

    public static int i(String tag, String msg) {
        return LoggerConfig.ON ? android.util.Log.i(tag, msg) : 0;
    }

    public static int d(String tag, String msg) {
        return LoggerConfig.ON ? android.util.Log.d(tag, msg) : 0;
    }

    public static int e(String tag, String msg) {
        return LoggerConfig.ON ? android.util.Log.e(tag, msg) : 0;
    }

    public static int e(String tag, String msg, Throwable tr) {
        return LoggerConfig.ON ? android.util.Log.e(tag, msg, tr) : 0;
    }

    public static int v(String tag, String msg) {
        return LoggerConfig.ON ? android.util.Log.v(tag, msg) : 0;
    }

    public static int w(String tag, String msg) {
        return LoggerConfig.ON ? android.util.Log.v(tag, msg) : 0;
    }

}
