package ru.limeek.codesample.data.api

import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.limeek.codesample.domain.entities.SearchRepoResponse


interface GithubApi{
    @GET("/search/repositories")
    fun searchRepos(@Query("q") repoName: String) : Flowable<SearchRepoResponse>
}