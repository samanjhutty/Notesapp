package com.example.note.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.example.note.R
import com.example.note.adapter.RecyclerViewAdapterNote
import com.example.note.databinding.FragmentListBinding
import com.example.note.roomdb.NoteEntity
import com.google.android.material.appbar.AppBarLayout
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentListBinding
    private lateinit var arrNotes: List<NoteEntity>
    private lateinit var list: ArrayList<NoteEntity>
    private lateinit var adapter: RecyclerViewAdapterNote


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_list, container, false)

        binding.btnAddNote.setOnClickListener {
            binding.layoutMain.visibility = View.GONE
            requireActivity().supportFragmentManager.beginTransaction().apply {
                remove(HomeFragment())
                replace(R.id.homeLayoutContainer, AddFragment())
            }.commit()

        }
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.GridLayout, NoteFragment()).commit()

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {


                return true
            }
        })

        return binding.root
    }

}