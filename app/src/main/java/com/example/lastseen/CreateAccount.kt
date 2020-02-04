package com.example.lastseen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreateAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account)

        initializeButton()
    }

    private fun initializeButton() {
        val submit : Button = findViewById(R.id.submit)

        submit.setOnClickListener {
            if (validateAccountCreation()) {
                finish()
            }
        }
    }

    private fun validateAccountCreation() : Boolean {
        val firstName : EditText = findViewById(R.id.FirstName)
        val lastName : EditText = findViewById(R.id.LastName)
        val dateOfBirth : EditText = findViewById(R.id.DateofBirth)
        val address : EditText = findViewById(R.id.Address)
        val phoneNumber : EditText = findViewById(R.id.PhoneNumber)

        if (TextUtils.isEmpty(firstName.text) || TextUtils.isEmpty(lastName.text) ||
            TextUtils.isEmpty(dateOfBirth.text) || TextUtils.isEmpty(address.text) ||
            TextUtils.isEmpty(phoneNumber.text)) {

            Toast.makeText(applicationContext, "REQUIRED FIELDS CANNOT BE EMPTY", Toast.LENGTH_SHORT).show()

            return false
        }

        //TODO
        //CHECK IF ACCOUNT WAS SUCCESSFULLY CREATED SERVER SIDE

        return true
    }
}