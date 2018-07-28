package io.khkhm.notebook.presentation.notes

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import io.khkhm.notebook.R
import io.khkhm.notebook.data.NoteRepository
import io.khkhm.notebook.domain.Color
import io.khkhm.notebook.domain.Note
import io.khkhm.notebook.domain.NoteBook
import kotlinx.android.synthetic.main.activity_notes.*
import timber.log.Timber
import java.util.*

class NotesActivity : AppCompatActivity(), NotesContract.View {

    lateinit var mPresenter: NotesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        var notebook: NoteBook

        Timber.e("oncraete")

        if (intent.extras != null) {
            notebook = intent.extras[EXTRA_NOTEBOOK] as NoteBook
            NoteRepository.updateCurrentNoteBook(notebook)
        } else {
            notebook = NoteRepository.currentNoteBook
        }

        Timber.e("notes: " + notebook.notes.size)
        mPresenter = NotesPresenter(this, notebook)

        note_act_notebook_name.setText(notebook.name)

        note_act_notebook_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                NoteRepository.updateNoteBookName(notebook, p0.toString())
            }
        })

        note_act_add_note.setOnClickListener {
            val note = Note("", "", Date(), Color.BLUE)
            NoteRepository.addNoteToNotebook(note, notebook)
            openNote(note)
        }

        when (notebook.color) {
            Color.BLUE ->
                note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.blue))

            Color.RED ->
                note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.red))

            Color.GREEN ->
                note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.green))

            Color.PURPLE ->
                note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.purple))

            Color.YELLOW ->
                note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.yellow))

        }

        note_act_notebook_color.setOnClickListener {
            val colors = arrayOf<CharSequence>("blue", "red", "green", "purple", "yellow")

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pick a color!")
            builder.setItems(colors, DialogInterface.OnClickListener { dialogInterface, i ->
                when (i) {
                    0 -> {
                        note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.blue))
                        NoteRepository.updateNoteBookColor(notebook, Color.BLUE)
                    }

                    1 -> {
                        note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.red))
                        NoteRepository.updateNoteBookColor(notebook, Color.RED)
                    }

                    2 -> {
                        note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.green))
                        NoteRepository.updateNoteBookColor(notebook, Color.GREEN)
                    }

                    3 -> {
                        note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.purple))
                        NoteRepository.updateNoteBookColor(notebook, Color.PURPLE)
                    }

                    4 -> {
                        note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.yellow))
                        NoteRepository.updateNoteBookColor(notebook, Color.YELLOW)
                    }
                }
            })
            builder.show()
        }


    }

    override fun setPresenter(presenter: NotesContract.Presenter) {}

    override fun showNotes(notes: ArrayList<Note>) {
        val adapter = NotesAdapter(notes, this,
                object : NoteItemListener {
                    override fun onItemClick(note: Note) {
                        mPresenter.onNoteClick(note)
                    }

                    override fun onItemLongClick(note: Note) {
                        mPresenter.onNoteLongClick(note)
                    }
                })


        notes_recv.layoutManager = GridLayoutManager(this, 2)
        notes_recv.adapter = adapter
    }

    override fun openNote(note: Note) {
        startActivity(NoteDetailActivity.newIntent(this, note))
    }

    override fun showNoteDetail(note: Note) {
        Toast.makeText(this, "show detail", Toast.LENGTH_LONG).show()
    }


    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    companion object {
        private val EXTRA_NOTEBOOK = "extra.notebook.io"

        fun newIntent(packageContext: Context, notebook: NoteBook): Intent {
            val intent = Intent(packageContext, NotesActivity::class.java)
            intent.putExtra(EXTRA_NOTEBOOK, notebook)
            return intent
        }
    }
}
