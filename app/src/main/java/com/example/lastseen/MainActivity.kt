package com.example.lastseen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeClickables()
    }

    private fun initializeClickables() {
        val btSignIn : Button = findViewById(R.id.login_sign_in_button)
        val tvSignUp : TextView = findViewById(R.id.login_sign_up_link)
        val tvForgotten : TextView = findViewById(R.id.login_forgot_password_link)

        btSignIn.setOnClickListener {
            if (validateSignIn()) {
                val intent = Intent(this, Itinerary::class.java)

                startActivity(intent)
                finish()
            }
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this, CreateAccount::class.java)

            startActivity(intent)
        }

        tvForgotten.setOnClickListener {
            Toast.makeText(applicationContext, "IMPLEMENT LATER", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateSignIn() : Boolean {
        val username : EditText = findViewById(R.id.login_username_input)
        val password : EditText = findViewById(R.id.login_password_input)

        if (TextUtils.isEmpty(username.text) || TextUtils.isEmpty(password.text)) {
            Toast.makeText(applicationContext, "USERNAME AND PASSWORD CANNOT BE EMPTY", Toast.LENGTH_SHORT).show()

            return false
        }

        return true
    }
}