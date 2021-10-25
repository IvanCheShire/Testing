package ru.geekbrains.testing.presenter

import ru.geekbrains.testing.view.ViewContract

internal interface PresenterContract {
    fun onAttach(viewContract: ViewContract)
    fun onDetach()
}