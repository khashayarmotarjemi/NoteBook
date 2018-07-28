package io.khkhm.notebook.presentation.di

import dagger.Component
import io.khkhm.notebook.presentation.MainActivity
import javax.inject.Singleton

@Singleton
interface AppComponent {

    fun inject(target: MainActivity)

}