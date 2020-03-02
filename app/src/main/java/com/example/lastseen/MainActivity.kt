package com.example.lastseen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.itinerary.*
import java.util.*



class MainActivity : AppCompatActivity() {

    private lateinit var providers : List<AuthUI.IdpConfig>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.itinerary)


        providers = Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        showSignInOptions()

    }

    private fun checkIfUserExists(user : FirebaseUser?){
        val queue = Volley.newRequestQueue(this)
        val urlString = getString(R.string.server_url) + getString(R.string.server_profile) + user?.uid

        val getProfileRequest = JsonObjectRequest(
            Request.Method.GET, urlString, null,
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1234){
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, ""+user!!.email,Toast.LENGTH_LONG).show()

            }
            else{
                Toast.makeText(this, ""+response!!.error!!.message,Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun showSignInOptions(){
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.MyTheme)
            .build(), RC_SIGN_IN)
    }


    companion object{
        const val RC_SIGN_IN = 1234
    }

    }
