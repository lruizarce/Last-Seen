package com.example.lastseen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.w3c.dom.Text

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
        val streetAddress : EditText = findViewById(R.id.StreetAddress)
        val city : EditText = findViewById(R.id.City)
        val state : EditText = findViewById(R.id.State)
        val zipCode : EditText = findViewById(R.id.ZipCode)
        val phoneNumber : EditText = findViewById(R.id.PhoneNumber)

        if (TextUtils.isEmpty(firstName.text) || TextUtils.isEmpty(lastName.text) ||
            TextUtils.isEmpty(dateOfBirth.text) || TextUtils.isEmpty(streetAddress.text) ||
            TextUtils.isEmpty(city.text) || TextUtils.isEmpty(state.text) ||
            TextUtils.isEmpty(zipCode.text) || TextUtils.isEmpty(phoneNumber.text)) {

            Toast.makeText(applicationContext, "REQUIRED FIELDS CANNOT BE EMPTY", Toast.LENGTH_SHORT).show()

            return false
        }

        //TODO
        //CHECK IF ACCOUNT WAS SUCCESSFULLY CREATED SERVER SIDE

        return true
    }
}