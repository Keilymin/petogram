package com.burlakov.petogram.view


import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType


interface SingUpView : MvpView, MessageView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun enableSingUpButton(isPositive: Boolean)
}