package io.khkhm.notebook

import android.app.Application
import io.khkhm.notebook.presentation.di.AppComponent
import timber.log.Timber

/**
 * Created by khashayar on 7/16/18.
 */
class NoteBookApplication : Application() {
    lateinit var applicationComponent: AppComponent

    private fun initInjector() {
        /*applicationComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()*/
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }


    override fun onCreate() {
        super.onCreate()
        initInjector()
        initTimber()
    }
}