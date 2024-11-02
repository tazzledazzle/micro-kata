package org.example.app

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*
import java.io.File

private fun FlowContent.videoPlayer(video: String) {
    video {
        source {
            src = "/video/$video"
            type = "video/mp4"
        }
        controls = true
    }
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
                    h1 { +"YouKube" }
                    form(action = "/upload", encType = FormEncType.multipartFormData, method = FormMethod.post) {
                        fileInput(name = "video") { accept = "video/*" }
                        submitInput { value = "Upload" }
                    }
                    h2 { +"Videos" }
                    videos.forEach { video ->
                        videoPlayer(video)
                    }
                }
            }
        }
    }
}