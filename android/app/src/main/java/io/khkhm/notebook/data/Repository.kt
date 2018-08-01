package io.khkhm.notebook.data

import android.content.Context
import io.khkhm.notebook.domain.Color
import io.khkhm.notebook.domain.Note
import io.khkhm.notebook.domain.NoteBook
import io.reactivex.Single

/**
 * Created by khashayar on 7/29/18.
 */
object Repository {
    var apiServices: ApiServices? = null
    private val apiNotInitError = "Repository haven't been initialized"

    fun init(context: Context) {
        apiServices =
                ApiClient.getClient(context.applicationContext)
                        .create(ApiServices::class.java)

    }

    private fun addNotebookIds(notebooks: List<NoteBook>): List<NoteBook> {
        notebooks.map { noteBook -> noteBook.notes.forEach { it.notebookId = noteBook.id } }
        return notebooks
    }


    fun getAllNotebooks(): Single<List<NoteBook>> {
        if (checkApiServicesInitialized()) {
            return apiServices!!.fetchAllNotes().map { addNotebookIds(it) }
        } else {
            throw IllegalStateException(apiNotInitError)
        }
    }

    fun createNotebook(name: String, color: Color): Single<NoteBook> {
        if (checkApiServicesInitialized()) {
            return apiServices!!.createNewNotebook(name, color.name)
        } else {
            throw IllegalStateException(apiNotInitError)
        }
    }

    fun createNewNote(notebookId: String, text: String, title: String, color: Color): Single<Note> {
        if (checkApiServicesInitialized()) {
            return apiServices!!.createNewNoteInNotebook(notebookId, text, title, color.name)
        } else {
            throw IllegalStateException(apiNotInitError)
        }
    }

    fun updateNote(notebookId: String, noteId: String, text: String?, title: String?, color: Color?): Single<Note> {
        if (checkApiServicesInitialized()) {
            return apiServices!!.updateNoteInNotebook(notebookId, noteId, text, title, color?.name)
        } else {
            throw IllegalStateException(apiNotInitError)
        }
    }

    fun updateNoteBook(notebookId: String, name: String?, color: Color?): Single<NoteBook> {
        if (checkApiServicesInitialized()) {
            return apiServices!!.updateNotebook(notebookId, name, color?.name)
        } else {
            throw IllegalStateException(apiNotInitError)
        }
    }

    fun removeNotebook(notebookId: String): Single<BaseResponse> {
        if (checkApiServicesInitialized()) {
            return apiServices!!.removeNotebook(notebookId)
        } else {
            throw IllegalStateException(apiNotInitError)
        }
    }

    fun removeNote(notebookId: String, noteId: String): Single<BaseResponse> {
        if (checkApiServicesInitialized()) {
            return apiServices!!.removeNote(notebookId, noteId)
        } else {
            throw IllegalStateException(apiNotInitError)
        }
    }

    private inline fun checkApiServicesInitialized(): Boolean {
        if (apiServices == null) {
            return false
        }
        return true
    }

}