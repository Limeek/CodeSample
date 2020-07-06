package ru.limeek.codesample.presentation.di.modules.fragments

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ru.limeek.codesample.domain.usecases.SearchReposUseCase
import ru.limeek.codesample.presentation.di.scopes.FragmentScope
import ru.limeek.codesample.presentation.view.RepoListFragment
import ru.limeek.codesample.presentation.viewmodel.RepoListViewModel
import ru.limeek.codesample.presentation.viewmodel.factory.RepoListViewModelFactory

@Module
class RepoListFragmentModule {
    @Provides
    @FragmentScope
    fun provideRepoListViewModel(fragment: RepoListFragment,
                                 searchUseCase: SearchReposUseCase): RepoListViewModel{
        return ViewModelProvider(fragment, RepoListViewModelFactory(searchUseCase)).get(RepoListViewModel::class.java)
    }
}