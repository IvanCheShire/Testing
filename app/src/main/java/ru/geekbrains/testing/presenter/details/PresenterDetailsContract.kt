package ru.geekbrains.testing.presenter.details

import ru.geekbrains.testing.presenter.PresenterContract

internal interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}
