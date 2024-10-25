package com.example

import com.example.model.Task
import com.example.plugins.configureRouting
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import java.lang.reflect.TypeVariable
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ApplicationTest {

//    @Test
//    fun testPostCustomer() = testApplication {
//            install(ContentNegotiation) {
//                json()
//            }
//        }


@Ignore("This test is not working, the classpath doesn't recognize the testApplication function")
fun tasksCanBeFoundByPriority() = testApplication {
    application {
        configureRouting()
    }
    client.get("/tasks/byPriority/HIGH").apply {

        val gson = Gson()
        val taskListType = object : TypeToken<List<Task>>() {}.type
        val tasks: List<Task> = gson.fromJson(body() ?: "[]", taskListType)
        println(tasks)
        val expectedTaskNames = listOf("studying")
        val actualTaskNames = tasks.map { it.name }
        assertContentEquals(expectedTaskNames, actualTaskNames)
        println(bodyAsText())
        assertEquals(tasks.size, 1)
        assertEquals(HttpStatusCode.OK, status)


    }
}

    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
