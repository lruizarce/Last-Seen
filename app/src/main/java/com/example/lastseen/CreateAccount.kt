package com.example.lastseen

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.create_account.*
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class CreateAccount : AppCompatActivity() {
    private var filledOutRequiredFields = false
    private var linkedGoogleAccount = false

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
    lateinit var mBluetoothAdapter : BluetoothAdapter

    var user : FirebaseUser? = null
    lateinit var auth : FirebaseAuth
    lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account)

        initializeGoogleSignIn()
        initializeViewsAndAdapters()
        initializeButton()
    }

    private fun initializeGoogleSignIn() {
        val googleSignInButton : SignInButton = findViewById(R.id.create_account_google_sign_in_button)

        googleSignInButton.setOnClickListener {
            signIn()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e : ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct : GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    user = auth.currentUser
                    create_account_submit_button.isEnabled = true
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    displayUnableToLoginMessage()
                }
            }
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
            || TextUtils.isEmpty(emergencyContactFirstName.text)
            || TextUtils.isEmpty(emergencyContactLastName.text)
            || TextUtils.isEmpty(emergencyContactPhoneNumber.text))
        {
            Toast.makeText(applicationContext, "REQUIRED FIELDS CANNOT BE EMPTY", Toast.LENGTH_SHORT).show()

            return false
        }
        return true
    }

    private fun sendPayloadToServer() {
        val jsonProfileObject = generateJsonProfilePayload()
        val queue = Volley.newRequestQueue(this)
        val urlString = getString(R.string.server_url) + getString(R.string.server_profile)

        val addProfileRequest = object : JsonObjectRequest(Request.Method.POST, urlString, jsonProfileObject,
            Response.Listener {
                 finish()
            },
            Response.ErrorListener {
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

    private fun generateJsonProfilePayload() : JSONObject {
        val userInformationMap = HashMap<String, Any?>()

        userInformationMap.put("userIdentifier", user?.uid)
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

    private fun displayUnableToLoginMessage() {
        Toast.makeText(applicationContext, "UNABLE TO LOGIN", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}