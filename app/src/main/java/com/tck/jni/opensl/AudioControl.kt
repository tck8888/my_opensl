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

    private val nativeHandle: Long

    init {
       nativeHandle = nativeInit()
    }


    fun play(url: String) {
        Log.d("tck6666", url)
        Log.d("tck6666", File(url).length().toString())
        nativePlay(url)
    }

    //https://www.cnblogs.com/yongdaimi/p/12426966.html
    fun startRecordAudio(url: String) {
        nativeStartRecordAudio(nativeHandle, url)
    }

    fun stopRecordAudio() {
        nativeStopRecordAudio(nativeHandle)
    }

    private external fun nativePlay(url: String)

    private external fun nativeInit(): Long

    private external fun nativeStartRecordAudio(nativeHandle: Long, url: String)

    private external fun nativeStopRecordAudio(nativeHandle: Long)

}