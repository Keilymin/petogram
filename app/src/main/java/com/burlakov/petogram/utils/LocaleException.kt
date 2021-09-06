package com.burlakov.petogram.utils

import com.burlakov.petogram.view.MessageView
import com.burlakov.petogram.view.SingUpView
import kotlinx.coroutines.CoroutineExceptionHandler

//For server and coroutines error
class LocaleException(viewState: MessageView) {
    val handler = CoroutineExceptionHandler { _, exception ->
        val message = "Error: "
        viewState.showMessage("$message ${exception.localizedMessage}", false)

        if (viewState is SingUpView){
            viewState.enableButton(true)
        }
    }
}