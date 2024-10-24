package com.example.plugins

import com.example.model.Priority
import com.example.model.Task
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/tasks") {
            call.respond(
                listOf(
                    Task("cleaning", "Clean The House", Priority.LOW),
                    Task("shopping", "Buy Groceries", Priority.MEDIUM),
                    Task("studying", "Study for the exam", Priority.HIGH),
                    Task("sleeping", "Take a nap", Priority.VITAL)
                )
            )
        }
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}
