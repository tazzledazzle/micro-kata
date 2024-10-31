
fun main() {
    val repository = NoteRepository(NotesDatabase())
    repository.addNote("Learn Kotlin")
    repository.addNote("Practice coding")
    val notes = repository.getAllNotes()
    notes.forEach { println(it) }
}