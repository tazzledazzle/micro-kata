package com.example

import com.example.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String> = emptyArray()) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
