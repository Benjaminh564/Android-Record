package com.finalteam6.HighResAudioRecorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide();
        setContentView(R.layout.activity_main)

        val recordFrag = fragment_record()
        val settingsFrag = fragment_settings()
        val recordingsFrag = fragment_showrecordings()

        changeFragment(recordFrag)
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener {
                item ->
            when(item.itemId){
                R.id.record ->{
                    changeFragment(recordFrag)
                    true
                }
                R.id.display->{
                    changeFragment(recordingsFrag)
                    true
                }
                R.id.settings->{
                    changeFragment(settingsFrag)
                    true
                }
                else->{
                    false
                }
            }
        }

    }
    private fun changeFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,fragment)
            commit()
        }


        //
    }
}