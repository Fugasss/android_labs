package com.example.calculator

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding


data class Person(val id: Int, val name: String, val age: Int)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        val idText = binding.idText
        val nameText = binding.nameText
        val ageText = binding.ageText

        binding.saveButton.setOnClickListener {
            saveButtonClick(idText, nameText, ageText)
        }

        binding.loadButton.setOnClickListener {
            loadButtonClick(idText, nameText, ageText)
        }
    }

    private fun saveButtonClick(
        idText: EditText,
        nameText: EditText,
        ageText: EditText
    ) {
        val id = idText.text.toString().toIntOrNull()
        val name = nameText.text.toString()
        val age = ageText.text.toString().toIntOrNull()

        if (id != null && age != null && name.isNotEmpty()) {
            val person = Person(id, name, age)
            dbHelper.savePerson(person)

            Toast.makeText(this, "Person saved!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadButtonClick(
        idText: EditText,
        nameText: EditText,
        ageText: EditText
    ) {
        val person = dbHelper.loadPerson()
        if (person != null) {
            idText.setText(person.id.toString())
            nameText.setText(person.name)
            ageText.setText(person.age.toString())

            Toast.makeText(this, "Person loaded!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show()
        }
    }
}
