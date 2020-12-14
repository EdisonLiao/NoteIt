package com.kikulabs.noteusingroom.activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.awesomedialog.*
import com.kikulabs.noteusingroom.R
import com.kikulabs.noteusingroom.databinding.ActivityEditBinding
import com.kikulabs.noteusingroom.entity.Note
import com.kikulabs.noteusingroom.viewModel.NotesViewModel


class EditActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditBinding
    val editNoteExtra = "edit_note_extra"
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var note: Note
    private var isUpdate = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initViewModel()
        initListener()

    }

    private fun initView() {

        if (intent.getParcelableExtra<Note>(editNoteExtra) != null) {

            isUpdate = true
            binding.buttonDelete.visibility = View.VISIBLE

            note = intent.getParcelableExtra(editNoteExtra)!!
            binding.editTextTitle.setText(note.title)
            binding.editTextBody.setText(note.body)
            binding.editTextTitle.setSelection(note.title.length)

            //set spinner position
            val compareValue = note.label
            val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.labels,
                android.R.layout.simple_spinner_item
            )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.spLabel.adapter = adapter

            val spinnerPosition = adapter.getPosition(compareValue)
            binding.spLabel.setSelection(spinnerPosition)

        }
    }

    private fun initViewModel() {
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
    }


    private fun initListener() {
        binding.buttonSave.setOnClickListener(this)
        binding.buttonDelete.setOnClickListener(this)
        binding.toolbar.nibBack.setOnClickListener(this)
    }

    private fun deleteNote(note: Note) {
        notesViewModel.deleteNote(note)
        Toast.makeText(applicationContext, "Note removed", Toast.LENGTH_SHORT).show()
    }

    private fun showDialog() {

        AwesomeDialog.build(this)
            .position(AwesomeDialog.POSITIONS.CENTER)
            .body("The note will be permanently deleted.")
            .icon(R.mipmap.ic_launcher)
            .onPositive(
                "Yes, delete",
                buttonBackgroundColor = android.R.color.white,
                textColor = ContextCompat.getColor(this, android.R.color.black)
            ) {
                deleteNote(note)
                finish()
            }
            .onNegative(
                "Cancel",
                buttonBackgroundColor = R.drawable.bg_btn_black,
                textColor = ContextCompat.getColor(this, android.R.color.white)
            ) {

            }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.nib_back -> {
                onBackPressed()
            }
            R.id.button_save -> {
                val title = binding.editTextTitle.text.toString()
                val body = binding.editTextBody.text.toString()
                val label = binding.spLabel.selectedItem.toString()

                if (title.isEmpty() || body.isEmpty()) {
                    Toast.makeText(applicationContext, "Note cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (isUpdate) {
                        notesViewModel.updateNote(
                            Note(
                                id = note.id,
                                title = title,
                                label = label,
                                body = body
                            )
                        )
                    } else {
                        notesViewModel.insertNote(Note(title = title, label = label, body = body))
                    }
                }

                finish()
            }
            R.id.button_delete -> {
                showDialog()
            }
        }
    }
}