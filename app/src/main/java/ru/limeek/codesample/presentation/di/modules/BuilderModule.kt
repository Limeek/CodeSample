package ru.limeek.codesample.presentation.di.modules

import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import ru.limeek.codesample.presentation.di.modules.fragments.RepoDetailsFragmentModule
import ru.limeek.codesample.presentation.di.modules.fragments.RepoListFragmentModule
import ru.limeek.codesample.presentation.di.scopes.ActivityScope
import ru.limeek.codesample.presentation.di.scopes.FragmentScope
import ru.limeek.codesample.presentation.view.MainActivity
import ru.limeek.codesample.presentation.view.RepoDetailsFragment
import ru.limeek.codesample.presentation.view.RepoListFragment

@Module(includes = [AndroidInjectionModule::class])
interface BuilderModule {
    @ActivityScope
    @ContributesAndroidInjector
    fun mainActivityInjector(): MainActivity

    @FragmentScope
    @ContributesAndroidInjector(modules = [RepoListFragmentModule::class])
    fun repoListFragmentInjector(): RepoListFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [RepoDetailsFragmentModule::class])
    fun repoDetailsFragmentInjector(): RepoDetailsFragment
}