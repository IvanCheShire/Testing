package ru.geekbrains.testing.repository

import retrofit2.Response
import ru.geekbrains.testing.model.SearchResponse
import ru.geekbrains.testing.repository.RepositoryContract

internal class GitHubRepository : RepositoryContract {

    override fun searchGithub(
        query: String,
        callback: RepositoryCallback
    ) {
        callback.handleGitHubResponse(Response.success(SearchResponse(42, listOf())))
    }
}