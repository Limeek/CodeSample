package ru.limeek.codesample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.limeek.codesample.domain.entities.GithubRepo

class RepoDetailsViewModel: ViewModel() {
    var repo = MutableLiveData<GithubRepo>()

    fun init(repo: GithubRepo){
        this.repo.value = repo
    }
}