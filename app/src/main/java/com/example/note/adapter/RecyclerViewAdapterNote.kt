package com.example.note.adapter

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
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
        if(item.url!=null){
            holder.imageShowView.setImageURI(Uri.parse(item.url))
            holder.imageShowView.visibility=View.VISIBLE
        }
        else if(item.url==null){
            holder.imageShowView.visibility=View.GONE
        }

        if (item.color != null){
            holder.noteshowLayout.setBackgroundColor(Color.parseColor(item.color))
        }else{
            holder.noteshowLayout.setBackgroundColor(Color.parseColor(R.color.white.toString()))
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
            obj.arguments=bundle
            context.requireActivity().findViewById<CoordinatorLayout>(R.id.layoutMain).visibility=View.GONE
            context.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.homeLayoutContainer,obj).commit()
        }
    }

    inner class ViewHolder(binding: FragmentNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvTitle
        val content: TextView = binding.tvContent
        val date: TextView = binding.tvDate
        val layout:ConstraintLayout=binding.listLayout
        val imageShowView:ImageView=binding.viewForImageView
        val noteshowLayout:RelativeLayout=binding.noteShowLayout
    }
}