package ru.geekbrains.testing.view.search

import ru.geekbrains.testing.model.SearchResult
import ru.geekbrains.testing.view.ViewContract

internal interface ViewSearchContract : ViewContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
