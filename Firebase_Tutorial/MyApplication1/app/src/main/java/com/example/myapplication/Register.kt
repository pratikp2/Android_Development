package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase

class Register : AppCompatActivity() {

    private var TAG = "REGISTER_CLASS"
    private lateinit var auth: FirebaseAuth
    private lateinit var buttonRegister : Button;
    private lateinit var buttonBack : Button;
    private lateinit var textEmail : EditText;
    private lateinit var textPassphrase : EditText;
    private lateinit var textComfirmPassphrase : EditText;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = Firebase.auth


        buttonRegister = findViewById(R.id.register_page_confirm_register)
        buttonBack = findViewById(R.id.register_page_back)
        textEmail = findViewById(R.id.register_page_email)
        textPassphrase = findViewById(R.id.register_page_passphrase)
        textComfirmPassphrase = findViewById(R.id.register_page_confirm_passphrase)


        buttonRegister.setOnClickListener {
            if(textPassphrase.text.toString() == textComfirmPassphrase.text.toString()) {
                auth.createUserWithEmailAndPassword(textEmail.text.toString(), textPassphrase.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            //val user = auth.currentUser
                            val intent = Intent(this, DatabaseEntry::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.Retry",
                                Toast.LENGTH_SHORT,
                            ).show()
                            //updateUI(null)
                        }
                    }
            } else {
                Toast.makeText(
                    baseContext,
                    "Passphrase Do not Match. Retry !!",
                    Toast.LENGTH_SHORT,
                ).show()
                //updateUI(null)
            }
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

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //reload()
        }
    }
}