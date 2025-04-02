package com.example.calculator

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageView: ImageView
    private lateinit var videoView: VideoView
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        imageView = binding.imageView
        videoView = binding.videoView

        binding.selectFileButton.setOnClickListener { checkPermissionsAndSelectFile() }

        setContentView(binding.root)

    }

    private fun checkPermissionsAndSelectFile() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        } else {
            selectFile()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                selectFile()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    handleFile(uri)
                }
            }
        }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        filePickerLauncher.launch(intent)
    }

    private fun handleFile(uri: Uri) {
        val fileType = contentResolver.getType(uri) ?: ""
        when {
            fileType.startsWith("image/") -> displayImage(uri)
            fileType.startsWith("audio/") -> playAudio(uri)
            fileType.startsWith("video/") -> playVideo(uri)
            else -> Toast.makeText(this, "Unsupported file type", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayImage(uri: Uri) {
        imageView.visibility = ImageView.VISIBLE
        videoView.visibility = VideoView.GONE
        imageView.setImageURI(uri)
        mediaPlayer?.release()
    }

    private fun playAudio(uri: Uri) {
        imageView.visibility = ImageView.GONE
        videoView.visibility = VideoView.GONE
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(applicationContext, uri)
            prepare()
            start()
        }
    }

    private fun playVideo(uri: Uri) {
        videoView.visibility = VideoView.VISIBLE
        imageView.visibility = ImageView.GONE

        val mediaController = android.widget.MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.setBackgroundColor(Color.TRANSPARENT)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.setOnPreparedListener { it.start() }
        mediaPlayer?.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}