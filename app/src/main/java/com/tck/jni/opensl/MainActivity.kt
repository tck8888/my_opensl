package com.tck.jni.opensl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.tck.jni.opensl.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var audioControl: AudioControl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        audioControl = AudioControl()

        binding.btnStartPlay.setOnClickListener {
            audioControl.play("${cacheDir}${File.separator}mydream.pcm")

        }
    }


}