//class NotesRepository(private val db: NotesDatabase) {
//    fun addNote(content: String) {
//        db.addNote(content)
//    }
//
//    fun findNoteById(id: Int): Note? {
//        return db.findNoteById(id)
//    }
//
//    fun deleteNoteById(id: Int) {
//        db.deleteNote(id)
//    }
//
//    fun updateNoteById(id: Int, content: String) {
//        db.updateNote(id, content)
//    }
//
//    fun getAllNotes(): List<Note> {
//        return db.getAllNotes()
//    }
//}

class NoteRepository(private val db: NotesDatabase) {

    fun addNote(content: String) {
        db.addNoteToDatabase(content)
    }

    fun findNoteById(id: Int): Note? {
        return db.findNoteByIdFromDatabase(id)
    }

    fun deleteNote(id: Int) {
        db.deleteNoteFromDatabase(id)
    }

    fun updateNote(id: Int, content: String) {
        db.updateNoteInDatabase(id, content)
    }

    fun getAllNotes(): List<Note> {
        val resultSet = db.getAllNotesFromDatabase()
        val notes = mutableListOf<Note>()
        while (resultSet.next()) {
            notes.add(Note(
                resultSet.getInt("id"),
                resultSet.getString("content")
            ))
        }
        return notes
    }
}