package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var TAG : String
    private lateinit var buttonLogin : Button
    private lateinit var buttonRegister : Button
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        signOutIfLoggedIn()
        setContentView(R.layout.activity_main)
        setElementIDAndListeners()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setElementIDAndListeners(){
        buttonLogin = findViewById(R.id.login)
        buttonRegister = findViewById(R.id.register)
        TAG = "Main_Activity"

        buttonLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun signOutIfLoggedIn(){
        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val user = Firebase.auth.currentUser

        if (user != null) {
            //val userName = user.displayName
            mGoogleSignInClient.signOut().addOnCompleteListener(this) {
                Toast.makeText(this, "${user.displayName} signed out", Toast.LENGTH_SHORT).show()
            }
            mAuth.signOut()
        } else {
            // Handle the case where the user is not signed in
        }
    }
}