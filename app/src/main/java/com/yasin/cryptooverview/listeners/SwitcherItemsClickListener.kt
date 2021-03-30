package com.yasin.cryptooverview.listeners

//interface SwitcherItemsClickListener {
//    fun on1hClick()
//    fun on2hClick()
//    fun on4hClick()
//    fun on8hClick()
//    fun on1dClick()
//    fun on1wClick()
//}

class SwitcherItemsClickListener(
    private val clickOn1h: () -> Unit,
    private val clickOn2h: () -> Unit,
    private val clickOn4h: () -> Unit,
    private val clickOn8h: () -> Unit,
    private val clickOn1d: () -> Unit,
    private val clickOn1w: () -> Unit
) {
    fun on1hClick() = clickOn1h()

    fun on2hClick() = clickOn2h()

    fun on4hClick() = clickOn4h()

    fun on8hClick() = clickOn8h()

    fun on1dClick() = clickOn1d()

    fun on1wClick() = clickOn1w()

}