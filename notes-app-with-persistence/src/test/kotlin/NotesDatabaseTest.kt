import io.mockk.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.engine.test.status.isEnabled
import io.kotest.matchers.shouldBe
import kotlin.reflect.KClass

class NotesDatabaseTest : StringSpec({

    lateinit var testRepository: NoteRepository

    beforeTest {
        testRepository = NoteRepository(NotesDatabase())
        println("Starting tests")
    }

    "test framework should assert equal values" {
        1 shouldBe 1
    }

    "test if DB exists" {
        val db = NotesDatabase()

        db.checkConnection() shouldBe true
        db.checkTable() shouldBe true
    }

    "test add note" {
        val testText = "test note"
        testRepository.addNote(testText)
        val notes = testRepository.getAllNotes()
        notes.find { it.content == testText }!!.content shouldBe testText
    }

    "test find note by ID" {
        testRepository.addNote("test note")
        val notes = testRepository.getAllNotes()
        val note = testRepository.findNoteById(notes[0].id)
        note?.content shouldBe "test note"
    }

    "test update note" {
        val mockDatabase = mockk<NotesDatabase>()

        val note = Note(
            id = 1,
            content = "test note"
        )

        every { mockDatabase.addNoteToDatabase(any()) } just Runs
        every { mockDatabase.getAllNotesFromDatabase() } returns listOf(note)
        every { mockDatabase.updateNoteInDatabase(any(), any()) } just Runs
        every { mockDatabase.findNoteByIdFromDatabase(any()) } returns note.copy(content = "updated note")

        mockDatabase.addNoteToDatabase("test note")
        val notes = mockDatabase.getAllNotesFromDatabase()
        mockDatabase.updateNoteInDatabase(notes[0].id, "updated note")
        val updatedNote = mockDatabase.findNoteByIdFromDatabase(notes[0].id)
        updatedNote?.content shouldBe "updated note"

        verify { mockDatabase.addNoteToDatabase("test note") }
        verify { mockDatabase.getAllNotesFromDatabase() }
        verify { mockDatabase.updateNoteInDatabase(notes[0].id, "updated note") }
        verify { mockDatabase.findNoteByIdFromDatabase(notes[0].id) }
    }

    "test delete note" {
        val mockDatabase = mockk<NotesDatabase>()

        val note = Note(
            id = 1,
            content = "test note"
        )

        every { mockDatabase.addNoteToDatabase(any()) } just Runs
        every { mockDatabase.getAllNotesFromDatabase() } returns listOf(note)
        every { mockDatabase.updateNoteInDatabase(any(), any()) } just Runs
        every { mockDatabase.findNoteByIdFromDatabase(any()) } returns null

        mockDatabase.addNoteToDatabase("test note")
        val notes = mockDatabase.getAllNotesFromDatabase()
        mockDatabase.deleteNoteFromDatabase(notes[0].id)
        val deletedNote = mockDatabase.findNoteByIdFromDatabase(notes[0].id)
        deletedNote shouldBe null

        verify { mockDatabase.addNoteToDatabase("test note") }
        verify { mockDatabase.getAllNotesFromDatabase() }
        verify { mockDatabase.deleteNoteFromDatabase(notes[0].id) }
        verify { mockDatabase.findNoteByIdFromDatabase(notes[0].id) }
    }

    "integrate test delete note" {
        val existingNotes = testRepository.getAllNotes()
        val currentSize = existingNotes.size
        if (existingNotes.isNotEmpty()) {
            testRepository.deleteNote(existingNotes[0].id)
        }

        testRepository.getAllNotes().size shouldBe currentSize - 1
    }
})