package com.finalteam6.HighResAudioRecorder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class signup:AppCompatActivity() {
    private val email: String
        get() = findViewById<EditText>(R.id.email).text.toString()

    private val password: String
        get() = findViewById<EditText>(R.id.password).text.toString()

    private val passwordConfirm: String
        get() = findViewById<EditText>(R.id.confirmPass).text.toString()

    private val fullName: String
        get() = findViewById<EditText>(R.id.fullName).text.toString()

    private val signError: TextView
        get() = findViewById<TextView>(R.id.errorSignup)

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        val submitBtn = findViewById<Button>(R.id.signUpEnter)


        submitBtn.setOnClickListener() {
            if (password != passwordConfirm) {
                signError.text = "Passwords do not match"
            } else if (email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() || fullName.isEmpty()) {
                signError.text = "Please fill in all information"
            } else {
                val userCollection = db.collection("users")
                val user: MutableMap<String, Any> = HashMap()
                user["email"] = email
                user["user"] = fullName
                user["password"] = password

                signError.text = ""
                userCollection.whereEqualTo("email", user["email"]).get()
                    .addOnSuccessListener { task ->
                        if (task.count() > 0) {
                            signError.text = "That email address already exists, please try another"
                        } else {
                            userCollection.add(user).addOnSuccessListener {
                                signError.text = "Successfully Created Account, Please log in"
                                startActivity(Intent(this, loginaccess::class.java))
                            }
                        }
                    }
            }
        }
    }
}
