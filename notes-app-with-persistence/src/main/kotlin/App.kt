
fun main() {
    val db = NotesDatabase()
    db.addNote("Learn Kotlin")
    db.addNote("Practice coding")
    val notes = db.getAllNotes()
    notes.forEach { println(it) }
}