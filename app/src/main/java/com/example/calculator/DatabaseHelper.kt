package com.example.calculator

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "PersonDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE Person (id INTEGER PRIMARY KEY, name TEXT, age INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Person")
        onCreate(db)
    }

    fun savePerson(person: Person) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("id", person.id)
        values.put("name", person.name)
        values.put("age", person.age)
        db.insertWithOnConflict("Person", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    fun loadPerson(): Person? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Person LIMIT 1", null)

        return if (cursor.moveToFirst()) {
            val person = Person(cursor.getInt(0), cursor.getString(1), cursor.getInt(2))
            cursor.close()
            db.close()
            person
        } else {
            cursor.close()
            db.close()
            null
        }
    }
}