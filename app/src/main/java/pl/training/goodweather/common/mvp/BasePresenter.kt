package pl.training.goodweather.common.mvp

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T: MvpView> : MvpPresenter<T> {

    protected val disposableBag = CompositeDisposable()

    protected var view: T? = null

    @CallSuper
    override fun attachView(view: T) {
        this.view = view
        onViewAttached()
    }

    @CallSuper
    override fun detachView() {
        disposableBag.clear()
        view = null
    }

    open fun onViewAttached() {
    }

}