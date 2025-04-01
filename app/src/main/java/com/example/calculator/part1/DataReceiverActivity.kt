package com.example.calculator.part1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityDataReceiverBinding

class DataReceiverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataReceiverBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDataReceiverBinding.inflate(layoutInflater)

        val receivedText = intent.extras?.getString("text") ?: "ERROR"

        binding.receivedText.text = receivedText
        binding.textLength.text = "Length: ${receivedText.length}"
        binding.textVowelsCount.text = "Vowels: ${receivedText.filter { isVowel(it) }.length}"
        binding.textConsonantsCount.text = "Consonants: ${receivedText.filter { !isVowel(it) }.length}"

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun isVowel(c: Char): Boolean {
        return when (c.lowercaseChar()) {
            'a', 'e', 'i', 'o', 'u' -> true
            else -> false
        }
    }
}