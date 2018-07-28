package io.khkhm.notebook.presentation.notebooks

import io.khkhm.notebook.data.NoteRepository
import io.khkhm.notebook.domain.NoteBook

/**
 * Created by khashayar on 7/17/18.
 */
class NoteBookPresenter(private val view: NoteBooksContract.View) : NoteBooksContract.Presenter {

    private val noteBookRepo = NoteRepository

    override fun start() {
        noteBookRepo.getAllNoteBooks().subscribe {
            view.showNotebooks(it)
        }
    }

    override fun onNoteBookClick(notebook: NoteBook) {
        view.openNoteBook(notebook)
    }

    override fun onNoteBookLongClick(notebook: NoteBook) {
        view.showNotebookDetail(notebook)
    }

    override fun onNoteBookDelete(notebook: NoteBook) {
        noteBookRepo.removeNoteBook(notebook)
    }
}