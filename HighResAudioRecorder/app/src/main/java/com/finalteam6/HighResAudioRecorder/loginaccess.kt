package com.finalteam6.HighResAudioRecorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class loginaccess:AppCompatActivity() {

    private val email: String
        get() = findViewById<EditText>(R.id.loginEmail).text.toString()

    private val password: String
        get() = findViewById<EditText>(R.id.loginPass).text.toString()

    private val errorMessage : TextView
        get() = findViewById<TextView>(R.id.loginError)

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginaccess)

        findViewById<Button>(R.id.loginEnter).setOnClickListener(){
            errorMessage.text = "Logging in..."
            val userCollection = db.collection("users")
            userCollection.whereEqualTo("email", email).get()
                .addOnSuccessListener { task ->
                    if(task.count() > 0) {
                        val user = task.elementAt(0).data
                        if (user["password"] == password) {
                            errorMessage.text = "Success"
                            val intent = Intent(this@loginaccess, recorder::class.java)
                            intent.putExtra("name", user["user"].toString())
                            startActivity(intent)
                        } else {
                            errorMessage.text = "Invalid Password"
                        }
                    } else {
                        errorMessage.text = "User not found"
                    }
                }
        }
    }
}
