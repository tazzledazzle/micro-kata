package org.example.app

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
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
        post("/upload") {
            val multipart = call.receiveMultipart()
            multipart.forEachPart { part ->
                if (part is PartData.FileItem) {
                    val file = File("uploads/${part.originalFileName}")
                    part.streamProvider().use { its ->
                        file.outputStream().buffered().use { its.copyTo(it) }
                    }
                }
                part.dispose()
            }
            call.respondRedirect("/")
        }
    }
}