package com.tck.jni.audiorecord

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.tck.jni.audiorecord.databinding.ActivityAudioRecordTrainBinding
import java.io.File
import java.io.FileOutputStream

class AudioRecordTrainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudioRecordTrainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioRecordTrainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartRecord.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startRecordAudio()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    1000
                )
            }
        }
    }


    fun getSavePath(): String {
        val pcmFile = File("${cacheDir}${File.separator}10.pcm")
        if (pcmFile.exists()) {
            pcmFile.delete()
        }
        pcmFile.createNewFile()
        return pcmFile.absolutePath
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRecordAudio()
        } else {
            Toast.makeText(this, "请打开录音权限", Toast.LENGTH_SHORT).show()
        }
    }

    private var audioRecordControl: AudioRecordControl? = null
    private fun startRecordAudio() {
        if (audioRecordControl == null) {
            audioRecordControl = AudioRecordControl(getSavePath())
        }

        audioRecordControl?.let {
            if (it.isRecording) {
                it.isRecording = false
            } else {
                MyExecutors.instance.execute(it)
            }
        }
    }

}

class AudioRecordControl(var url: String) : Runnable {

    companion object {
        const val DEFAULT_BUFFER_SIZE = 2048
    }

    private var audioRecord: AudioRecord? = null
    private val buffer = ByteArray(DEFAULT_BUFFER_SIZE)

    @Volatile
    var isRecording = false

    private fun startRecordAudio() {
        try {
            FileOutputStream(File(url)).use {
                //声音来源
                val audioSource = MediaRecorder.AudioSource.MIC
                //采样率
                val sampleRateInHz = 44100
                //音频通道的配置
                val channelConfig = AudioFormat.CHANNEL_OUT_STEREO
                //返回音频数据的格式
                val audioFormat = AudioFormat.ENCODING_PCM_16BIT
                //AudioRecord能接受的最小的buffer大小
                val bufferSizeInBytes =
                    AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat)

                audioRecord = AudioRecord(
                    audioSource,
                    sampleRateInHz,
                    channelConfig,
                    audioFormat,
                    bufferSizeInBytes.coerceAtLeast(DEFAULT_BUFFER_SIZE)
                )
                audioRecord?.startRecording()
                isRecording = true


                while (isRecording) {
                    val count = audioRecord?.read(buffer, 0, DEFAULT_BUFFER_SIZE) ?: 0
                    Log.d("tck6666", "count:$count")
                    if (count > 0) {
                        it.write(buffer, 0, count)
                        //也可以在这里对音频数据进行处理，压缩、直播等
                    }
                }
                audioRecord?.stop()
                audioRecord?.release()
                audioRecord = null
            }
        } catch (e: Exception) {
        } finally {
            audioRecord?.release()
            audioRecord = null
        }
    }

    override fun run() {
        startRecordAudio()
    }
}