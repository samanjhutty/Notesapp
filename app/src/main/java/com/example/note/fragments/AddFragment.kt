package com.example.note.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.note.HomeActivity
import com.example.note.R
import com.example.note.databinding.FragmentAddBinding
import com.example.note.roomdb.NoteDb
import com.example.note.roomdb.NoteEntity
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {
    lateinit var binding: FragmentAddBinding
    var selectedColor ="#FFFFFF"
    var imagepath=""
    var layoutcolor ="#FFFFFF"
    var color ="#e0e0e0"
    var textcolor ="#606060"
    var id2:Int=1
    var up=0
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
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

        val window = (activity as? AppCompatActivity)?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.statusBarColor = this.resources.getColor(R.color.white)

        val toolbar = binding.toolbar
        getData()
        binding.ivImageDelete.setOnClickListener{
            binding.imagelayout.visibility=View.GONE
        }
        binding.ivBack.setOnClickListener {
            if (up == 1) {
                myUpdateData()
            }
            if (up == 0) {
                if (binding.etContent.text.isNotEmpty()) {
                    saveData()
                } else if (binding.etTitle.text.isNotEmpty()) {
                    saveData()
                } else if (binding.etContent.text.isEmpty() && binding.etTitle.text.isEmpty()) {
                    val a = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(a)
                    requireActivity().finish()
                }
            }
        }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.color1 -> {
                        binding.layout.setBackgroundColor(resources.getColor(R.color.bg_oyster_bay))
                        binding.imagelayout.setBackgroundColor(resources.getColor(R.color.bg_oyster_bay))
                        selectedColor = "#d7feff"
                        layoutcolor = "#d7feff"
                        binding.etContent.setHintTextColor(resources.getColor(R.color.bg_grey))
                        binding.etTitle.setHintTextColor(resources.getColor(R.color.bg_grey))
                        window?.statusBarColor = this.resources.getColor(R.color.bg_oyster_bay)
                        binding.etContent.setTextColor(resources.getColor(R.color.text_color))
                        binding.etTitle.setTextColor(resources.getColor(R.color.text_color))
                        textcolor="#606060"
                    }
                    R.id.color2 -> {
                        binding.layout.setBackgroundColor(resources.getColor(R.color.bg_cruise))
                        binding.imagelayout.setBackgroundColor(resources.getColor(R.color.bg_cruise))
                        window?.statusBarColor = this.resources.getColor(R.color.bg_cruise)
                        selectedColor = "#abead8"
                        layoutcolor = "#abead8"
                        binding.etContent.setHintTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setHintTextColor(resources.getColor(R.color.white))
                        binding.etContent.setTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setTextColor(resources.getColor(R.color.white))
                        textcolor="#FFFFFF"

                    }
                    R.id.color3 -> {
                        binding.layout.setBackgroundColor(resources.getColor(R.color.bg_we_peep))
                        binding.imagelayout.setBackgroundColor(resources.getColor(R.color.bg_we_peep))
                        window?.statusBarColor = this.resources.getColor(R.color.bg_we_peep)
                        selectedColor = "#f2cbf2"
                        layoutcolor = "#f2cbf2"
                        binding.etContent.setHintTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setHintTextColor(resources.getColor(R.color.white))
                        binding.etContent.setTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setTextColor(resources.getColor(R.color.white))
                        textcolor="#FFFFFF"

                    }
                    R.id.color4 -> {
                        binding.layout.setBackgroundColor(resources.getColor(R.color.bg_cherokee))
                        binding.imagelayout.setBackgroundColor(resources.getColor(R.color.bg_cherokee))
                        window?.statusBarColor = this.resources.getColor(R.color.bg_cherokee)
                        selectedColor = "#fada95"
                        layoutcolor = "#fada95"
                        binding.etContent.setHintTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setHintTextColor(resources.getColor(R.color.white))
                        binding.etContent.setTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setTextColor(resources.getColor(R.color.white))
                        textcolor="#606060"

                    }
                    R.id.color5 -> {
                        binding.layout.setBackgroundColor(resources.getColor(R.color.bg_illusion))
                        binding.imagelayout.setBackgroundColor(resources.getColor(R.color.bg_illusion))
                        window?.statusBarColor = this.resources.getColor(R.color.bg_illusion)
                        selectedColor = "#f3b0c3"
                        layoutcolor = "#f3b0c3"
                        binding.etContent.setHintTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setHintTextColor(resources.getColor(R.color.white))
                        binding.etContent.setTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setTextColor(resources.getColor(R.color.white))
                        textcolor="#FFFFFF"

                    }
                    R.id.color6 -> {
                        binding.layout.setBackgroundColor(resources.getColor(R.color.bg_spring_rain))
                        binding.imagelayout.setBackgroundColor(resources.getColor(R.color.bg_spring_rain))
                        window?.statusBarColor = this.resources.getColor(R.color.bg_spring_rain)
                        selectedColor = "#b6cfb6"
                        layoutcolor = "#b6cfb6"
                        binding.etContent.setHintTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setHintTextColor(resources.getColor(R.color.white))
                        binding.etContent.setTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setTextColor(resources.getColor(R.color.white))
                        textcolor="#FFFFFF"


                    }
                    R.id.color7 -> {
                        binding.layout.setBackgroundColor(resources.getColor(R.color.white))
                        binding.imagelayout.setBackgroundColor(resources.getColor(R.color.white))
                        window?.statusBarColor = this.resources.getColor(R.color.white)
                        selectedColor = "#ffffff"
                        layoutcolor = "#ffffff"
                        binding.etContent.setHintTextColor(resources.getColor(R.color.bg_grey))
                        binding.etTitle.setHintTextColor(resources.getColor(R.color.bg_grey))
                        binding.etContent.setTextColor(resources.getColor(R.color.text_color))
                        binding.etTitle.setTextColor(resources.getColor(R.color.text_color))
                        textcolor="#606060"

                    }
                    R.id.color8 -> {
                        binding.layout.setBackgroundColor(resources.getColor(R.color.bg_grey))
                        binding.imagelayout.setBackgroundColor(resources.getColor(R.color.bg_grey))
                        window?.statusBarColor = this.resources.getColor(R.color.bg_grey)
                        selectedColor = "#A9A9A9"
                        layoutcolor = "#A9A9A9"
                        binding.etContent.setHintTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setHintTextColor(resources.getColor(R.color.white))
                        binding.etContent.setTextColor(resources.getColor(R.color.white))
                        binding.etTitle.setTextColor(resources.getColor(R.color.white))
                        textcolor="#FFFFFF"

                    }
                    R.id.saveNote -> {
                        if (up == 1) {
                            myUpdateData()
                        }
                        if (up == 0) {
                            if (binding.etContent.text.isNotEmpty()) {
                                saveData()
                            } else if (binding.etTitle.text.isNotEmpty()) {
                                saveData()
                            } else if (binding.etContent.text.isEmpty() && binding.etTitle.text.isEmpty()) {
                                val a = Intent(requireContext(), HomeActivity::class.java)
                                startActivity(a)
                                requireActivity().finish()
                            }
                        }
                    }
                    R.id.delete -> {
                        if (up == 1) {
                            delData()
                        }
                        if (up == 0) {
                                val a = Intent(requireContext(), HomeActivity::class.java)
                                startActivity(a)
                                requireActivity().finish()
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
                val a=Intent(requireContext(),HomeActivity::class.java)
                startActivity(a)
                requireActivity().finish()
                Toast.makeText(requireContext(), "Data Saved", Toast.LENGTH_SHORT).show()
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
            up=1

            binding.etTitle.setText(title)
            binding.etContent.setText(desc)
            binding.layout.setBackgroundColor(Color.parseColor(color))
            val window = (activity as? AppCompatActivity)?.window
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window?.statusBarColor = Color.parseColor(layoutcolor)
            binding.etContent.setTextColor(Color.parseColor(textcolor))
            binding.etTitle.setTextColor(Color.parseColor(textcolor))
            binding.etContent.setHintTextColor(Color.parseColor(textcolor))
            binding.etTitle.setHintTextColor(Color.parseColor(textcolor))

            Glide.with(requireContext()).load(imagepath).into(binding.ivImage)
        }
        else{
            up=0
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
                val a= Intent(requireContext(), HomeActivity::class.java)
                startActivity(a)
                requireActivity().finish()
                Toast.makeText(requireContext(), " Data Updated", Toast.LENGTH_SHORT).show()

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
                val a = Intent(requireContext(), HomeActivity::class.java)
                startActivity(a)
                requireActivity().finish()
            }
        }
        DelData().execute()
    }
}
