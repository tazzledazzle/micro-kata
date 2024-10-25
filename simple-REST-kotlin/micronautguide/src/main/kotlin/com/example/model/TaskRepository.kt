package com.example.model
object TaskRepository {
    private val tasks = mutableListOf(
        Task(8881, "cleaning", "Clean The House", Priority.LOW),
        Task(8882, "shopping", "Buy Groceries", Priority.MEDIUM),
        Task(8883, "studying", "Study for the exam", Priority.HIGH),
        Task(8884, "sleeping", "Take a nap", Priority.VITAL)
    )

    fun allTasks(): List<Task> = tasks

    fun tasksByPriority(priority: Priority) = tasks.filter { it.priority == priority }

    fun taskByName(name: String) = tasks.find {
        it.name.equals(name, ignoreCase = true)
    }

    fun addTask(task: Task) {
        if (taskByName(task.name) != null) {
            throw IllegalStateException("Cannot add task with name ${task.name} because it already exists")
        }
        tasks.add(task)
    }

    fun removeTask(name: String) : Boolean {
        return tasks.removeIf { it.name == name }
    }
}