package io.khkhm.notebook.data

import io.khkhm.notebook.domain.Color
import io.khkhm.notebook.domain.Note
import io.khkhm.notebook.domain.NoteBook
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Singleton


@Singleton
object NoteRepository {

    private var noteBooks: ArrayList<NoteBook> = ArrayList()

    var currentNoteBook: NoteBook? = null


    fun updateCurrentNoteBook(newNoteBook: NoteBook) {
        if (currentNoteBook == null) {
            currentNoteBook = newNoteBook
        } else {
            syncNoteBooksAndRun { currentNoteBook = noteBooks.find { it.id == newNoteBook.id }!! }
        }
    }

    //
    private fun updateNoteBooks(notebooks: List<NoteBook>): List<NoteBook> {
        noteBooks = ArrayList(notebooks)
        return notebooks
    }

    fun syncNoteBooksAndRun(afterSync: () -> Unit) {

        var oldId: String? = null

        if (currentNoteBook != null) {
            oldId = currentNoteBook!!.id
        }

        Repository.getAllNotebooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<NoteBook>>() {

                    override fun onSuccess(notebooks: List<NoteBook>) {
                        noteBooks = ArrayList(notebooks)
                        if (oldId != null) {
                            val requiredNotebook: NoteBook? = notebooks.find { it.id == oldId }
                            if (requiredNotebook != null) {
                                currentNoteBook = requiredNotebook
                            }
                        }
                        afterSync()
                    }


                    override fun onError(e: Throwable) {
                        Timber.e(e)
                    }
                })

    }


    fun getAllNoteBooks(): Single<List<NoteBook>> {
        return Repository.getAllNotebooks().map { updateNoteBooks(it) }
    }

    fun getNoteBookById(id: String): Single<NoteBook> {
        val notebook = noteBooks.find { it.id == id }

        return if (notebook != null) {
            Single.just(notebook)
        } else {
            Repository.getAllNotebooks().map { it.find { it.id == id } }
        }
    }


    fun addNoteBook(noteBook: NoteBook): Single<NoteBook> {
        /*noteBooks.add(noteBook)*/
        return Repository.createNotebook(noteBook.name, noteBook.color)
    }


    fun removeNoteBook(noteBook: NoteBook): Single<BaseResponse> {
        return Repository.removeNotebook(noteBook.id)
    }


    //t
    fun removeNote(note: Note): Single<BaseResponse> {
        return Repository.removeNote(note.notebookId, note.id)
    }

    //
    fun addNoteToNotebook(note: Note, notebook: NoteBook): Single<Note> {
        return Repository.createNewNote(notebook.id, note.text, note.title, note.color)
    }

    fun updateNote(note: Note, newText: String? = null, newColor: Color? = null, newTitle: String? = null) {
        Repository.updateNote(note.notebookId, note.id, newText, newTitle, newColor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Note>() {

                    override fun onSuccess(updatedNote: Note) {
                        Timber.e("updated note: text : ${updatedNote.text} title : ${updatedNote.title} color : ${updatedNote.color} ")
                    }


                    override fun onError(e: Throwable) {
                        Timber.e(e)
                    }
                })
    }
/*
    //
    fun updateNoteText(note: Note, newText: String) {


        Timber.e(newText)
        findNote(note)?.text = newText
        Timber.e("founded note: " + findNote(note)?.text)

    }*/

