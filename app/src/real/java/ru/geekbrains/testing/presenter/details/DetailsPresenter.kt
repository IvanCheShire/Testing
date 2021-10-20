package ru.geekbrains.testing.presenter.details

import androidx.annotation.VisibleForTesting
import ru.geekbrains.testing.view.ViewContract
import ru.geekbrains.testing.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    private var viewContract: ViewDetailsContract? = null,
    private var count: Int = 0
) : PresenterDetailsContract {

    @VisibleForTesting
    fun getViewContract(): ViewContract? { return viewContract }


    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract?.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract?.setCount(count)
    }

    override fun onAttach(viewContract: ViewContract) {
        this.viewContract = viewContract as ViewDetailsContract
        print("DetailsPresenter onAttach()")
    }

    override fun onDetach() {
        viewContract = null
        print("DetailsPresenter onDetach()")
    }
}
