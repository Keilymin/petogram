package com.burlakov.petogram.presenter


import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.model.Answer
import com.burlakov.petogram.model.User
import com.burlakov.petogram.utils.LocaleException
import com.burlakov.petogram.view.SingUpView
import kotlinx.coroutines.*


class SingUpPresenter(val viewState: SingUpView) {
    val handler = LocaleException(viewState).handler

    fun singUp(email: String, password: String) = CoroutineScope(Dispatchers.Main).launch(handler) {
        val answer: Answer = PetogramApplication.userService.singUp(User(email, password))
        viewState.showMessage(
            answer.message, answer.isPositive
        )
        viewState.enableButton(true)

    }
}