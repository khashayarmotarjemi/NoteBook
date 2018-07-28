package io.khkhm.notebook.presentation.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.khkhm.notebook.data.NoteRepository
import javax.inject.Singleton

/**
 * Created by khashayar on 7/16/18.
 */

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

}