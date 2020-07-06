package ru.limeek.codesample.presentation.di.components

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import ru.limeek.codesample.presentation.app.App
import ru.limeek.codesample.presentation.di.modules.BuilderModule
import ru.limeek.codesample.presentation.di.modules.RetrofitModule
import javax.inject.Singleton

@Component(
    modules = [
        RetrofitModule::class,
        BuilderModule::class
    ]
)
@Singleton
interface AppComponent: AndroidInjector<App> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<App>{
        abstract override fun create(@BindsInstance app: App): AppComponent
    }
}