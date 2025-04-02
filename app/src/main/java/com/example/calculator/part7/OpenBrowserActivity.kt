package com.example.calculator.part7

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.R
import com.example.calculator.databinding.ActivityOpenBrowserBinding

class OpenBrowserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOpenBrowserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOpenBrowserBinding.inflate(layoutInflater)

        binding.openButton.setOnClickListener { openBrowser(binding.text.text.toString()) }

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun openBrowser(url: String) {

        var newUrl = url

        if(!newUrl.startsWith("http")){
            newUrl = "http://${newUrl}"
        }

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newUrl))

        startActivity(intent)
    }
}