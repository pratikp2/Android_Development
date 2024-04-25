package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var TAG : String
    private lateinit var buttonLogin : Button
    private lateinit var buttonRegister : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        buttonLogin = findViewById(R.id.login)
        buttonRegister = findViewById(R.id.register)
        TAG = "Main_Activity"

        buttonLogin.setOnClickListener {
            //val intent = Intent(this, Login::class.java)
            val intent = Intent(this, LlmActivity::class.java)
            startActivity(intent) // Start the SecondActivity
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent) // Start the SecondActivity
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}