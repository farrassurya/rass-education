package com.example.rass_education.Note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.rass_education.data.local.AppDatabase
import com.example.rass_education.data.local.entity.Note
import com.example.rass_education.databinding.DialogAddNoteBinding
import com.example.rass_education.databinding.FragmentNoteBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteAdapter: NoteAdapter
    private val database by lazy { AppDatabase.getDatabase(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeNotes()

        binding.fabAdd.setOnClickListener {
            showNoteDialog(null)
        }
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(
            onEdit = { note -> showNoteDialog(note) },
            onDelete = { note -> confirmDelete(note) }
        )
        binding.rvNotes.adapter = noteAdapter
    }

    private fun observeNotes() {
        viewLifecycleOwner.lifecycleScope.launch {
            database.noteDao().getAllNotes().collectLatest { notes ->
                noteAdapter.submitList(notes)
                binding.tvEmptyNote.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun showNoteDialog(note: Note?) {
        val dialogBinding = DialogAddNoteBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)

        val dialog = builder.create()

        if (note != null) {
            dialogBinding.tvDialogTitle.text = "Edit Catatan"
            dialogBinding.etTitle.setText(note.title)
            dialogBinding.etContent.setText(note.content)
            dialogBinding.btnSave.text = "Perbarui"
        }

        dialogBinding.btnSave.setOnClickListener {
            val title = dialogBinding.etTitle.text.toString().trim()
            val content = dialogBinding.etContent.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val newNote = note?.copy(title = title, content = content, date = System.currentTimeMillis())
                    ?: Note(title = title, content = content)

                viewLifecycleOwner.lifecycleScope.launch {
                    if (note == null) {
                        database.noteDao().insertNote(newNote)
                    } else {
                        database.noteDao().updateNote(newNote)
                    }
                    dialog.dismiss()
                }
            } else {
                Toast.makeText(requireContext(), "Judul dan isi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun confirmDelete(note: Note) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Catatan")
            .setMessage("Apakah Anda yakin ingin menghapus catatan ini?")
            .setPositiveButton("Hapus") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    database.noteDao().deleteNote(note)
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}