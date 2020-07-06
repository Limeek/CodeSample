package ru.limeek.codesample.presentation.app

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ru.limeek.codesample.presentation.di.components.DaggerAppComponent

class App: DaggerApplication() {
    lateinit var component: AndroidInjector<App>

    override fun onCreate() {
        component = DaggerAppComponent
            .factory()
            .create(this)

        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }
}