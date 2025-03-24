package com.example.calculator

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlin.random.Random

class CustomBackgroundService : Service() {
    private lateinit var TAG: String

    private var isGenerationEnabled = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        TAG = CustomBackgroundService::class.simpleName ?: "BG SERVICE"

        Toast.makeText(applicationContext, "Service $TAG Started", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "$TAG started...")
        Log.i(TAG, "$TAG Thread ID is " + Thread.currentThread().id)

        isGenerationEnabled = true
        Thread { startGeneration() }.start()

        return START_STICKY

    }

    private fun startGeneration() {
        val asciiStart = 33
        val asciiEnd = 125

        while (isGenerationEnabled) {
            try {
                val asciiSymbol = Random.nextInt(asciiStart, asciiEnd + 1)
                val byteRepresentation = asciiSymbol.toByte().toString(2)

                val broadcastDataIntent = Intent("com.example.action.UPDATE_DATA")
                broadcastDataIntent.setPackage(packageName)
                broadcastDataIntent.putExtra("byteSymbol", byteRepresentation)

                sendBroadcast(broadcastDataIntent)

                Thread.sleep(50)
            } catch (e: InterruptedException) {
                Log.i(TAG, "Thread Interrupted")
            }
        }
    }

    private fun stopGeneration() {
        isGenerationEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        stopGeneration()
        Toast.makeText(applicationContext, "Service $TAG Stopped", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "$TAG Destroyed...");

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}