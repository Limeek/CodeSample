package ru.limeek.codesample.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.limeek.codesample.domain.usecases.SearchReposUseCase
import ru.limeek.codesample.presentation.viewmodel.RepoListViewModel
import javax.inject.Inject

class RepoListViewModelFactory @Inject constructor(private val searchReposUseCase: SearchReposUseCase): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RepoListViewModel::class.java)){
            return RepoListViewModel(searchReposUseCase) as T
        }
        throw IllegalStateException()
    }
}