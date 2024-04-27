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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore

class DatabaseEntry : AppCompatActivity() {

    private val db = Firebase.firestore
    private val user : String = "USER_" + (1..16).map {('0'..'9').random() }.joinToString("")
    private lateinit var buttonLogout : Button
    private lateinit var saveToDB : Button
    private lateinit var fetchFromDB : Button
    private lateinit var saveToCfDB : Button
    private lateinit var fetchFromCfDB : Button
    private lateinit var crashTest : Button
    private lateinit var spinner1 : Spinner
    private lateinit var spinner2 : Spinner
    private lateinit var database: DatabaseReference
    private lateinit var currentUser : FirebaseUser
    private var url = "https://sample-project-a986b-default-rtdb.europe-west1.firebasedatabase.app/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_database_entry)
        setElementIDAndListeners()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setElementIDAndListeners(){
        crashTest = findViewById(R.id.DummyCrash)
        buttonLogout = findViewById(R.id.Database_Entry_logout)
        saveToDB = findViewById(R.id.Database_Entry_Add_to_db)
        fetchFromDB = findViewById(R.id.Database_Entry_fetch_value)
        saveToCfDB = findViewById(R.id.Database_Entry_write_cf_value)
        fetchFromCfDB = findViewById(R.id.Database_Entry_fetch_cf_value)
        spinner1 = findViewById(R.id.Database_Entry_spinner1)
        spinner2 = findViewById(R.id.Database_Entry_spinner2)
        database = Firebase.database.reference

        val items1 = listOf("Item_1", "Item_2", "Item_3", "Item_4")
        val items2 = listOf("1", "2", "3", "4")

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2

        buttonLogout.setOnClickListener {
            signOut()
        }

        crashTest.setOnClickListener {
            throw RuntimeException("Test Crash")
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
            child(spinner1.selectedItem.toString()).get().
            addOnSuccessListener {
                Toast.makeText(baseContext, "Got value ${it.value}", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }

        saveToCfDB.setOnClickListener {
            val cities = db.collection("cities")

            val data1 = hashMapOf(
                "name" to "San Francisco",
                "state" to "CA",
                "country" to "USA",
                "capital" to false,
                "population" to 860000,
                "regions" to listOf("west_coast", "normal"),
            )
            cities.document("SF").set(data1)

            val data2 = hashMapOf(
                "name" to "Los Angeles",
                "state" to "CA",
                "country" to "USA",
                "capital" to false,
                "population" to 3900000,
                "regions" to listOf("west_coast", "social"),
            )
            cities.document("LA").set(data2)

            val data3 = hashMapOf(
                "name" to "Washington D.C.",
                "state" to null,
                "country" to "USA",
                "capital" to true,
                "population" to 680000,
                "regions" to listOf("east_coast"),
            )
            cities.document("DC").set(data3)

            val data4 = hashMapOf(
                "name" to "Tokyo",
                "state" to null,
                "country" to "Japan",
                "capital" to true,
                "population" to 9000000,
                "regions" to listOf("Canto", "honshu"),
            )
            cities.document("TOK").set(data4)

            val data5 = hashMapOf(
                "name" to "Beijing",
                "state" to null,
                "country" to "China",
                "capital" to true,
                "population" to 21500000,
                "regions" to listOf("jingjinji", "hebei"),
            )
            cities.document("BJ").set(data5)
        }

        fetchFromCfDB.setOnClickListener {
            var fetchedData : String
            val docRef = db.collection("cities").document("SF")
            docRef.get()
                .addOnSuccessListener { document ->
                    fetchedData = document?.data?.toString() ?: "No such document"
                    Toast.makeText(baseContext, "Got value $fetchedData", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Got value $fetchedData")
                }
                .addOnFailureListener { exception -> Log.d(TAG, "get failed with ", exception) }
        }
    }

    private fun signOut(){
        currentUser = FirebaseAuth.getInstance().currentUser!!
        currentUser.let { user ->
            user.providerData.forEach { userInfo ->
                val providerId = userInfo.providerId
                if (providerId == GoogleAuthProvider.PROVIDER_ID) {
                    signOutGoogle()
                } else if (providerId == "firebase") {
                    signOutFirebase()
                }
            }
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun signOutGoogle() {
        FirebaseAuth.getInstance().signOut()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            Toast.makeText(this, "$user signed out ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signOutFirebase() {
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(baseContext, "Log out Successful", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    companion object {
        private const val TAG : String = "DatabaseEntry"
    }
}