package com.example.lastseen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeClickables()
    }

    private fun initializeClickables() {
        val btSignIn : Button = findViewById(R.id.btSignIn)
        val tvSignUp : TextView = findViewById(R.id.tvSignUp)
        val tvForgotten : TextView = findViewById(R.id.tvForgotten)

        btSignIn.setOnClickListener {
            val intent = Intent(this, Itinerary::class.java)

            startActivity(intent)
            finish()
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this, CreateAccount::class.java)

            startActivity(intent)
        }

        tvForgotten.setOnClickListener {
            Toast.makeText(applicationContext, "IMPLEMENT LATER", Toast.LENGTH_SHORT).show()
        }
    }
}