package com.example.calculator.part6

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.R
import com.example.calculator.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val webView = binding.webBrowser
        webView.webViewClient = WebViewClient()

        webView.settings.apply {
            cacheMode = WebSettings.LOAD_DEFAULT
            domStorageEnabled = true
            databaseEnabled = true
            javaScriptEnabled = true
        }

        webView.loadUrl("www.google.com")
        webView.clearCache(true)
        webView.reload()

    }
}