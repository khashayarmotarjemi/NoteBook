package io.khkhm.notebook.presentation.notes

import io.khkhm.notebook.domain.Note
import io.khkhm.notebook.presentation.BasePresenter
import io.khkhm.notebook.presentation.BaseView

/**
 * Created by khashayar on 7/17/18.
 */
interface NotesContract {
    interface Presenter : BasePresenter {
        fun onNoteClick(note: Note)
        fun onNoteLongClick(note: Note)
    }

    interface View : BaseView<Presenter> {
        fun showNotes(notes: ArrayList<Note>)
        fun openNote(note: Note)
        fun showNoteDetail(note: Note)
    }
}