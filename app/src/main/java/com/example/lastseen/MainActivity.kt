package com.example.lastseen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
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

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeGoogleSignIn()
        initializeClickables()
    }

    private fun initializeGoogleSignIn() {
        val googleSignInButton : SignInButton = findViewById(R.id.google_sign_in_button)

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
                    val user = auth.currentUser
                    checkIfUserExists(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    displayUnableToLoginMessage()
                }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun initializeClickables() {
        val tvSignUp : TextView = findViewById(R.id.login_sign_up_link)

        tvSignUp.setOnClickListener {
            val intent = Intent(this, CreateAccount::class.java)

            startActivity(intent)
        }
    }

    private fun displayUnableToLoginMessage() {
        Toast.makeText(applicationContext, "UNABLE TO LOGIN", Toast.LENGTH_SHORT).show()
    }

    private fun checkIfUserExists(user : FirebaseUser?){
        val queue = Volley.newRequestQueue(this)
        val urlString = getString(R.string.server_url) + getString(R.string.server_profile) + user?.uid

        val getProfileRequest = JsonObjectRequest(Request.Method.GET, urlString, null,
            Response.Listener {
                val intent = Intent(this, Itinerary::class.java)
                intent.putExtra("userIdentifier", user?.uid)

                startActivity(intent)
                finish()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "NO TREK POINT ACCOUNT FOUND FOR GOOGLE ACCOUNT", Toast.LENGTH_SHORT).show()
            })

        queue.add(getProfileRequest)
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}