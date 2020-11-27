package com.tck.jni.opensl

/**
 *<p>description:</p>
 *<p>created on: 2020/11/27 15:46</p>
 * @author tck
 *
 */
class AudioControl {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }


    fun play(url: String) {
        nativePlay(url)
    }

    private external fun nativePlay(url: String);

}