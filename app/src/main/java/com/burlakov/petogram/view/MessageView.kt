package com.burlakov.petogram.view

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType


interface MessageView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showMessage(message: String, isPositive: Boolean)
}