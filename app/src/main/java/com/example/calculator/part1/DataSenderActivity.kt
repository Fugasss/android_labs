package com.example.calculator.part1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.R
import com.example.calculator.databinding.ActivityDataSenderBinding

class DataSenderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataSenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDataSenderBinding.inflate(layoutInflater)

        binding.callButton.setOnClickListener { openReceiverActivity() }

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun openReceiverActivity() {
        val text = binding.text.text

        val intent = Intent(this, DataReceiverActivity::class.java)
        intent.putExtra("text", text.toString())

        startActivity(intent)
    }
}