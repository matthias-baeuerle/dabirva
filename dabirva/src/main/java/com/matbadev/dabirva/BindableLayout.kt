package com.matbadev.dabirva

/**
 * Contains everything necessary to bind a layout to a specific object using data binding.
 */
interface BindableLayout {

    /**
     * ID of the layout stored in the generated R-class.
     */
    val layoutId: Int

    /**
     * The binding ID stored in the generated BR-class
     */
    val bindingId: Int

}
