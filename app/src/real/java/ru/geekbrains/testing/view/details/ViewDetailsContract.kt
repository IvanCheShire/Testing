package ru.geekbrains.testing.view.details

import ru.geekbrains.testing.view.ViewContract

internal interface ViewDetailsContract : ViewContract {
    fun setCount(count: Int)
}
