package com.matbadev.dabirva

object DabirvaConfig {

    @JvmStatic
    var factory: DabirvaFactory = DabirvaFactory(::Dabirva)

}
