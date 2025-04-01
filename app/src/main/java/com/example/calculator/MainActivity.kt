package com.example.calculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.part1.DataSenderActivity
import com.example.calculator.part2.OpenCameraActivity
import com.example.calculator.part3.ContactsListActivity
import com.example.calculator.part4.SendMessageActivity
import com.example.calculator.part5.PhoneCallActivity
import com.example.calculator.part6.WebViewActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.part1.setOnClickListener { startActivity(Intent(this, DataSenderActivity::class.java)) }
        binding.part2.setOnClickListener { startActivity(Intent(this, OpenCameraActivity::class.java)) }
        binding.part3.setOnClickListener { startActivity(Intent(this, ContactsListActivity::class.java)) }
        binding.part4.setOnClickListener { startActivity(Intent(this, SendMessageActivity::class.java)) }
        binding.part5.setOnClickListener { startActivity(Intent(this, PhoneCallActivity::class.java)) }
        binding.part6.setOnClickListener { startActivity(Intent(this, WebViewActivity::class.java)) }
//        binding.part7.setOnClickListener { startActivity(Intent(this, DataSenderActivity::class.java)) }

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}