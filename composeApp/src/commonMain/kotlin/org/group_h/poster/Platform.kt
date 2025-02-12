package org.group_h.poster

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform