package com.burlakov.petogram.presenter

import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.model.User
import com.burlakov.petogram.utils.LocaleException
import com.burlakov.petogram.view.SingInView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class LogInPresenter : MvpPresenter<SingInView>() {
    val handler = LocaleException(viewState).handler

    fun logIn(email: String, password: String) = CoroutineScope(Dispatchers.Main).launch(handler) {

        val answer = PetogramApplication.userService.logIn(User(email, password))

        if (!answer.isPositive) {
            viewState.showMessage(
                answer.message, answer.isPositive
            )
        } else {
            viewState.goToMain()
        }
        viewState.enableButton(true)

    }
}