package com.example.lastseen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class UpdateInformation : AppCompatActivity() {
    private var toggleNameEdit = false
    private var toggleAddressEdit = false
    private var togglePhoneEdit = false
    private var toggleEmergencyContact1Edit = false

    private lateinit var inputMethodManager : InputMethodManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_information)

        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        initializeChangeNameButton()
        initializeChangeAddressButton()
        initializeChangePhoneNumberButton()
        initializeChangeEmergencyContact1Button()
    }

    private fun initializeChangeNameButton() {
        val changeNameButton : Button = findViewById(R.id.change_name_button)
        val firstNameInput : EditText = findViewById(R.id.first_name_input)
        val lastNameInput : EditText = findViewById(R.id.last_name_input)
        val name : TextView = findViewById(R.id.name)

        changeNameButton.setOnClickListener {
            if (toggleNameEdit) {
                toggleNameEdit = !toggleNameEdit
                firstNameInput.clearFocus()
                lastNameInput.clearFocus()
                hideKeyboard(firstNameInput)
                hideKeyboard(lastNameInput)
                name.text = firstNameInput.text.append(" " + lastNameInput.text)
                name.visibility = View.VISIBLE
                firstNameInput.visibility = View.INVISIBLE
                lastNameInput.visibility = View.INVISIBLE
            } else {
                toggleNameEdit = !toggleNameEdit
                name.visibility = View.INVISIBLE
                firstNameInput.setText(name.text.split(" ")[0])
                lastNameInput.setText(name.text.split(" ")[1])
                firstNameInput.visibility = View.VISIBLE
                lastNameInput.visibility = View.VISIBLE
                firstNameInput.requestFocus()
                showKeyboard(firstNameInput)
            }
        }
    }

    private fun initializeChangeAddressButton() {
        val changeAddressButton : Button = findViewById(R.id.change_address_button)
        val streetAddressInput : EditText = findViewById(R.id.address_street_input)
        val cityAddressInput : EditText = findViewById(R.id.address_city_input)
        val stateAddressInput : EditText = findViewById(R.id.address_state_input)
        val zipCodeAddressInput : EditText = findViewById(R.id.address_zip_code_input)
        val address : TextView = findViewById(R.id.address)
    }

    private fun initializeChangePhoneNumberButton() {
        val changePhoneNumberButton : Button = findViewById(R.id.change_phone_number_button)
        val phoneNumberInput : EditText = findViewById(R.id.phone_number_input)
        val phoneNumber : TextView = findViewById(R.id.phone_number)

        changePhoneNumberButton.setOnClickListener {
            if (togglePhoneEdit) {
                togglePhoneEdit = !togglePhoneEdit
                phoneNumberInput.clearFocus()
                hideKeyboard(phoneNumberInput)
                phoneNumber.text = phoneNumberInput.text
                phoneNumber.visibility = View.VISIBLE
                phoneNumberInput.visibility = View.INVISIBLE
            } else {
                togglePhoneEdit = !togglePhoneEdit
                phoneNumber.visibility = View.INVISIBLE
                phoneNumberInput.visibility = View.VISIBLE
                phoneNumberInput.requestFocus()
                showKeyboard(phoneNumberInput)
            }
        }

    }

    private fun initializeChangeEmergencyContact1Button() {
        val changeEmergencyContact1Button : Button = findViewById(R.id.change_emergency_contact_1_button)
    }

    private fun hideKeyboard(view : View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view : View) {
        inputMethodManager.showSoftInput(view, SHOW_IMPLICIT)
    }
}
