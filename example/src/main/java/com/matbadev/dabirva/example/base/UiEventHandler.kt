package com.matbadev.dabirva.example.base

interface UiEventHandler<E> {

    fun handleCommonUiEvent(event: CommonUiEvent)

    fun handleScreenUiEvent(event: E)

}
