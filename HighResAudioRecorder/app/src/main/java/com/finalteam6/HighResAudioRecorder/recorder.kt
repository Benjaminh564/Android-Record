package com.finalteam6.HighResAudioRecorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
class recorder: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //Hide action bar
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide();
        setContentView(R.layout.activity_main)

        //Grab references to our fragments
        val recordFrag = fragment_record()
        val settingsFrag = fragment_settings()
        val recordingsFrag = fragment_showrecordings()

        //Fragment switching
        changeFragment(recordFrag)
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.record -> {
                    changeFragment(recordFrag)
                    true
                }
                R.id.display -> {
                    changeFragment(recordingsFrag)
                    true
                }
                R.id.settings -> {
                    changeFragment(settingsFrag)
                    true
                }
                else -> {
                    false
                }
            }
        }

    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }
    }
}