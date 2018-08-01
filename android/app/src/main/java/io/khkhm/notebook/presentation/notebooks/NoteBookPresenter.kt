package io.khkhm.notebook.presentation.notebooks

import io.khkhm.notebook.data.BaseResponse
import io.khkhm.notebook.data.NoteRepository
import io.khkhm.notebook.domain.NoteBook
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by khashayar on 7/17/18.
 */
class NoteBookPresenter(private val view: NoteBooksContract.View) : NoteBooksContract.Presenter {

    private val noteBookRepo = NoteRepository

    override fun start() {
        noteBookRepo.getAllNoteBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<NoteBook>>() {

                    override fun onSuccess(notebooks: List<NoteBook>) {
                        view.showNotebooks(ArrayList(notebooks))
/*
                        Timber.e("size: ${notebooks.size.toString()}  color: ${notebooks[0].color}")
*/
                    }


                    override fun onError(e: Throwable) {
                        Timber.e(e)
                    }
                })
    }

    override fun onNoteBookClick(notebook: NoteBook) {
        view.openNoteBook(notebook)
    }

    override fun onNoteBookLongClick(notebook: NoteBook) {
        view.showNotebookDetail(notebook)
    }

    override fun onNoteBookDelete(notebook: NoteBook) {
        noteBookRepo.removeNoteBook(notebook).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<BaseResponse>() {

                    override fun onSuccess(response: BaseResponse) {
                        Timber.e(response.response)
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                    }
                })
    }
}