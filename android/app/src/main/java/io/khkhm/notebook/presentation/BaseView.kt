package io.khkhm.notebook.presentation

interface BaseView<T : BasePresenter> {
    fun setPresenter(presenter: T)
}
