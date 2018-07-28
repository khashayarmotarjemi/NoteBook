package io.khkhm.notebook.presentation

import android.support.v7.app.AppCompatActivity
import io.khkhm.notebook.NoteBookApplication
import io.khkhm.notebook.presentation.di.AppComponent

/**
 * Created by khashayar on 7/16/18.
 */
open class BaseActivity : AppCompatActivity() {
    protected fun getAppComponent(): AppComponent {
        return (application as NoteBookApplication).applicationComponent
    }
}