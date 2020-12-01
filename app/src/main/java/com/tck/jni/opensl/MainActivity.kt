package com.tck.jni.opensl

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.tck.jni.opensl.databinding.ActivityMainBinding
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var audioControl: AudioControl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        audioControl = AudioControl()

        binding.btnStartPlay.setOnClickListener {
           // audioControl.play("${cacheDir}${File.separator}mydream.pcm")
            audioControl.play("${cacheDir}${File.separator}1.pcm")
        }

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
        binding.btnStopRecord.setOnClickListener {
            audioControl.stopRecordAudio()
        }
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

    private fun startRecordAudio() {
        audioControl.startRecordAudio("${cacheDir}${File.separator}1.pcm")
    }


}