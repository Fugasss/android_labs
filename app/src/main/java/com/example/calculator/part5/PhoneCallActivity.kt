package com.example.calculator.part5

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityPhoneCallBinding

class PhoneCallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPhoneCallBinding.inflate(layoutInflater)

        binding.callButton.setOnClickListener {

            val phoneNumber = binding.text.text.toString()

            if (phoneNumber.isNotBlank()) {
                checkCallPermissionAndMakeCall(phoneNumber)
            } else {
                Toast.makeText(this, "Enter a phone number!", Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No calling app found!", Toast.LENGTH_SHORT).show()
        }
    }

    private val CALL_PERMISSION_REQUEST = 102

    private fun checkCallPermissionAndMakeCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            makePhoneCall(phoneNumber)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PERMISSION_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val phoneNumber = binding.text.text.toString()
                makePhoneCall(phoneNumber)
            } else {
                Toast.makeText(this, "Permission denied! Cannot make calls.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}