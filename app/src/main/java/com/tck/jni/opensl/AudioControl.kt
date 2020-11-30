package com.tck.jni.opensl

import android.util.Log
import java.io.File

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
        Log.d("tck6666",url)
        Log.d("tck6666",File(url).length().toString())
        nativePlay(url)
    }

    private external fun nativePlay(url: String);

}