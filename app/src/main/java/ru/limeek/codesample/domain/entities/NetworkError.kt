package ru.limeek.codesample.domain.entities

sealed class NetworkError {
    object NoInternetConnection: NetworkError()
    class HttpError(var errorCode: Int): NetworkError()
    object Unprocessed: NetworkError()
}