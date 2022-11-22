package com.example.persistentstoragelesson

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var textView: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences(PREFERENCES_STRING, Context.MODE_PRIVATE)

        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)

        button.setOnClickListener {
            val text = editText.text.toString()
            saveString(text)
        }

        textView.text = getText()
    }

    private fun saveString (text: String) {
        sharedPreferences.edit {
            putString("text_key", text)
        }
    }

    private fun getText(): String {
        val savedText = sharedPreferences.getString("text_key", "No Result")
        return savedText ?: ""
    }

    companion object {
        private const val PREFERENCES_STRING = "PREFERENCES_STRING"
    }
}