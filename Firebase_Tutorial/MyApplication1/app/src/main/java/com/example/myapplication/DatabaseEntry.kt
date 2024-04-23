package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class DatabaseEntry : AppCompatActivity() {

    private lateinit var buttonLogout : Button
    private lateinit var saveToDB : Button
    private lateinit var fetchFromDB : Button
    private lateinit var spinner1 : Spinner
    private lateinit var spinner2 : Spinner
    private lateinit var database: DatabaseReference
    private lateinit var user : String
    private var url = "https://sample-project-a986b-default-rtdb.europe-west1.firebasedatabase.app/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_database_entry)

        buttonLogout = findViewById(R.id.Database_Entry_logout)
        saveToDB = findViewById(R.id.Database_Entry_Add_to_db)
        fetchFromDB = findViewById(R.id.Database_Entry_fetch_value)
        spinner1 = findViewById(R.id.Database_Entry_spinner1)
        spinner2 = findViewById(R.id.Database_Entry_spinner2)
        database = Firebase.database.reference
        user = Firebase.auth.currentUser?.email.toString()

        val items1 = listOf("Item_1", "Item_2", "Item_3", "Item_4")
        val items2 = listOf("1", "2", "3", "4")

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2

        buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(baseContext, "Log out Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        saveToDB.setOnClickListener {
            FirebaseDatabase.
            getInstance(url).
            getReference().
            child(user).
            child(spinner1.selectedItem.toString()).
            setValue(spinner2.selectedItem.toString())
        }

        fetchFromDB.setOnClickListener{
            FirebaseDatabase.
            getInstance(url).
            getReference().
            child(user).
            child("Item_2").get().
            addOnSuccessListener {
                Toast.makeText(baseContext, "Got value ${it.value}", Toast.LENGTH_SHORT,).show()
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}