/*

    //
    fun updateNoteColor(note: Note, newColor: Color) {
        findNote(note)?.color = newColor
    }

    //
    fun updateNoteTitle(note: Note, newTitle: String) {
        findNote(note)?.title = newTitle
    }

*/

    //t
    fun updateNoteBook(notebook: NoteBook, newColor: Color? = null, newName: String? = null) {
        Repository.updateNoteBook(notebook.id, newName, newColor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NoteBook>() {

                    override fun onSuccess(updatedNotebook: NoteBook) {
                        Timber.e("updated note: name : ${updatedNotebook.name} color : ${updatedNotebook.color}  ")
                    }


                    override fun onError(e: Throwable) {
                        Timber.e(e)
                    }
                })
    }


    private fun findNote(note: Note): Note? {
        Timber.e("called")
        for (notebook: NoteBook in noteBooks) {
            for (fNote: Note in notebook.notes) {
                if (fNote.id == note.id) {
                    Timber.e(fNote.id)
                    return fNote
                }
            }
        }
        Timber.e("not found")
        return null
    }

    private fun findNoteBook(notebook: NoteBook): NoteBook? {
        for (fNotebook: NoteBook in noteBooks) {
            if (notebook.id == fNotebook.id)
                return fNotebook
        }
        return null
    }

    private fun findNoteBook(note: Note): NoteBook? {
        for (notebook: NoteBook in noteBooks) {
            for (fNote: Note in notebook.notes) {
                if (fNote.id == note.id) {
                    Timber.e(fNote.id)
                    return notebook
                }
            }
        }
        return null
    }


}


// original local codes

