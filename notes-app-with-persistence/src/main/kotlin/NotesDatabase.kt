import java.sql.*

class NotesDatabase {
    private val connection: Connection

    init {
        connection = DriverManager.getConnection("jdbc:sqlite:notes.db")
        createTable()
    }

    private fun createTable() {
        connection.createStatement()
            .execute(""" CREATE TABLE IF NOT EXISTS notes (
                id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT )""")
    }

    fun checkConnection(): Boolean {
        return connection.isValid(5)
    }

    fun addNoteToDatabase(content: String) {
        val stmt = connection.prepareStatement("INSERT INTO notes (content) VALUES (?)")
        stmt.setString(1, content)
        stmt.executeUpdate()
        stmt.close()
    }

    fun findNoteByIdFromDatabase(id: Int): Note? {
        val stmt = connection.prepareStatement("SELECT * FROM notes WHERE id = ?")
        stmt.setInt(1, id)
        val resultSet = stmt.executeQuery()
        if (resultSet.next()) {
            return Note(
                resultSet.getInt("id"),
                resultSet.getString("content")
            )
        }
        return null
    }

    fun deleteNoteFromDatabase(id: Int) {
        val stmt = connection.prepareStatement("DELETE FROM notes WHERE id = ?")
        stmt.setInt(1, id)
        stmt.executeUpdate()
        stmt.close()
        println("Note '${id}' deleted")
    }

    fun updateNoteInDatabase(id: Int, content: String) {
        val stmt = connection.prepareStatement("UPDATE notes SET content = ? WHERE id = ?")
        stmt.setString(1, content)
        stmt.setInt(2, id)
        stmt.executeUpdate()
        stmt.close()
        println("Note '${id}' updated")
    }

    fun getAllNotesFromDatabase(): List<Note> {
        val stmt = connection.createStatement()
        val resultSet = stmt.executeQuery("SELECT * FROM notes")
        val notes = mutableListOf<Note>()
        while(resultSet.next()) {
            notes.add(Note(
                resultSet.getInt("id"),
                resultSet.getString("content")
            ))
        }
        stmt.close()
        return notes
    }

    fun checkTable(): Boolean {
        val stmt = connection.createStatement()
        if (stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='notes'").next()) {
            stmt.close()
            return true
        }
        return false
    }
}

