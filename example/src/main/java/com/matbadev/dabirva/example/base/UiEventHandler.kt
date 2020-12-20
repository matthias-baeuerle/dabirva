package com.matbadev.dabirva.example.base

interface UiEventHandler<SE> {

    fun handleCommonUiEvent(event: CommonUiEvent)

    fun handleScreenUiEvent(event: SE)

}
