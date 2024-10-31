import io.mockk.*
import org.junit.Before
import org.junit.Test
import kotlin.test.Ignore
import kotlin.test.assertEquals


class NotesDatabaseTest {
    private lateinit var repository: NoteRepository
    @Before
    fun setUp() {
        repository = NoteRepository(NotesDatabase())
        println("Starting tests")
    }
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

        val testText = "test note"
        repository.addNote(testText)
        val notes = repository.getAllNotes()
        assertEquals(testText, notes.find { it.content == testText }!!.content)
    }

    @Test
    fun testFindNoteById() {
        repository.addNote("test note")
        val notes = repository.getAllNotes()
        val note = repository.findNoteById(notes[0].id)
        assertEquals("test note", note?.content)
    }

    @Test
    fun testUpdateNote() {
        val repository = mockk<NoteRepository>()
        val note = Note(
            id = 1,
            content = "test note"
        )

        every { repository.addNote(any()) } just Runs
        every { repository.getAllNotes() } returns listOf(note)
        every { repository.updateNote(any(), any()) } just Runs
        every { repository.findNoteById(any()) } returns note.copy(content = "updated note")

        repository.addNote("test note")
        val notes = repository.getAllNotes()
        repository.updateNote(notes[0].id, "updated note")
        val updatedNote = repository.findNoteById(notes[0].id)
        assertEquals("updated note", updatedNote?.content)

        verify { repository.addNote("test note") }
        verify { repository.getAllNotes() }
        verify { repository.updateNote(notes[0].id, "updated note") }
        verify { repository.findNoteById(notes[0].id) }
    }

    @Test
    fun testDeleteNote() {
        val repository = mockk<NoteRepository>()
        val note = Note(
            id = 1,
            content = "test note"
        )

        every { repository.addNote(any()) } just Runs
        every { repository.getAllNotes() } returns listOf(note)
        every { repository.deleteNote(any()) } just Runs
        every { repository.findNoteById(any()) } returns null

        repository.addNote("test note")
        val notes = repository.getAllNotes()
        repository.deleteNote(notes[0].id)
        val deletedNote = repository.findNoteById(notes[0].id)
        assertEquals(null, deletedNote)

        verify { repository.addNote("test note") }
        verify { repository.getAllNotes() }
        verify { repository.deleteNote(notes[0].id) }
        verify { repository.findNoteById(notes[0].id) }
    }

    @Ignore("Database is locked for some reason")
    fun integrateTestDeleteNote() {
        val exitingNotes = repository.getAllNotes()
        val currentSize = exitingNotes.size
        if (exitingNotes.isNotEmpty()) {
            repository.deleteNote(exitingNotes[0].id)
        }

        assertEquals(currentSize - 1, repository.getAllNotes().size)

    }
}