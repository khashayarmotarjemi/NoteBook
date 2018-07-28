package io.khkhm.notebook.presentation.notes

import io.khkhm.notebook.domain.Note
import io.khkhm.notebook.domain.NoteBook

/**
 * Created by khashayar on 7/17/18.
 */
class NotesPresenter(private val view: NotesContract.View,
                     private val notebook: NoteBook) : NotesContract.Presenter {

    override fun start() {
        view.showNotes(notebook.notes)
    }

    override fun onNoteClick(note: Note) {
        view.openNote(note)
    }

    override fun onNoteLongClick(note: Note) {
        view.showNoteDetail(note)
    }
}