package com.civil.shield

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform