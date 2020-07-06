package ru.limeek.codesample.data.repository

import io.reactivex.rxjava3.core.Flowable
import ru.limeek.codesample.data.api.GithubApi
import ru.limeek.codesample.domain.entities.GithubRepo
import javax.inject.Inject

class RepositoriesRepo @Inject constructor(private val githubApi: GithubApi) {

    fun getRepos(repoName: String): Flowable<List<GithubRepo>>{
        return githubApi.searchRepos(repoName).map { it.items }
    }

}