package com.burlakov.petogram.presenter



import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.utils.LocaleException
import com.burlakov.petogram.view.SingUpView
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class SingUpPresenter : MvpPresenter<SingUpView>() {
    val handler = LocaleException(viewState).handler

    fun singUp(email: String, password: String) = CoroutineScope(Dispatchers.Main).launch(handler) {
        viewState.showMessage(PetogramApplication.singUpService.singUp().password, true)
        viewState.enableSingUpButton(true)

    }
}