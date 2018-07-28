package io.khkhm.notebook.presentation

import android.os.Bundle
import io.khkhm.notebook.R
import io.khkhm.notebook.data.NoteRepository
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var mNotebookRepo: NoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAppComponent().inject(this)


    }
}
