import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlin.test.Test
import kotlin.test.fail

class AppTest {
    @Test
    fun testAppHasAGreeting() {
        val classUnderTest = App()
        assertNotNull("app should have a greeting", classUnderTest.greeting)
    }

    @Test
    fun testConversionAmount() {
        val amountInUSD = 100.0
        when (val result = amountInUSD.convert(Currency.USD, Currency.EUR)) {
            is ConversionResult.Success -> {
                assertEquals(85.0, result.amount)
                assertEquals(Currency.EUR, result.currency)
            }
            else -> fail("Conversion failed")
        }
    }
}