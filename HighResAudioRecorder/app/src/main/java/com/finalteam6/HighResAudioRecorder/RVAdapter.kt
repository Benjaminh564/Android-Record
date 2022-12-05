package com.finalteam6.HighResAudioRecorder.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.finalteam6.HighResAudioRecorder.items
import com.finalteam6.HighResAudioRecorder.R
import com.finalteam6.HighResAudioRecorder.fragment_record


 class RVAdapter(private val dataList: List<items>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)

    }
     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val item_ = dataList[position]
         //val currentFilename_ = "${currentFilename}"
         //holder.rec_Title.text = item_.currentFilename_
         holder.rec_Title.text = item_.title.toString()
     }

     override fun getItemCount(): Int {
         return dataList.size
     }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rec_Title: TextView = itemView.findViewById(R.id.recTitle)

        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "You have clicked", Toast.LENGTH_LONG).show()
            }
        }
    }

 }
