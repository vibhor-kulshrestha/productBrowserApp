package com.productbrowser.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform