import junit.framework.TestCase.assertNotNull
import kotlin.test.Test

class AppTest {
    @Test
    fun testAppHasAGreeting() {
        val classUnderTest = App()
        assertNotNull("app should have a greeting", classUnderTest.greeting)
    }
}