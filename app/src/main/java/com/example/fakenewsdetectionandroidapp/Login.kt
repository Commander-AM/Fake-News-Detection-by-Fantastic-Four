package com.example.fakenewsdetectionandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
//import com.example.fakenewsdetectionandroidapp.R
/**
 * Defines tasks for login window
 * Mostly to switch to mainActivity
 */
class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        /**
         * stores button for classification
         */
        val button = findViewById<Button>(R.id.button2)
        /**
         * switches to main activity when button is clicked
         */
        button.setOnClickListener{
           val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}