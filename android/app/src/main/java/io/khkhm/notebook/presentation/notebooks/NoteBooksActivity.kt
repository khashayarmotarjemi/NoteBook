package io.khkhm.notebook.presentation.notebooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import io.khkhm.notebook.R
import io.khkhm.notebook.data.NoteRepository
import io.khkhm.notebook.domain.Color
import io.khkhm.notebook.domain.NoteBook
import io.khkhm.notebook.presentation.notes.NotesActivity
import kotlinx.android.synthetic.main.activity_note_books.*
import java.util.*
import kotlin.collections.ArrayList


class NoteBooksActivity : AppCompatActivity(), NoteBooksContract.View {

    val presenter = NoteBookPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_books)

    }

    override fun setPresenter(presenter: NoteBooksContract.Presenter) {
    }

    override fun showNotebooks(notebooks: ArrayList<NoteBook>) {
        val adapter = NoteBookAdapter(notebooks, this,
                object : NoteBookItemListener {
                    override fun onItemClick(notebook: NoteBook) {
                        presenter.onNoteBookClick(notebook)
                    }

                    override fun onItemLongClick(notebook: NoteBook) {
                        presenter.onNoteBookLongClick(notebook)
                    }

                    override fun onItemRemoveClick(notebook: NoteBook) {
                        presenter.onNoteBookDelete(notebook)
                    }
                })

        val dividerItemDecoration = DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL)
        notebooks_list_recv.layoutManager = LinearLayoutManager(this)
        notebooks_list_recv.adapter = adapter

        add_notebook_fab.setOnClickListener {
            val notebook = NoteBook("", Date(), Color.BLUE, ArrayList())
            NoteRepository.addNoteBook(notebook)
            openNoteBook(notebook)
        }
/*
        notebooks_list_recv.addItemDecoration(dividerItemDecoration)
*/
    }

    companion object {

        fun newIntent(packageContext: Context): Intent {
            val intent = Intent(packageContext, NoteBooksActivity::class.java)
            return intent
        }
    }

    override fun openNoteBook(notebook: NoteBook) {
        val intent = NotesActivity.newIntent(this, notebook)
        startActivity(intent)
    }

    override fun showNotebookDetail(notebook: NoteBook) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onResume() {
        super.onResume()
        presenter.start()
    }
}
