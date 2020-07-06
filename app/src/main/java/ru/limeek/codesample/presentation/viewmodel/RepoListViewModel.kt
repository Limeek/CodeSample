package ru.limeek.codesample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import retrofit2.HttpException
import ru.limeek.codesample.domain.entities.GithubRepo
import ru.limeek.codesample.domain.entities.NetworkError
import ru.limeek.codesample.domain.entities.SearchUseCaseParams
import ru.limeek.codesample.domain.usecases.SearchReposUseCase
import java.io.IOException
import java.util.concurrent.TimeUnit

class RepoListViewModel(private var searchUseCase: SearchReposUseCase) : ViewModel() {

    private var searchSubject = PublishSubject.create<String>()
    private var searchDisposable: Disposable? = null
    var queryText: String = ""

    var currentState = MutableLiveData<RepoListState>().apply { value = RepoListState.Start }


    init {
        subscribeToSearchSubject()
    }

    fun startRepoSearch(repoName: String) {
        queryText = repoName
        if (repoName.isNotEmpty())
            searchSubject.onNext(repoName)
        else {
            searchDisposable?.dispose()
            subscribeToSearchSubject()
            currentState.value = RepoListState.Start
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchDisposable?.dispose()
    }

    private fun subscribeToSearchSubject() {
        searchDisposable = searchSubject
            .subscribeOn(Schedulers.io())
            .doOnNext { currentState.postValue(RepoListState.Loading) }
            .debounce(300, TimeUnit.MILLISECONDS)
            .switchMap { searchUseCase.execute(SearchUseCaseParams(it)).toObservable() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { currentState.value = RepoListState.Error(getNetworkError(it)) }
            .retry()
            .subscribe(
                {
                    currentState.value = if (it.isNotEmpty())
                        RepoListState.Ready(it)
                    else
                        RepoListState.Empty
                },
                { currentState.value = RepoListState.Error(getNetworkError(it)) }
            )
    }

    private fun getNetworkError(e: Throwable): NetworkError {
        return when (e) {
            is IOException -> NetworkError.NoInternetConnection
            is HttpException -> NetworkError.HttpError(e.code())
            else -> NetworkError.Unprocessed
        }
    }
}

sealed class RepoListState {
    object Empty : RepoListState()
    object Loading : RepoListState()
    object Start : RepoListState()
    class Ready(var repoList: List<GithubRepo>) : RepoListState()
    class Error(var error: NetworkError) : RepoListState()
}
