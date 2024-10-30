import io.mockk.*
import org.junit.Test
import kotlin.test.assertEquals


class NotesDatabaseTest {

    @Test
    fun testFramework() {
        assertEquals(1, 1)
    }

    @Test
    fun testIfDBExists() {
        val db = NotesDatabase()

        assertEquals(true, db.checkConnection())

        // check table
        assertEquals(true, db.checkTable())
    }


    @Test
    fun testAddNote() {
        val db = NotesDatabase()
        val testText = "test note"
        db.addNote(testText)
        val notes = db.getAllNotes()
        assertEquals(testText, notes.find { it.content == testText }!!.content)
    }

    @Test
    fun testFindNoteById() {
        val db = NotesDatabase()
        db.addNote("test note")
        val notes = db.getAllNotes()
        val note = db.findNoteById(notes[0].id)
        assertEquals("test note", note?.content)
    }

    @Test
    fun testUpdateNote() {
        val db = mockk<NotesDatabase>()
        val note = Note(
            id = 1,
            content = "test note"
        )

        every { db.addNote(any()) } just Runs
        every { db.getAllNotes() } returns listOf(note)
        every { db.updateNote(any(), any()) } just Runs
        every { db.findNoteById(any()) } returns note.copy(content = "updated note")

        db.addNote("test note")
        val notes = db.getAllNotes()
        db.updateNote(notes[0].id, "updated note")
        val updatedNote = db.findNoteById(notes[0].id)
        assertEquals("updated note", updatedNote?.content)

        verify { db.addNote("test note") }
        verify { db.getAllNotes() }
        verify { db.updateNote(notes[0].id, "updated note") }
        verify { db.findNoteById(notes[0].id) }
    }

    @Test
    fun testDeleteNote() {
        val db = mockk<NotesDatabase>()
        val note = Note(
            id = 1,
            content = "test note"
        )

        every { db.addNote(any()) } just Runs
        every { db.getAllNotes() } returns listOf(note)
        every { db.deleteNote(any()) } just Runs
        every { db.findNoteById(any()) } returns null

        db.addNote("test note")
        val notes = db.getAllNotes()
        db.deleteNote(notes[0].id)
        val deletedNote = db.findNoteById(notes[0].id)
        assertEquals(null, deletedNote)

        verify { db.addNote("test note") }
        verify { db.getAllNotes() }
        verify { db.deleteNote(notes[0].id) }
        verify { db.findNoteById(notes[0].id) }
    }

    @Test
    fun integrateTestDeleteNote() {
        val db = NotesDatabase()
        val exitingNotes = db.getAllNotes()
        val currentSize = exitingNotes.size
        if (exitingNotes.isNotEmpty()) {
            db.deleteNote(exitingNotes[0].id)
        }

        assertEquals(currentSize - 1, db.getAllNotes().size)

    }
}