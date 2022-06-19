package com.example.note.fragments

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.note.R
import com.example.note.adapter.RecyclerViewAdapterNote
import com.example.note.databinding.FragmentNoteListBinding
import com.example.note.roomdb.NoteDb
import com.example.note.roomdb.NoteEntity

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
        val mlist=binding.list
        mlist.layoutManager =  GridLayoutManager(requireContext(),3)

        getData()

        binding.view.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.view)
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.menu, popup.menu)

            popup.setOnMenuItemClickListener{
                when(it.itemId){
                    R.id.title->{
                        list.sortedBy { it.title }
                        adapter = RecyclerViewAdapterNote(list,this@NoteFragment)
                        mlist.adapter = adapter
                        Toast.makeText(requireContext(), "Sorted by title", Toast.LENGTH_SHORT).show()
                    }
                    R.id.sort_date->{
                        list.sortedBy { it.date }
                        adapter = RecyclerViewAdapterNote(list,this@NoteFragment)
                        mlist.adapter = adapter
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
        class GetAdapter : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg p0: Void?): Void? {
                list = NoteDb.gtBase(requireContext()).mynotedao().getData()
                return null
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                adapter = RecyclerViewAdapterNote(list,this@NoteFragment)
                binding.list.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
        GetAdapter().execute()
    }
}