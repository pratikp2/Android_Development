package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase

class Login : AppCompatActivity() {

    private var TAG = "LOGIN_CLASS"
    private lateinit var auth: FirebaseAuth
    private lateinit var buttonLogin : Button
    private lateinit var buttonBack : Button
    private lateinit var textEmail: EditText
    private lateinit var textPassphrase: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        buttonLogin = findViewById(R.id.login_page_login)
        buttonBack = findViewById(R.id.login_page_back)
        textEmail = findViewById(R.id.login_page_email)
        textPassphrase = findViewById(R.id.login_page_passphrase)

        // Initialize Firebase Auth
        auth = Firebase.auth

        buttonLogin.setOnClickListener {
            auth.signInWithEmailAndPassword(textEmail.text.toString(), textPassphrase.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        //val user = auth.currentUser
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        //updateUI(null)
                    }
                }
            val intent = Intent(this, DatabaseEntry::class.java)
            startActivity(intent) // Start the SecondActivity
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}