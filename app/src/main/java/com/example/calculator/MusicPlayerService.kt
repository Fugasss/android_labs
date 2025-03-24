package com.example.calculator

import android.R as NativeR
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat

class MusicPlayerService : Service() {
    private var musicPlayer: MediaPlayer? = null
    private val CHANNEL_ID = "channelId"
    private var notificationManager: NotificationManager? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val serviceName = MusicPlayerService::class.simpleName

        Toast.makeText(applicationContext, "$serviceName started!", Toast.LENGTH_SHORT).show()

        Log.i(serviceName, "Music service started on thread: ${Thread.currentThread().id}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val offerChannelName = "Service Channel"
            val offerChannelDescription = "Music Channel"
            val offerChannelImportance = NotificationManager.IMPORTANCE_DEFAULT

            val notificationChannel =
                NotificationChannel(CHANNEL_ID, offerChannelName, offerChannelImportance)
            notificationChannel.description = offerChannelDescription
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(NativeR.drawable.ic_media_play)
            .setContentTitle("My Music Player")
            .setContentText("Music is playing")
            .build()

        startForeground(1, notification)

        startMusic()

        return START_STICKY
    }

    override fun onCreate() {
        Toast.makeText(this, "Music Service Created", Toast.LENGTH_SHORT).show()
        musicPlayer = MediaPlayer.create(this, R.raw.meow_song)
        musicPlayer?.isLooping = false
    }

    override fun onDestroy() {
        Toast.makeText(this, "Music Service Stopped", Toast.LENGTH_SHORT).show()
        stopMusic()
    }

    private fun startMusic() {
        musicPlayer?.start()
    }

    private fun stopMusic() {
        musicPlayer?.stop()
    }
}