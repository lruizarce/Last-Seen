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
    private var toggleEmergencyContact2Edit = false

    private lateinit var updateInformationLayout : View
    private lateinit var inputMethodManager : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_information)

        updateInformationLayout = findViewById(R.id.update_information_layout)
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        initializeChangeNameButton()
        initializeChangeAddressButton()
        initializeChangePhoneNumberButton()
        initializeChangeEmergencyContact1Button()
        initializeChangeEmergencyContact2Button()
    }

    private fun initializeChangeNameButton() {
        val changeNameButton : Button = findViewById(R.id.change_name_button)
        val firstNameInput : EditText = findViewById(R.id.first_name_input)
        val lastNameInput : EditText = findViewById(R.id.last_name_input)
        val name : TextView = findViewById(R.id.name)

        changeNameButton.setOnClickListener {
            if (toggleNameEdit) {
                toggleNameEdit = !toggleNameEdit
                clearFocus(firstNameInput, lastNameInput)
                hideKeyboard(updateInformationLayout)
                name.text = firstNameInput.text.append(" " + lastNameInput.text)
                toggleVisibility(name, firstNameInput, lastNameInput)
            } else {
                toggleNameEdit = !toggleNameEdit
                firstNameInput.setText(name.text.split(" ")[0])
                lastNameInput.setText(name.text.split(" ")[1])
                toggleVisibility(name, firstNameInput, lastNameInput)
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

        changeAddressButton.setOnClickListener {
            if (toggleAddressEdit) {
                toggleAddressEdit = !toggleAddressEdit
                clearFocus(streetAddressInput, cityAddressInput, stateAddressInput, zipCodeAddressInput)
                hideKeyboard(updateInformationLayout)
                address.text = streetAddressInput.text.append("\n")
                    .append(cityAddressInput.text.toString() + ", "
                            + stateAddressInput.text.toString() + " "
                            + zipCodeAddressInput.text.toString())
                toggleVisibility(address, streetAddressInput, cityAddressInput, stateAddressInput, zipCodeAddressInput)
            } else {
                toggleAddressEdit = !toggleAddressEdit

                val street : String = address.text.split("\n")[0]
                val cityStateZip : String = address.text.split("\n")[1]
                val city : String = cityStateZip.split(" ")[0].replace(",", "")
                val state : String = cityStateZip.split(" ")[1]
                val zip : String = cityStateZip.split(" ")[2]

                streetAddressInput.setText(street)
                cityAddressInput.setText(city)
                stateAddressInput.setText(state)
                zipCodeAddressInput.setText(zip)

                toggleVisibility(address, streetAddressInput, cityAddressInput, stateAddressInput, zipCodeAddressInput)
            }
        }
    }

    private fun initializeChangePhoneNumberButton() {
        val changePhoneNumberButton : Button = findViewById(R.id.change_phone_number_button)
        val phoneNumberInput : EditText = findViewById(R.id.phone_number_input)
        val phoneNumber : TextView = findViewById(R.id.phone_number)

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

    private fun initializeChangeEmergencyContact1Button() {
        val changeEmergencyContact1Button : Button = findViewById(R.id.change_emergency_contact_1_button)
        val emergencyContact1FirstNameInput : EditText = findViewById(R.id.emergency_contact_1_first_name_input)
        val emergencyContact1LastNameInput : EditText = findViewById(R.id.emergency_contact_1_last_name_input)
        val emergencyContact1PhoneNumberInput : EditText = findViewById(R.id.emergency_contact_1_phone_number_input)
        val emergencyContact1 : TextView = findViewById(R.id.emergency_contact_1)

        changeEmergencyContact1Button.setOnClickListener {
            if (toggleEmergencyContact1Edit) {
                toggleEmergencyContact1Edit = !toggleEmergencyContact1Edit
                clearFocus(emergencyContact1FirstNameInput, emergencyContact1LastNameInput, emergencyContact1PhoneNumberInput)
                hideKeyboard(updateInformationLayout)

                emergencyContact1.text = emergencyContact1FirstNameInput.text.append(" "
                        + emergencyContact1LastNameInput.text.toString()
                        + "\n" + emergencyContact1PhoneNumberInput.text)

                toggleVisibility(emergencyContact1, emergencyContact1FirstNameInput, emergencyContact1LastNameInput, emergencyContact1PhoneNumberInput)
            } else {
                toggleEmergencyContact1Edit = !toggleEmergencyContact1Edit

                val fullName = emergencyContact1.text.split("\n")[0]
                val firstName = fullName.split(" ")[0]
                val lastName = fullName.split(" ")[1]
                val phoneNumber = emergencyContact1.text.split("\n")[1]

                emergencyContact1FirstNameInput.setText(firstName)
                emergencyContact1LastNameInput.setText(lastName)
                emergencyContact1PhoneNumberInput.setText(phoneNumber)

                toggleVisibility(emergencyContact1, emergencyContact1FirstNameInput, emergencyContact1LastNameInput, emergencyContact1PhoneNumberInput)
            }
        }
    }

    private fun initializeChangeEmergencyContact2Button() {
        val changeEmergencyContact2Button : Button = findViewById(R.id.change_emergency_contact_2_button)
        val emergencyContact2FirstNameInput : EditText = findViewById(R.id.emergency_contact_2_first_name_input)
        val emergencyContact2LastNameInput : EditText = findViewById(R.id.emergency_contact_2_last_name_input)
        val emergencyContact2PhoneNumberInput : EditText = findViewById(R.id.emergency_contact_2_phone_number_input)
        val emergencyContact2 : TextView = findViewById(R.id.emergency_contact_2)

        changeEmergencyContact2Button.setOnClickListener {
            if (toggleEmergencyContact2Edit) {
                toggleEmergencyContact2Edit = !toggleEmergencyContact2Edit
                clearFocus(emergencyContact2FirstNameInput, emergencyContact2LastNameInput, emergencyContact2PhoneNumberInput)
                hideKeyboard(updateInformationLayout)

                emergencyContact2.text = emergencyContact2FirstNameInput.text.append(" "
                        + emergencyContact2LastNameInput.text.toString()
                        + "\n" + emergencyContact2PhoneNumberInput.text)

                toggleVisibility(emergencyContact2, emergencyContact2FirstNameInput, emergencyContact2LastNameInput, emergencyContact2PhoneNumberInput)
            } else {
                toggleEmergencyContact2Edit = !toggleEmergencyContact2Edit

                val fullName = emergencyContact2.text.split("\n")[0]
                val firstName = fullName.split(" ")[0]
                val lastName = fullName.split(" ")[1]
                val phoneNumber = emergencyContact2.text.split("\n")[1]

                emergencyContact2FirstNameInput.setText(firstName)
                emergencyContact2LastNameInput.setText(lastName)
                emergencyContact2PhoneNumberInput.setText(phoneNumber)

                toggleVisibility(emergencyContact2, emergencyContact2FirstNameInput, emergencyContact2LastNameInput, emergencyContact2PhoneNumberInput)
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
}
