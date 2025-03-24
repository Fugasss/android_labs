package com.example.calculator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.TextView

class CustomBroadcastReceiver(
    private val onDataReceived: (String) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val data = intent.getStringExtra("byteSymbol")
            data?.let { onDataReceived(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}