package ru.geekbrains.testing.repository

import retrofit2.Response
import ru.geekbrains.testing.model.SearchResponse

interface RepositoryCallback {
    fun handleGitHubResponse(response: Response<SearchResponse?>?)
    fun handleGitHubError()
}