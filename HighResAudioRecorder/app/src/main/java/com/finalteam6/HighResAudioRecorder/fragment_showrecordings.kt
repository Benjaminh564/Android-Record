package com.finalteam6.HighResAudioRecorder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class fragment_showrecordings : Fragment() {

    private var data: ArrayList<String>? = ArrayList<String>()
   //private lateinit var db =

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // arguments?.let {
      //  }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_showrecordings, container, false)
    }

    companion object {
/*
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_showrecordings().apply {
                arguments = Bundle().apply {

                }
            }*/
    }
}