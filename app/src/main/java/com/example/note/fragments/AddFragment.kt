package com.example.note.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.note.R
import com.example.note.databinding.FragmentAddBinding
import com.example.note.roomdb.NoteDb
import com.example.note.roomdb.NoteEntity
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private var selectedColor ="#FFFFFF"
    private var imagepath=""
    private var layoutcolor ="#FFFFFF"
    private var color ="#e0e0e0"
    private var textcolor ="#606060"
    private var id2:Int=1
    private var up:Boolean=false

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Handle the returned Uri
        imagepath= uri.toString()
        binding.imagelayout.visibility=View.VISIBLE
        binding.ivImage.setImageURI(uri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)

        val toolbar = binding.toolbar
        getData()
        binding.ivImageDelete.setOnClickListener{
            binding.imagelayout.visibility=View.GONE
        }
        binding.ivBack.setOnClickListener {
            if (up) {
                myUpdateData()
            }
            if (!up) {
                if (binding.etContent.text.isNotEmpty()) {
                    saveData()
                } else if (binding.etTitle.text.isNotEmpty()) {
                    saveData()
                } else if (binding.etContent.text.isEmpty() && binding.etTitle.text.isEmpty()) {
                    showFragment()
                }
            }
        }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.color1 -> {
                        layoutColorData("#d7feff","#606060",R.color.bg_oyster_bay,R.color.bg_grey,R.color.text_color) }

                    R.id.color2 -> {
                        layoutColorData( "#abead8","#FFFFFF",R.color.bg_cruise,R.color.white,R.color.white) }

                    R.id.color3 -> {
                        layoutColorData("#f2cbf2","#FFFFFF",R.color.bg_we_peep,R.color.white,R.color.white) }

                    R.id.color4 -> {
                        layoutColorData("#fada95","#FFFFFF",R.color.bg_cherokee,R.color.white,R.color.white) }

                    R.id.color5 -> {
                        layoutColorData("#f3b0c3","#FFFFFF",R.color.bg_illusion,R.color.white,R.color.white) }

                    R.id.color6 -> {
                        layoutColorData("#b6cfb6","#FFFFFF",R.color.bg_spring_rain,R.color.white,R.color.white) }

                    R.id.color7 -> {
                        layoutColorData("#ffffff","#606060",R.color.white,R.color.bg_grey,R.color.text_color) }

                    R.id.color8 -> {
                        layoutColorData("#A9A9A9","#FFFFFF",R.color.bg_grey,R.color.white,R.color.white) }

                    R.id.saveNote -> {
                        if (up) {
                            myUpdateData()
                        }
                        if (!up) {
                            if (binding.etContent.text.isNotEmpty()) {
                                saveData()
                            } else if (binding.etTitle.text.isNotEmpty()) {
                                saveData()
                            } else if (binding.etContent.text.isEmpty() && binding.etTitle.text.isEmpty()) {
                                showFragment()
                            }
                        }
                    }

                    R.id.delete -> {
                        if (up) {
                            delData()
                        }
                        if (!up) {
                            showFragment()
                        }
                    }

                    R.id.action_attach -> {
                        getContent.launch("image/*")
                    }
                }
                super.onOptionsItemSelected(it)
            }

        return binding.root
    }
    private fun layoutColorData(selected_n_layout_color:String,text_color:String,layout_bg_color_id:Int,text_hint_color_id:Int,text_color_id:Int){
        binding.layout.setBackgroundColor(resources.getColor(layout_bg_color_id))
        binding.imagelayout.setBackgroundColor(resources.getColor(layout_bg_color_id))
//        window?.statusBarColor = this.resources.getColor(layout_color_id)
        selectedColor = selected_n_layout_color
        layoutcolor = selected_n_layout_color
        binding.etContent.setHintTextColor(resources.getColor(text_hint_color_id))
        binding.etTitle.setHintTextColor(resources.getColor(text_hint_color_id))
        binding.etContent.setTextColor(resources.getColor(text_color_id))
        binding.etTitle.setTextColor(resources.getColor(text_color_id))
        textcolor=text_color
    }

    private fun showFragment(){
        requireActivity().supportFragmentManager.beginTransaction().apply {
            remove(AddFragment())
            replace(R.id.homeLayoutContainer,NoteFragment())
        }.commit()
    }

    private fun saveData(){
        class MySave:AsyncTask<Void,Void,Void>()
        {
            @SuppressLint("SimpleDateFormat")
            override fun doInBackground(vararg params: Void?): Void? {
                val sdf = SimpleDateFormat("EEE, MMM d, ''yy")
                val currentDate = sdf.format(Date())

                val notes=NoteEntity()
                notes.title=binding.etTitle.text.toString()
                notes.content=binding.etContent.text.toString()
                notes.color=selectedColor
                notes.laycolor=layoutcolor
                notes.textcolor=textcolor
                notes.date=currentDate.toString()
                notes.url=imagepath

                NoteDb.gtBase(requireContext()).mynotedao().saveData(notes)

                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(requireContext(), "Data Saved", Toast.LENGTH_SHORT).show()
                showFragment()
            }
        }
        MySave().execute()
    }

    private fun getData() {
        val  bundle =arguments
        if (bundle!=null) {
            id2=bundle.getInt("id",1)
            val title = bundle.getString("title").toString()
            val desc = bundle.getString("content").toString()
            color = bundle.getString("color").toString()
            layoutcolor = bundle.getString("laycolor").toString()
            textcolor = bundle.getString("textcolor").toString()
            imagepath = bundle.getString("url").toString()

            selectedColor=color
            up=true

            binding.etTitle.setText(title)
            binding.etContent.setText(desc)
            binding.layout.setBackgroundColor(Color.parseColor(color))
            binding.etContent.setTextColor(Color.parseColor(textcolor))
            binding.etTitle.setTextColor(Color.parseColor(textcolor))
            binding.etContent.setHintTextColor(Color.parseColor(textcolor))
            binding.etTitle.setHintTextColor(Color.parseColor(textcolor))

            if(imagepath.isBlank()){
                binding.imagelayout.visibility=View.GONE
            }
            else if(imagepath.isNotBlank()){
                binding.imagelayout.visibility=View.VISIBLE
            }
            Glide.with(requireContext()).load(imagepath).into(binding.ivImage)
        }
        else{
            up=false
        }
    }

    private fun myUpdateData() {
        class UpdateMyData : AsyncTask<Void, Void, Void>() {
            @SuppressLint("SimpleDateFormat")
            override fun doInBackground(vararg p0: Void?): Void? {
                val note = NoteEntity()
                val sdf = SimpleDateFormat("EEE, MMM d, ''yy")
                val currentDate = sdf.format(Date())

                note.content = binding.etContent.text.toString()
                note.title = binding.etTitle.text.toString()
                note.date=currentDate.toString()
                note.id1 = id2
                note.color=selectedColor
                note.laycolor=layoutcolor
                note.textcolor=textcolor
                note.url=imagepath

                NoteDb.gtBase(requireContext()).mynotedao().updateData(note)

                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(requireContext(), " Data Updated", Toast.LENGTH_SHORT).show()
                showFragment()
            }
        }
        UpdateMyData().execute()
    }

    private fun delData(){
        class DelData:AsyncTask<Void,Void,Void>(){

            override fun doInBackground(vararg p0: Void?): Void ?{
                val note=NoteEntity()
                note.id1=id2

                NoteDb.gtBase(requireContext()).mynotedao().delData(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()
                showFragment()
            }
        }
        DelData().execute()
    }
}
