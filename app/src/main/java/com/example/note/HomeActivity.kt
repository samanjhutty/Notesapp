package com.example.note

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.note.adapter.RecyclerViewAdapterNote
import com.example.note.databinding.ActivityHomeBinding
import com.example.note.fragments.AddFragment
import com.example.note.fragments.NoteFragment
import com.example.note.roomdb.NoteEntity
import com.google.android.material.appbar.AppBarLayout
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var arrNotes: List<NoteEntity>
    private lateinit var list: ArrayList<NoteEntity>
    private lateinit var adapter: RecyclerViewAdapterNote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.bg_light)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportFragmentManager.beginTransaction().replace(R.id.GridLayout, NoteFragment()).commit()

        binding.btnAddNote.setOnClickListener {
            binding.layoutMain.visibility = View.GONE
            supportFragmentManager.beginTransaction().replace(R.id.layoutContainer, AddFragment())
                .commit()
        }

        var isShow = true
        var scrollRange = -1
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }

            if (scrollRange + verticalOffset == 0) {
                binding.tvName.text = getString(R.string.note)
                binding.tvName.visibility = View.VISIBLE
                val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                binding.tvName.startAnimation(animation)

                isShow = true
            } else if (isShow) {
                binding.tvName.text = " "
                binding.tvName.visibility = View.GONE
                val animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
                binding.tvName.startAnimation(animation)
                isShow = false
            }
        })

        binding.searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                for (arr in arrNotes){
                    if (arr.title!!.lowercase(Locale.getDefault()).contains(p0.toString())){
//                        adapter = RecyclerViewAdapterNote()
//                        binding.list.adapter = adapter
                    }
                }
                findViewById<RecyclerView>(R.id.list).adapter = adapter
                adapter.notifyDataSetChanged()

                return true
            }
        })

        val intent= Intent()
        val a=intent.getStringExtra("count")
        binding.notesSize.text = "$a notes"


    }

    override fun onBackPressed() {
        super.onBackPressed()

        if(binding.layoutMain.visibility == View.GONE){
            HomeActivity()
        }
    }
}
