package io.khkhm.notebook.presentation.login

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import io.khkhm.notebook.R
import io.khkhm.notebook.data.Repository
import io.khkhm.notebook.presentation.notebooks.NoteBooksActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    val presenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        Repository.getAllNotebooks()







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
}
