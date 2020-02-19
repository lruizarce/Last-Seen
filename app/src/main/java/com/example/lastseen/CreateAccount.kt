package com.example.lastseen

import android.bluetooth.BluetoothAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class CreateAccount : AppCompatActivity() {
    lateinit var firstName : EditText
    lateinit var lastName : EditText
    lateinit var dateOfBirth : EditText
    lateinit var streetAddress : EditText
    lateinit var city : EditText
    lateinit var state : EditText
    lateinit var zipCode : EditText
    lateinit var phoneNumber : EditText
    lateinit var macAddress : EditText
    lateinit var emergencyContactFirstName : EditText
    lateinit var emergencyContactLastName : EditText
    lateinit var emergencyContactPhoneNumber : EditText
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var mBluetoothAdapter : BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account)

        initializeViewsAndAdapters()
        initializeButton()
    }

    private fun initializeViewsAndAdapters() {
        firstName = findViewById(R.id.create_account_first_name_input)
        lastName = findViewById(R.id.create_account_last_name_input)
        dateOfBirth = findViewById(R.id.create_account_date_of_birth_input)
        streetAddress = findViewById(R.id.create_account_street_address_input)
        city = findViewById(R.id.create_account_city_input)
        state = findViewById(R.id.create_account_state_input)
        zipCode = findViewById(R.id.create_account_zip_code_input)
        phoneNumber = findViewById(R.id.create_account_phone_number_input)
        macAddress = findViewById(R.id.create_account_mac_address_input)
        emergencyContactFirstName = findViewById(R.id.create_account_emergency_contact_first_name_input)
        emergencyContactLastName = findViewById(R.id.create_account_emergency_contact_last_name_input)
        emergencyContactPhoneNumber = findViewById(R.id.create_account_emergency_contact_phone_number_input)
        email = findViewById(R.id.create_account_email_input)
        password = findViewById(R.id.create_account_password_input)

        /*****************/
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        /*****************/
    }

    private fun initializeButton() {
        val submit : Button = findViewById(R.id.create_account_submit_button)

        submit.setOnClickListener {
            if (requiredFieldsNotEmpty()) {
                sendPayloadToServer()
            }
        }
    }

    private fun requiredFieldsNotEmpty() : Boolean {
        if (TextUtils.isEmpty(firstName.text)
            || TextUtils.isEmpty(lastName.text)
            || TextUtils.isEmpty(dateOfBirth.text)
            || TextUtils.isEmpty(streetAddress.text)
            || TextUtils.isEmpty(city.text)
            || TextUtils.isEmpty(state.text)
            || TextUtils.isEmpty(zipCode.text)
            || TextUtils.isEmpty(phoneNumber.text)
            || TextUtils.isEmpty(macAddress.text)
            || TextUtils.isEmpty(email.text)
            || TextUtils.isEmpty(password.text))
        {
            Toast.makeText(applicationContext, "REQUIRED FIELDS CANNOT BE EMPTY", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun sendPayloadToServer() {
        val jsonObject = generateJsonPayload()
        val queue = Volley.newRequestQueue(this)
        val urlString = getString(R.string.server_url) + getString(R.string.server_profile)

        val addProfileRequest = object : JsonObjectRequest(Request.Method.POST, urlString, jsonObject,
            Response.Listener {
                 finish()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "COULD NOT CREATE ACCOUNT", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONObject> {
                try {
                    val json = String(response!!.data, charset("UTF-8"))

                    if (json.isEmpty()) {
                        return Response.success(null, HttpHeaderParser.parseCacheHeaders(response))
                    } else {
                        return super.parseNetworkResponse(response)
                    }
                } catch (e : UnsupportedEncodingException) {
                    return Response.error(ParseError(e))
                }
            }
        }

        queue.add(addProfileRequest)
    }

    private fun generateJsonPayload() : JSONObject {
        val userInformationMap = HashMap<String, Any?>()

        userInformationMap.put("userIdentifier", "TEST_1")
        userInformationMap.put("userFirstName", firstName.text.toString())
        userInformationMap.put("userLastName", lastName.text.toString())
        userInformationMap.put("userDateOfBirth", dateOfBirth.text.toString())
        userInformationMap.put("userStreetAddress", streetAddress.text.toString())
        userInformationMap.put("userCity", city.text.toString())
        userInformationMap.put("userState", state.text.toString())
        userInformationMap.put("userZipCode", zipCode.text.toString())
        userInformationMap.put("userPhoneNumber", phoneNumber.text.toString())
        userInformationMap.put("userMacAddress", macAddress.text.toString())
        userInformationMap.put("userDeviceName", "TEST DEVICE NAME")
        userInformationMap.put("emergencyContactFirstName", emergencyContactFirstName.text.toString())
        userInformationMap.put("emergencyContactLastName", emergencyContactLastName.text.toString())
        userInformationMap.put("emergencyContactPhoneNumber", emergencyContactPhoneNumber.text.toString())

        return JSONObject(userInformationMap)
    }
}