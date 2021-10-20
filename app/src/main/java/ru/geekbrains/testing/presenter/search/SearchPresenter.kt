package ru.geekbrains.testing.presenter.search

import androidx.annotation.VisibleForTesting
import ru.geekbrains.testing.presenter.search.PresenterSearchContract
import retrofit2.Response
import ru.geekbrains.testing.model.SearchResponse
import ru.geekbrains.testing.repository.GitHubRepository
import ru.geekbrains.testing.view.ViewContract
import ru.geekbrains.testing.view.search.ViewSearchContract


internal class SearchPresenter internal constructor(
    private val repository: GitHubRepository,
    private var viewContract: ViewSearchContract? = null
) : PresenterSearchContract, GitHubRepository.GitHubRepositoryCallback {

    @VisibleForTesting
    fun getViewContract(): ViewContract? { return viewContract }

    override fun searchGitHub(searchQuery: String) {
        viewContract?.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract?.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewContract?.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract?.displayError("Search results or total count are null")
            }
        } else {
            viewContract?.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewContract?.displayLoading(false)
        viewContract?.displayError()
    }

    override fun onAttach(viewContract: ViewContract) {
        this.viewContract = viewContract as ViewSearchContract
        print("SearchPresenter onAttach()")
    }

    override fun onDetach() {
        viewContract = null
        print("SearchPresenter onDetach()")
    }

}
