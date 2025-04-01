package com.example.calculator

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import org.json.JSONObject

data class Person(val id: Int, val name: String, val age: Int)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("PersonPrefs", Context.MODE_PRIVATE)

        val idText =     binding.idText
        val nameText =   binding.nameText
        val ageText =    binding.ageText
        val saveButton = binding.saveButton
        val loadButton = binding.loadButton

        saveButton.setOnClickListener {
            saveButtonClick(idText, nameText, ageText)
        }

        loadButton.setOnClickListener {
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
            savePerson(person)
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
        val person = loadPerson()
        if (person != null) {
            idText.setText(person.id.toString())
            nameText.setText(person.name)
            ageText.setText(person.age.toString())
            Toast.makeText(this, "Person loaded!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun savePerson(person: Person) {
        val editor = sharedPreferences.edit()
        val jsonObject = JSONObject()
        jsonObject.put("id", person.id)
        jsonObject.put("name", person.name)
        jsonObject.put("age", person.age)
        editor.putString("person", jsonObject.toString())
        editor.apply()
    }

    private fun loadPerson(): Person? {
        val personString = sharedPreferences.getString("person", null) ?: return null
        val jsonObject = JSONObject(personString)
        return Person(jsonObject.getInt("id"), jsonObject.getString("name"), jsonObject.getInt("age"))
    }
}
