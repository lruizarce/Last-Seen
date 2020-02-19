package com.example.lastseen

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.signin.internal.SignInClientImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initializeClickables()

    }

    private fun initializeClickables() {
        val btSignIn : Button = findViewById(R.id.login_sign_in_button)
        val tvSignUp : TextView = findViewById(R.id.login_sign_up_link)
        val tvForgotten : TextView = findViewById(R.id.login_forgot_password_link)
        val btGoogleSignIn : SignInButton = findViewById(R.id.g_sign_in_button)


        btGoogleSignIn.setOnClickListener{
            val gso : GoogleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            signIn()

            auth = FirebaseAuth.getInstance()

        }
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

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        // [START_EXCLUDE silent]

        // [END_EXCLUDE]

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)

                }


            }
    }


    private fun signIn(){
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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


    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}
