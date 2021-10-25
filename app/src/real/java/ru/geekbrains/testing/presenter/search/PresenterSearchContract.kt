package ru.geekbrains.testing.presenter.search

import ru.geekbrains.testing.presenter.PresenterContract

internal interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
    //onAttach
    //onDetach
}
