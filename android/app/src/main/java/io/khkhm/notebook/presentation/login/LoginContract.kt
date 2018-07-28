package io.khkhm.notebook.presentation.login

import android.content.Context
import io.khkhm.notebook.presentation.BasePresenter
import io.khkhm.notebook.presentation.BaseView

/**
 * Created by khashayar on 7/17/18.
 */
interface LoginContract {
    interface Presenter : BasePresenter {
        fun onLoginBtnClick(username: String, password: String)
    }

    interface View : BaseView<Presenter> {
        fun setLoadingIndicator(active: Boolean)
        fun showLoginAttemptResult(success: Boolean)
        fun gotoNotebooksList()
        fun getContext(): Context
    }

}