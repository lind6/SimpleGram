package com.example.simplegram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {

    override fun onCreate( savedInstanceState: Bundle? ) {

        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_login )

        // forced logout (for debugging only)
        //ParseUser.logOut()

        // see if user is logged in
        if ( ParseUser.getCurrentUser() != null ) {
            // user already logged in
            goToMainActivity()
        }

        // get username and password (executed only when button clicked)
        findViewById<Button>( R.id.bLogin ).setOnClickListener {
            val username = findViewById<EditText>( R.id.etUsername ).text.toString()
            val password = findViewById<EditText>( R.id.etPassword ).text.toString()
            loginUser( username, password )
        }

        // get username and password (executed only when button clicked)
        findViewById<Button>( R.id.bRegister ).setOnClickListener {
            val username = findViewById<EditText>( R.id.etUsername ).text.toString()
            val password = findViewById<EditText>( R.id.etPassword ).text.toString()
            signUpUser( username, password )
        }
    }

    private fun signUpUser( username: String, password: String ) {
        // Create ParseUser
        val user = ParseUser()

        // Set fields for new user
        user.setUsername( username )
        user.setPassword( password )

        user.signUpInBackground { e ->
            if ( e == null ) {
                // successful registration of new user
                Log.i( TAG, "successful registration")
                goToMainActivity()
            } else {
                // Sign up did not succeed.
                // Look at ParseException to determine what went wrong
                e.printStackTrace()
                Toast.makeText( this, "Registration failed", Toast.LENGTH_SHORT ).show()
            }
        }
    }

    private fun loginUser( username: String, password: String ) {
        ParseUser.logInInBackground( username, password, ( { user, e ->
            if ( user != null ) {
                // Hooray!  The user is logged in.
                Log.i( TAG, "successful login" )
                goToMainActivity()
            } else {
                // Signup failed.  Look at the ParseException to see what happened.
                // Log.e( TAG, "login failed: $username $password" )
                e.printStackTrace()
                Toast.makeText( this, "Error logging in", Toast.LENGTH_SHORT ).show()
            }
        }))
    }

    private fun goToMainActivity() {
        val intent = Intent( this@LoginActivity, MainActivity::class.java )
        startActivity( intent )
        finish()
    }

    companion object {
        val TAG = "LoginActivity"
    }
}