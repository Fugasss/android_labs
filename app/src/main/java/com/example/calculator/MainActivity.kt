package com.example.calculator

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var musicServiceIntent: Intent? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        musicServiceIntent = Intent(this@MainActivity, MusicPlayerService::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.startServiceButton.setOnClickListener { startMusicService() }
        binding.stopServiceButton.setOnClickListener { stopMusicService() }
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun startMusicService(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(musicServiceIntent)
        else
            startService(musicServiceIntent)

        binding.statusText.text = "Started"

    }

    private fun stopMusicService(){
        stopService(musicServiceIntent)
        binding.statusText.text = "Stopped"
    }
}