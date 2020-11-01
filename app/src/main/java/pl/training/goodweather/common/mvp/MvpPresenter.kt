package pl.training.goodweather.common.mvp

interface MvpPresenter<V : MvpView> {

    fun attachView(view: V) = Unit

    fun detachView() = Unit

}