package com.example.lastseen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class UpdateInformation : AppCompatActivity() {
    private var toggleNameEdit = false
    private var toggleAddressEdit = false
    private var togglePhoneEdit = false
    private var toggleEmergencyContactEdit = false

    private lateinit var updateInformationLayout : View
    private lateinit var inputMethodManager : InputMethodManager

    private lateinit var changeNameButton : Button
    private lateinit var firstNameInput : EditText
    private lateinit var lastNameInput : EditText
    private lateinit var name : TextView

    private lateinit var changeAddressButton : Button
    private lateinit var streetAddressInput : EditText
    private lateinit var cityAddressInput : EditText
    private lateinit var stateAddressInput : EditText
    private lateinit var zipCodeAddressInput : EditText
    private lateinit var address : TextView

    private lateinit var changePhoneNumberButton : Button
    private lateinit var phoneNumberInput : EditText
    private lateinit var phoneNumber : TextView

    private lateinit var changeEmergencyContactButton : Button
    private lateinit var emergencyContactFirstNameInput : EditText
    private lateinit var emergencyContactLastNameInput : EditText
    private lateinit var emergencyContactPhoneNumberInput : EditText
    private lateinit var emergencyContact : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_information)

        updateInformationLayout = findViewById(R.id.update_information_layout)
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        initializeAllViewsAndButtons()
        setOnClickListenerChangeNameButton()
        setOnClickListenerChangeAddressButton()
        setOnClickListenerChangePhoneNumberButton()
        setOnClickListenerChangeEmergencyContactButton()
        setOnClickListenerSubmitButton()
    }

    private fun initializeAllViewsAndButtons() {
        changeNameButton = findViewById(R.id.change_name_button)
        firstNameInput = findViewById(R.id.first_name_input)
        lastNameInput = findViewById(R.id.last_name_input)
        name = findViewById(R.id.name)

        nameTextViewToEditText()

        changeAddressButton = findViewById(R.id.change_address_button)
        streetAddressInput = findViewById(R.id.address_street_input)
        cityAddressInput = findViewById(R.id.address_city_input)
        stateAddressInput = findViewById(R.id.address_state_input)
        zipCodeAddressInput = findViewById(R.id.address_zip_code_input)
        address = findViewById(R.id.address)

        addressTextViewToEditText()

        changePhoneNumberButton = findViewById(R.id.change_phone_number_button)
        phoneNumberInput = findViewById(R.id.phone_number_input)
        phoneNumber = findViewById(R.id.phone_number)

        phoneNumberTextViewToEditText()

        changeEmergencyContactButton = findViewById(R.id.change_emergency_contact_button)
        emergencyContactFirstNameInput = findViewById(R.id.emergency_contact_first_name_input)
        emergencyContactLastNameInput = findViewById(R.id.emergency_contact_last_name_input)
        emergencyContactPhoneNumberInput = findViewById(R.id.emergency_contact_phone_number_input)
        emergencyContact = findViewById(R.id.emergency_contact)
    }

    private fun setOnClickListenerChangeNameButton() {
        changeNameButton.setOnClickListener {
            if (toggleNameEdit) {
                toggleNameEdit = !toggleNameEdit
                clearFocus(firstNameInput, lastNameInput)
                hideKeyboard(updateInformationLayout)
                name.text = firstNameInput.text.toString() + " " + lastNameInput.text.toString()
                toggleVisibility(name, firstNameInput, lastNameInput)
            } else {
                toggleNameEdit = !toggleNameEdit
                nameTextViewToEditText()
                toggleVisibility(name, firstNameInput, lastNameInput)
            }
        }
    }

    private fun setOnClickListenerChangeAddressButton() {
        changeAddressButton.setOnClickListener {
            if (toggleAddressEdit) {
                toggleAddressEdit = !toggleAddressEdit
                clearFocus(streetAddressInput, cityAddressInput, stateAddressInput, zipCodeAddressInput)
                hideKeyboard(updateInformationLayout)
                address.text = streetAddressInput.text.toString() +
                        "\n" + cityAddressInput.text.toString() +
                        ", " + stateAddressInput.text.toString() +
                        " " + zipCodeAddressInput.text.toString()
                toggleVisibility(address, streetAddressInput, cityAddressInput, stateAddressInput, zipCodeAddressInput)
            } else {
                toggleAddressEdit = !toggleAddressEdit
                addressTextViewToEditText()
                toggleVisibility(address, streetAddressInput, cityAddressInput, stateAddressInput, zipCodeAddressInput)
            }
        }
    }

    private fun setOnClickListenerChangePhoneNumberButton() {
        changePhoneNumberButton.setOnClickListener {
            if (togglePhoneEdit) {
                togglePhoneEdit = !togglePhoneEdit
                clearFocus(phoneNumberInput)
                hideKeyboard(updateInformationLayout)
                phoneNumber.text = phoneNumberInput.text
                toggleVisibility(phoneNumber, phoneNumberInput)
            } else {
                togglePhoneEdit = !togglePhoneEdit
                toggleVisibility(phoneNumber, phoneNumberInput)
                phoneNumberInput.requestFocus()
                showKeyboard(phoneNumberInput)
            }
        }

    }

    private fun setOnClickListenerChangeEmergencyContactButton() {
        changeEmergencyContactButton.setOnClickListener {
            if (toggleEmergencyContactEdit) {
                toggleEmergencyContactEdit = !toggleEmergencyContactEdit
                clearFocus(emergencyContactFirstNameInput, emergencyContactLastNameInput, emergencyContactPhoneNumberInput)
                hideKeyboard(updateInformationLayout)
                emergencyContact.text = emergencyContactFirstNameInput.text.toString() +
                        " " + emergencyContactLastNameInput.text.toString() +
                        "\n" + emergencyContactPhoneNumberInput.text.toString()
                toggleVisibility(emergencyContact, emergencyContactFirstNameInput, emergencyContactLastNameInput, emergencyContactPhoneNumberInput)
            } else {
                toggleEmergencyContactEdit = !toggleEmergencyContactEdit

                val fullName = emergencyContact.text.split("\n")[0]
                val firstName = fullName.split(" ")[0]
                val lastName = fullName.split(" ")[1]
                val phoneNumber = emergencyContact.text.split("\n")[1]

                emergencyContactFirstNameInput.setText(firstName)
                emergencyContactLastNameInput.setText(lastName)
                emergencyContactPhoneNumberInput.setText(phoneNumber)

                toggleVisibility(emergencyContact, emergencyContactFirstNameInput, emergencyContactLastNameInput, emergencyContactPhoneNumberInput)
            }
        }
    }

    private fun setOnClickListenerSubmitButton() {
        val submitButton : Button = findViewById(R.id.update_information_submit_button)

        submitButton.setOnClickListener {
            if (requiredFieldEmpty()) {
                Toast.makeText(applicationContext, "The required fields are missing some information", Toast.LENGTH_SHORT).show()
            } else {
                finish()
            }
        }
    }

    private fun toggleVisibility(vararg views : View) {
        for (view : View in views) {
            if (view.visibility == View.VISIBLE) {
                view.visibility = View.INVISIBLE
            } else {
                view.visibility = View.VISIBLE
            }
        }
    }

    private fun clearFocus(vararg editTexts : EditText) {
        for (editText : EditText in editTexts) {
            editText.clearFocus()
        }
    }

    private fun hideKeyboard(view : View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
    }

    private fun showKeyboard(view : View) {
        inputMethodManager.showSoftInput(view, SHOW_IMPLICIT)
    }

    private fun requiredFieldEmpty() : Boolean {
        if (firstNameInput.text.isEmpty() ||
            lastNameInput.text.isEmpty() ||
            streetAddressInput.text.isEmpty() ||
            cityAddressInput.text.isEmpty() ||
            stateAddressInput.text.isEmpty() ||
            zipCodeAddressInput.text.isEmpty() ||
            phoneNumberInput.text.isEmpty()) {

            return true
        }

        return false
    }

    private fun nameTextViewToEditText() {
        firstNameInput.setText(name.text.split(" ")[0])
        lastNameInput.setText(name.text.split(" ")[1])
    }

    private fun addressTextViewToEditText() {
        val street : String = address.text.split("\n")[0]
        val cityStateZip : String = address.text.split("\n")[1]
        val city : String = cityStateZip.split(" ")[0].replace(",", "")
        val state : String = cityStateZip.split(" ")[1]
        val zip : String = cityStateZip.split(" ")[2]

        streetAddressInput.setText(street)
        cityAddressInput.setText(city)
        stateAddressInput.setText(state)
        zipCodeAddressInput.setText(zip)
    }

    private fun phoneNumberTextViewToEditText() {
        phoneNumberInput.setText(phoneNumber.text)
    }
}
