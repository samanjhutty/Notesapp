package com.example.note.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.note.R
import com.example.note.adapter.RecyclerViewAdapterNote
import com.example.note.databinding.FragmentNoteListBinding
import com.example.note.roomdb.NoteDb
import com.example.note.roomdb.NoteEntity
import java.util.*
import java.util.concurrent.Executors

class NoteFragment : Fragment() {
    private lateinit var binding:FragmentNoteListBinding
    private lateinit var list: List<NoteEntity>
    private lateinit var adapter: RecyclerViewAdapterNote

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false)
        list= listOf()

        binding.list.layoutManager =  GridLayoutManager(requireContext(),3)

        getData()

        binding.btnAddNote.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                remove(NoteFragment())
                setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                replace(R.id.homeLayoutContainer, AddFragment())
                addToBackStack(null)
            }.commit()
        }
        binding.searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

//                val tempArr = List<NoteEntity>(20,)
//
//                for (arr in list){
//                    if (arr.title!!.toLowerCase(Locale.getDefault()).contains(p0.toString())){
//                        tempArr.add(arr)
//                    }
//                }
//
//                adapter.setData(tempArr)
//                adapter.notifyDataSetChanged()
                return true
            }

        })


        binding.view.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.view)
            popup.menuInflater.inflate(R.menu.menu, popup.menu)

            popup.setOnMenuItemClickListener{ it ->
                when(it.itemId){
                    R.id.title->{
                        list.sortedBy { it.title?.lowercase(Locale.ROOT) }
                        adapter = RecyclerViewAdapterNote(list,this)
                        binding.list.adapter = adapter
                        Toast.makeText(requireContext(), "Sorted by title", Toast.LENGTH_SHORT).show()
                    }
                    R.id.sort_date->{
                        list.sortedBy { it.date }
                        adapter = RecyclerViewAdapterNote(list,this)
                        binding.list.adapter = adapter
                        Toast.makeText(requireContext(), "Sorted by date", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            popup.show()
        }
        return binding.root
    }

    private fun getData() {
        val executor=Executors.newSingleThreadExecutor()
        val handler=Handler(Looper.getMainLooper())
        executor.execute {
                list = NoteDb.gtBase(requireContext()).mynotedao().getData()
            }
            handler.post {
                adapter = RecyclerViewAdapterNote(list,this@NoteFragment)
                binding.list.adapter = adapter
                adapter.notifyDataSetChanged()
            }
    }
}