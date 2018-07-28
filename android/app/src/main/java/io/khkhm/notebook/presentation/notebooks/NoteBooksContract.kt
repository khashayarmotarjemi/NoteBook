package io.khkhm.notebook.presentation.notebooks

import io.khkhm.notebook.domain.NoteBook
import io.khkhm.notebook.presentation.BasePresenter
import io.khkhm.notebook.presentation.BaseView

/**
 * Created by khashayar on 7/17/18.
 */
interface NoteBooksContract {
    interface Presenter : BasePresenter {
        fun onNoteBookClick(notebook: NoteBook)
        fun onNoteBookLongClick(notebook: NoteBook)
        fun onNoteBookDelete(notebook: NoteBook)
    }

    interface View : BaseView<Presenter> {
        fun showNotebooks(notebooks : ArrayList<NoteBook>)
        fun openNoteBook(notebook: NoteBook)
        fun showNotebookDetail(notebook: NoteBook)
    }
}