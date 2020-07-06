package ru.limeek.codesample.presentation.di.modules.fragments

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ru.limeek.codesample.presentation.di.scopes.FragmentScope
import ru.limeek.codesample.presentation.view.RepoDetailsFragment
import ru.limeek.codesample.presentation.viewmodel.RepoDetailsViewModel

@Module
class RepoDetailsFragmentModule {
    @Provides
    @FragmentScope
    fun provideRepoDetailsViewModel(fragment: RepoDetailsFragment): RepoDetailsViewModel{
        return ViewModelProvider(fragment).get(RepoDetailsViewModel::class.java)
    }
}