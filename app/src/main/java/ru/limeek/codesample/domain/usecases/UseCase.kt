package ru.limeek.codesample.domain.usecases

interface UseCase<UseCaseParameters, Out>{
    fun execute(params: UseCaseParameters): Out
}

interface UseCaseParameters