package com.tck.jni.opensl

import android.media.*
import android.os.Process
import android.util.Log
import java.io.DataInputStream
import java.io.FileInputStream

/**
 *<p>description:</p>
 *<p>created on: 2020/12/1 13:27</p>
 * @author tck
 *
 */
class AudioTrackManager {


    /**
     * 采样率
     */
    private val sampleRateInHz = 44100

    /**
     *通道数目
     */
    private val channelConfig = AudioFormat.CHANNEL_OUT_STEREO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT

    /**
     * 用于音乐播放的音频流
     */
    private val streamType = AudioManager.STREAM_MUSIC

    /**
     * AudioTrack.MODE_STREAM
     * STREAM的意思是由用户在应用程序通过write方式把数据一次一次得写到AudioTrack中
     * 这个和我们在socket中发送数据一样，应用层从某个地方获取数据，例如通过编解码得到PCM数据，然后write到AudioTrack。
     * 这种方式的坏处就是总是在JAVA层和Native层交互，效率损失较大
     *
     * AudioTrack.MODE_STATIC
     * STATIC就是数据一次性交付给接收方。
     * 好处是简单高效，只需要进行一次操作就完成了数据的传递;缺点当然也很明显，对于数据量较大的音频回放，
     * 显然它是无法胜任的，因而通常只用于播放铃声、系统提醒等对内存小的操作
     */
    private val mode = AudioTrack.MODE_STREAM
    private val bufferSizeInBytes =
        AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat)

    private var audioTrack: AudioTrack? = null
    private var recordThread: Thread? = null

    private fun getAudioTrack(): AudioTrack? {
        try {
            return AudioTrack(
                streamType,
                sampleRateInHz,
                channelConfig,
                audioFormat,
                bufferSizeInBytes,
                mode
            )
        } catch (e: Exception) {
        }
        return null
    }

    /**
     * 启动播放线程
     */
    private fun startThread() {
        destroyThread()
        if (audioTrack == null) {
            audioTrack = getAudioTrack()
        }

        if (recordThread == null) {
            recordThread = Thread(recordRunnable)
            recordThread?.start()
        }
    }

    /**
     * 销毁线程方法
     */
    private fun destroyThread() {
        try {
            recordThread?.let {
                if (it.state == Thread.State.RUNNABLE) {
                    Thread.sleep(500)
                    it.isInterrupted
                }
            }
        } catch (e: Exception) {
        }
        recordThread = null
    }

    private val recordRunnable = Runnable {
        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO)
            val tempBuffer = ByteArray(bufferSizeInBytes)
            var readCount = 0
            audioTrack?.let { audioTrack ->
                audioTrack.play()
                FileInputStream(pcmPath).use {
                    DataInputStream(it).use { dataInputStream ->
                        while (dataInputStream.available() > 0) {
                            readCount = dataInputStream.read(tempBuffer)
                            if (readCount == AudioTrack.ERROR_INVALID_OPERATION || readCount == AudioTrack.ERROR_BAD_VALUE) {
                                continue
                            }
                            if (readCount != 0 && readCount != -1) {
                                audioTrack.write(tempBuffer, 0, readCount)
                            }
                            Log.d("tck6666", "play: " + readCount)
                        }
                    }
                }
            }
            stopPlay()
        } catch (e: Exception) {
        }
    }

    private var pcmPath = ""

    fun setDataSource(pcmPath: String) {
        this.pcmPath = pcmPath
    }

    fun startPlay() {
        try {
            startThread()
        } catch (e: Exception) {
        }
    }

    fun stopPlay() {
        try {
            destroyThread()
            audioTrack?.let {
                if (it.state == AudioRecord.STATE_INITIALIZED) {
                    it.stop()
                }
                it.release()
            }
            audioTrack = null
        } catch (e: Exception) {
        }
    }
}