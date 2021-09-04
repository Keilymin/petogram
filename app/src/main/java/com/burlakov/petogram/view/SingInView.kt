package com.burlakov.petogram.view

import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SingInView : SingUpView {
    @StateStrategyType(SingleStateStrategy::class)
    fun goToMain()
}