package com.example.calculator

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var backgroundServiceIntent: Intent
    private lateinit var broadcastReceiver: BroadcastReceiver

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.startServiceButton.setOnClickListener { startBackgroundService() }
        binding.stopServiceButton.setOnClickListener { stopBackgroundService() }
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val status = binding.statusText

        broadcastReceiver = CustomBroadcastReceiver{ data ->
            status.text = if(status.text.length < 500) "${status.text}${data}" else ""
        }
        backgroundServiceIntent = Intent(applicationContext, CustomBackgroundService::class.java)
    }

    private var isReceiverRegistered = false

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun startBackgroundService() {
        val filter = IntentFilter()
        filter.addAction("com.example.action.UPDATE_DATA")

        if (!isReceiverRegistered) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                registerReceiver(broadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
            } else {
                registerReceiver(broadcastReceiver, filter)
            }
            isReceiverRegistered = true
        }

        startService(backgroundServiceIntent)
    }

    private fun stopBackgroundService() {
        if (isReceiverRegistered) {
            unregisterReceiver(broadcastReceiver)
            isReceiverRegistered = false
        }

        stopService(backgroundServiceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopBackgroundService()
    }
}