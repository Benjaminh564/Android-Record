package com.finalteam6.HighResAudioRecorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initially starts with the login page before access to recording application
        setContentView(R.layout.login)

        //signup takes to signup page
        findViewById<Button>(R.id.Signup).setOnClickListener() {
            startActivity(Intent(this, signup::class.java))
        }
        //login takes to login page
        findViewById<Button>(R.id.Login).setOnClickListener() {
            startActivity(Intent(this, loginaccess::class.java))
        }
    }
}
