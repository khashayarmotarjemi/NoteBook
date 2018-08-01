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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_notes.*
import timber.log.Timber
import java.util.*

class NotesActivity : AppCompatActivity(), NotesContract.View {

    lateinit var mPresenter: NotesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        Timber.e("oncraete")


    }

    private fun initActivity(notebook: NoteBook) {
        Timber.e("notes: " + notebook.notes.size)
        mPresenter = NotesPresenter(this, notebook)

        note_act_notebook_name.setText(notebook.name)

        note_act_notebook_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                NoteRepository.updateNoteBook(notebook, newName = p0.toString())
            }
        })

        note_act_add_note.setOnClickListener {
            val newNote = Note("", "", Date(), Color.BLUE.name)
            NoteRepository.addNoteToNotebook(newNote, notebook).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Note>() {

                        override fun onSuccess(note: Note) {
                            openNote(newNote)
                        }

                        override fun onError(e: Throwable) {
                            Timber.e(e)
                        }
                    })
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
                        NoteRepository.updateNoteBook(notebook, newColor = Color.BLUE)
                    }

                    1 -> {
                        note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.red))
                        NoteRepository.updateNoteBook(notebook, newColor = Color.RED)
                    }

                    2 -> {
                        note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.green))
                        NoteRepository.updateNoteBook(notebook, newColor = Color.GREEN)
                    }

                    3 -> {
                        note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.purple))
                        NoteRepository.updateNoteBook(notebook, newColor = Color.PURPLE)
                    }

                    4 -> {
                        note_act_notebook_color.setCardBackgroundColor(resources.getColor(R.color.yellow))
                        NoteRepository.updateNoteBook(notebook, newColor = Color.YELLOW)
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
        //this.finish()
    }

    override fun showNoteDetail(note: Note) {
        Toast.makeText(this, "show detail", Toast.LENGTH_LONG).show()
    }


    override fun onResume() {
        super.onResume()
        if (intent.extras != null) {
            val notebook = intent.extras[EXTRA_NOTEBOOK] as NoteBook
            NoteRepository.updateCurrentNoteBook(notebook)
            initActivity(notebook)
            mPresenter.start()

        } else {
            NoteRepository.syncNoteBooksAndRun {
                val notebook = NoteRepository.currentNoteBook
                initActivity(notebook!!)
                mPresenter.start()

            }
        }
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
