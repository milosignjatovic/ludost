package com.ignja.gl.util;

/**
 * Created by milos on 01/02/17.
 */

public class LoggerConfig {

    /**
     * Enable application log
     *
     * Set to FALSE because of
     * JNI DETECTED ERROR IN APPLICATION: input is
     * not valid Modified UTF-8: illegal start byte 0xfc...
     * in call to NewStringUTF
     *
     * error with gl driver (in emulator)
     *
     */
    public static final boolean ON = false;

}
