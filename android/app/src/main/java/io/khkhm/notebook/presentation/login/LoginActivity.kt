package io.khkhm.notebook.presentation.login

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import io.khkhm.notebook.R
import io.khkhm.notebook.data.BaseResponse
import io.khkhm.notebook.data.Repository
import io.khkhm.notebook.presentation.notebooks.NoteBooksActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : AppCompatActivity(), LoginContract.View {

    val presenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testRepo()
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
            presenter.onLoginBtnClick(
                    login_username.text.toString(),
                    login_password.text.toString()
            )
        }
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
    }

    override fun setLoadingIndicator(active: Boolean) {
        login_pbar.visibility = if (active) View.VISIBLE else View.GONE
    }

    override fun showLoginAttemptResult(success: Boolean) {
        if (success) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show()
        }
    }

    override fun gotoNotebooksList() {
        startActivity(NoteBooksActivity.newIntent(this))
    }

    override fun getContext(): Context {
        return this
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }


    private fun testRepo() {
        /*Repository.removeNote("5b5d1d736f44c00b4fa8ff06",
                "5b5d2883b51ac60b9da5f2b9")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<BaseResponse>() {

                    override fun onSuccess(res: BaseResponse) {
                        Timber.e(res.response)
                    }


                    override fun onError(e: Throwable) {
                        Timber.e(e)
                    }
                })*/

        /*Repository.removeNotebook("5b5daa55ce8cba1bcecb7aec")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<BaseResponse>() {

                    override fun onSuccess(res: BaseResponse) {
                        Timber.e(res.response)
                    }


                    override fun onError(e: Throwable) {
                        Timber.e(e)
                    }
                })*/

        /*Repository.updateNoteBook("5b5daa55ce8cba1bcecb7aec",
                "test 3 modified notebook name",
                Color.GREEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NoteBook>() {

                    override fun onSuccess(noteBook: NoteBook) {
                        Timber.e(noteBook.name)
                    }


                    override fun onError(e: Throwable) {
                        Timber.e(e)
                    }
                })*/

        /*Repository.updateNote("5b5daa55ce8cba1bcecb7aec",
                "5b5db38886b15d1ca49d5c78",
                "test 2 from android text",
                "test 2 from android title",
                Color.YELLOW)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Note>() {

                    override fun onSuccess(note: Note) {
                        Timber.e(note.text)
                    }


                    override fun onError(e: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })*/

        /*Repository.createNewNote("5b5daa55ce8cba1bcecb7aec",
                "test 1 from android text",
                "test 1 from android title",
                Color.BLUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Note>() {

                    override fun onSuccess(note: Note) {
                        Timber.e(note.text)
                    }


                    override fun onError(e: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })*/


        /*Repository.createNotebook("the notebook from android", Color.RED)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NoteBook>() {

                    override fun onSuccess(notebook: NoteBook) {
                        Timber.e(notebook.name)
                    }


                    override fun onError(e: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })*/

        /*Repository.getAllNotebooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<NoteBook>>() {

                    override fun onSuccess(notebooks: List<NoteBook>) {
                        Timber.e(notebooks[0].notes[0].text)
                    }


                    override fun onError(e: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })*/
    }
}
