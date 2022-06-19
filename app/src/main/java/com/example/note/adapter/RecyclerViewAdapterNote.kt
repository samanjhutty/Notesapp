package com.example.note.adapter

import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.note.R
import com.example.note.databinding.FragmentNoteBinding
import com.example.note.fragments.AddFragment
import com.example.note.fragments.NoteFragment
import com.example.note.roomdb.NoteEntity

open class RecyclerViewAdapterNote(
    private val values:List<NoteEntity>, val context: NoteFragment
) : RecyclerView.Adapter<RecyclerViewAdapterNote.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.title
        holder.content.text = item.content
        holder.date.text = item.date

        if (item.color != null){
            holder.content.setBackgroundColor(Color.parseColor(item.color))
        }else{
            holder.content.setBackgroundColor(Color.parseColor(R.color.white.toString()))
        }
        if (item.laycolor != null){
            holder.content.setTextColor(Color.parseColor(item.textcolor))
        }else{
            holder.content.setTextColor(Color.parseColor(R.color.white.toString()))
        }



        holder.layout.setOnClickListener {
            val bundle=Bundle()
            val obj=AddFragment()

            bundle.putInt("id", item.id1)
            bundle.putString("title",item.title)
            bundle.putString("content",item.content)
            bundle.putString("color",item.color)
            bundle.putString("laycolor",item.laycolor)
            bundle.putString("textcolor",item.textcolor)
            bundle.putString("url",item.url)
            bundle.putString("count",itemCount.toString())
            obj.arguments=bundle
            context.requireActivity().findViewById<CoordinatorLayout>(R.id.layoutMain).visibility=View.GONE
            context.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.layoutContainer,obj).commit()
        }
    }

    inner class ViewHolder(binding: FragmentNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvTitle
        val content: TextView = binding.tvContent
        val date: TextView = binding.tvDate
        val layout:ConstraintLayout=binding.listLayout
    }
}