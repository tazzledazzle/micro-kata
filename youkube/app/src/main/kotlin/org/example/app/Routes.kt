package org.example.app

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*
import java.io.File

class Routes {
}

fun Application.module() {
    routing {
        get("/") {
            val videos = File("uploads").listFiles()?.map { it.name } ?: emptyList()
            call.respondHtml {
                head {
                    title { +"YouKube" }
                }
                body {
                    h1 { +"Welcome to YouKube" }
                    p { +"Upload and view videos easily" }
                }
            }
        }
    }
}