/*
package io.khkhm.notebook.data

import io.khkhm.notebook.domain.Color
import io.khkhm.notebook.domain.Note
import io.khkhm.notebook.domain.NoteBook
import io.reactivex.Observable
import timber.log.Timber
import java.util.*
import javax.inject.Singleton
import kotlin.collections.ArrayList


@Singleton
object NoteRepository {
    private val noteBooks = ArrayList<NoteBook>()

    lateinit var currentNoteBook: NoteBook

    fun updateCurrentNoteBook(newNoteBook: NoteBook) {
        currentNoteBook = noteBooks.find { it.id == newNoteBook.id }!!
    }

    init {
        noteBooks.add(NoteBook("Daily", Date(), Color.BLUE))
        noteBooks.add(NoteBook("University", Date(), Color.GREEN))
        noteBooks.add(NoteBook("Shopping", Date(), Color.YELLOW))
        noteBooks.add(NoteBook("Journal", Date(), Color.PURPLE))
        noteBooks.add(NoteBook("notebook1", Date(), Color.RED))
        noteBooks.add(NoteBook("Math", Date(), Color.BLUE))
        noteBooks.add(NoteBook("to-do", Date(), Color.RED))


        noteBooks[0].notes.add(Note("Lorem ipsum dolor sit amet, in vix nibh commune salutatus. Mea aperiri mnesarchum at. Eu velit intellegebat cum. Eum facilisi tractatos at. Nostro mollis cu nec.\n", "first", Date(), Color.BLUE))
        noteBooks[0].notes.add(Note("Quem porro no sea, has ei nostrud equidem", "second", Date(), Color.RED))
        noteBooks[0].notes.add(Note("Et quo ferri equidem. Te dicta dignissim aliquando vim", "third", Date(), Color.RED))
        noteBooks[0].notes.add(Note("Ei est hinc clita legere. Duo cu prima posidonium necessitatibus, ne qui adhuc iriure. ", "fourth", Date(), Color.YELLOW))
        noteBooks[1].notes.add(Note("No scaevola accusata omittantur pro. Dicunt vocibus tincidunt pri et, his ne probo discere. Mea at volumus nominavi adolescens", "first", Date(), Color.YELLOW))
        noteBooks[1].notes.add(Note("Ex quo primis viderer inimicus", "second", Date(), Color.BLUE))
        noteBooks[1].notes.add(Note("Quem porro no sea, has ei nostrud equidem", "third", Date(), Color.BLUE))
        noteBooks[2].notes.add(Note("Et quo ferri equidem. Te dicta dignissim aliquando vim", "first", Date(), Color.PURPLE))
        noteBooks[2].notes.add(Note("Pri ex alterum abhorreant sadipscing", "second", Date(), Color.PURPLE))
        noteBooks[3].notes.add(Note("ex qui primis epicurei", "first", Date(), Color.RED))
        noteBooks[3].notes.add(Note("Ex quo primis viderer inimicus", "second", Date(), Color.RED))
        noteBooks[3].notes.add(Note("No scaevola accusata omittantur pro. Dicunt vocibus tincidunt pri et, his ne probo discere. Mea at volumus nominavi adolescens", "third", Date(), Color.RED))
        noteBooks[3].notes.add(Note("Quem porro no sea, has ei nostrud equidem", "fourth", Date(), Color.GREEN))
        noteBooks[4].notes.add(Note("Lorem ipsum dolor sit amet, in vix nibh commune salutatus. Mea aperiri mnesarchum at. Eu velit intellegebat cum. Eum facilisi tractatos at. Nostro mollis cu nec.\n", "first", Date(), Color.BLUE))
        noteBooks[4].notes.add(Note("Quem porro no sea, has ei nostrud equidem", "second", Date(), Color.RED))
        noteBooks[4].notes.add(Note("Et quo ferri equidem. Te dicta dignissim aliquando vim", "third", Date(), Color.RED))
        noteBooks[5].notes.add(Note("Ei est hinc clita legere. Duo cu prima posidonium necessitatibus, ne qui adhuc iriure. ", "fourth", Date(), Color.YELLOW))
        noteBooks[5].notes.add(Note("No scaevola accusata omittantur pro. Dicunt vocibus tincidunt pri et, his ne probo discere. Mea at volumus nominavi adolescens", "first", Date(), Color.YELLOW))
        noteBooks[6].notes.add(Note("Ex quo primis viderer inimicus", "second", Date(), Color.BLUE))
        noteBooks[6].notes.add(Note("Quem porro no sea, has ei nostrud equidem", "third", Date(), Color.BLUE))
        noteBooks[6].notes.add(Note("Et quo ferri equidem. Te dicta dignissim aliquando vim", "first", Date(), Color.PURPLE))
        noteBooks[6].notes.add(Note("Pri ex alterum abhorreant sadipscing", "second", Date(), Color.PURPLE))

    }

    fun getAllNoteBooks(): Observable<ArrayList<NoteBook>> {
        return Observable.fromArray(noteBooks)
    }

    fun getNoteBookById(uuid: String): Observable<NoteBook> {
        return Observable.just(noteBooks.first { it.id == uuid })
    }

    fun addNoteBook(noteBook: NoteBook) {
        noteBooks.add(noteBook)
    }

    fun removeNoteBook(noteBook: NoteBook) {
        noteBooks.remove(noteBook)
    }

    fun removeNoteBook(uuid: String) {
        noteBooks.remove(noteBooks.first { it.id == uuid })
    }

    fun removeNote(note: Note) {
        val notebook = findNoteBook(note)

        notebook?.notes?.remove(findNote(note))
        currentNoteBook = notebook!!
    }

    fun addNoteToNotebook(note: Note, notebook: NoteBook) {
        findNoteBook(notebook)?.notes?.add(note)
    }

    fun updateNoteText(note: Note, newText: String) {

        Timber.e(newText)
        findNote(note)?.text = newText
        Timber.e("founded note: " + findNote(note)?.text)

    }

    fun updateNoteBookColor(notebook: NoteBook, newColor: Color) {
        findNoteBook(notebook)?.color = newColor
    }

    fun updateNoteColor(note: Note, newColor: Color) {
        findNote(note)?.color = newColor
    }

    fun updateNoteTitle(note: Note, newTitle: String) {
        findNote(note)?.title = newTitle
    }

    fun updateNoteBookName(notebook: NoteBook, newName: String) {
        findNoteBook(notebook)?.name = newName
    }

    private fun findNote(note: Note): Note? {
        Timber.e("called")
        for (notebook: NoteBook in noteBooks) {
            for (fNote: Note in notebook.notes) {
                if (fNote.id == note.id) {
                    Timber.e(fNote.id)
                    return fNote
                }
            }
        }
        Timber.e("not found")
        return null
    }

    private fun findNoteBook(notebook: NoteBook): NoteBook? {
        for (fNotebook: NoteBook in noteBooks) {
            if (notebook.id == fNotebook.id)
                return fNotebook
        }
        return null
    }

    private fun findNoteBook(note: Note): NoteBook? {
        for (notebook: NoteBook in noteBooks) {
            for (fNote: Note in notebook.notes) {
                if (fNote.id == note.id) {
                    Timber.e(fNote.id)
                    return notebook
                }
            }
        }
        return null
    }


}*/
