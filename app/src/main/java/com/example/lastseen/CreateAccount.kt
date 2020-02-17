package com.example.lastseen

import android.bluetooth.BluetoothAdapter
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
        val submit : Button = findViewById(R.id.create_account_submit_button)

        submit.setOnClickListener {
            if (validateAccountCreation()) {
                finish()
            }
        }
    }

    private fun validateAccountCreation() : Boolean {
        val firstName : EditText = findViewById(R.id.create_account_first_name_input)
        val lastName : EditText = findViewById(R.id.create_account_last_name_input)
        val dateOfBirth : EditText = findViewById(R.id.create_account_date_of_birth_input)
        val streetAddress : EditText = findViewById(R.id.create_account_street_address_input)
        val city : EditText = findViewById(R.id.create_account_city_input)
        val state : EditText = findViewById(R.id.create_account_state_input)
        val zipCode : EditText = findViewById(R.id.create_account_zip_code_input)
        val phoneNumber : EditText = findViewById(R.id.create_account_phone_number_input)
        val macAddress : EditText = findViewById(R.id.create_account_mac_address_input)
        val email : EditText = findViewById(R.id.create_account_email_input)
        val password : EditText = findViewById(R.id.create_account_password_input)
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (TextUtils.isEmpty(firstName.text) || TextUtils.isEmpty(lastName.text) ||
            TextUtils.isEmpty(dateOfBirth.text) || TextUtils.isEmpty(streetAddress.text) ||
            TextUtils.isEmpty(city.text) || TextUtils.isEmpty(state.text) ||
            TextUtils.isEmpty(zipCode.text) || TextUtils.isEmpty(phoneNumber.text) ||
            TextUtils.isEmpty(macAddress.text) || TextUtils.isEmpty(email.text) ||
            TextUtils.isEmpty(password.text)) {

            Toast.makeText(applicationContext, "REQUIRED FIELDS CANNOT BE EMPTY", Toast.LENGTH_SHORT).show()

            return false
        }

        //TODO
        //CHECK IF ACCOUNT WAS SUCCESSFULLY CREATED SERVER SIDE

        return true
    }
}