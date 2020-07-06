package ru.limeek.codesample.domain.usecases

import io.reactivex.rxjava3.core.Flowable
import ru.limeek.codesample.data.repository.RepositoriesRepo
import ru.limeek.codesample.domain.entities.GithubRepo
import ru.limeek.codesample.domain.entities.SearchUseCaseParams
import javax.inject.Inject

class SearchReposUseCase @Inject constructor(private var repositoriesRepo: RepositoriesRepo): UseCase<SearchUseCaseParams, Flowable<List<GithubRepo>>> {
    override fun execute(params: SearchUseCaseParams): Flowable<List<GithubRepo>> {
        return repositoriesRepo.getRepos(params.repoName)
    }
}