package com.example.model

import kotlinx.serialization.Serializable

enum class Priority(val priority: String) {
    LOW ("Low"), MEDIUM("Medium"), HIGH("High"), VITAL("Vital")
}


@Serializable
data class Task(
    val id: Int,
    val name: String,
    val description: String,
    val priority: Priority,
)

