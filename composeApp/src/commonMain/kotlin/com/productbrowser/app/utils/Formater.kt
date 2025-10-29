package com.productbrowser.app.utils

import kotlin.math.round

fun Double.formatTwoDecimal(): String {
    return (round(this * 100) / 100).toString()
}
fun Double.formatOneDecimal(): String {
    return (round(this * 10) / 10).toString()
}