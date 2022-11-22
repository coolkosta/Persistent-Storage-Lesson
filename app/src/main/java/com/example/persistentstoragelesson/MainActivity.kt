package com.example.persistentstoragelesson

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.edit
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map


class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var textView: TextView
    private lateinit var tvDataStore: TextView

    private lateinit var sharedPreferences: SharedPreferences
    private val Context.dataStore by preferencesDataStore(name = "settings")

    private object PreferencesKey {
        val userInput = stringPreferencesKey("text_key_data_store")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences(PREFERENCES_STRING, Context.MODE_PRIVATE)

        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)
        tvDataStore = findViewById(R.id.tvDataStore)

        button.setOnClickListener {
            val text = editText.text.toString()
            // Сохранение в SharedPref
            saveString(text)
            // Сохранение в DataStore
            saveStringWithDataStore(text)

        }

        textView.text = getText()

        val userPreferencesFlow: Flow<String> = dataStore.data.map { preferences ->
            preferences[PreferencesKey.userInput] ?: ""
        }

        GlobalScope.async {
            userPreferencesFlow.collect { it ->
              tvDataStore.text = it
            }
        }
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

    private fun saveStringWithDataStore(text: String) {
        GlobalScope.async {
            dataStore.edit { preferences ->
                preferences[PreferencesKey.userInput] = text
            }
        }
    }

    companion object {
        private const val PREFERENCES_STRING = "PREFERENCES_STRING"
    }
}