enum class Currency(val symbol: String) {
    USD("US Dollar"),
    EUR("Euro"),
    GBP("British Pound")
}

sealed class ConversionResult {
    data class Success(val amount: Double, val currency: Currency) : ConversionResult()
    data class Error(val message: String) : ConversionResult()
    object Pending : ConversionResult()
}


fun Double.convert(from: Currency, to: Currency): ConversionResult {
    return when (from) {
        Currency.USD -> {
            when (to) {
                Currency.USD -> ConversionResult.Success(this, Currency.USD)
                Currency.EUR -> ConversionResult.Success(this * 0.85, Currency.EUR)
                Currency.GBP -> ConversionResult.Success(this * 0.72, Currency.GBP)
            }
        }
        Currency.EUR -> {
            when (to) {
                Currency.USD -> ConversionResult.Success(this * 1.18, Currency.USD)
                Currency.EUR -> ConversionResult.Success(this, Currency.EUR)
                Currency.GBP -> ConversionResult.Success(this * 0.85, Currency.GBP)
            }
        }
        Currency.GBP -> {
            when (to) {
                Currency.USD -> ConversionResult.Success(this * 1.39, Currency.USD)
                Currency.EUR -> ConversionResult.Success(this * 1.18, Currency.EUR)
                Currency.GBP -> ConversionResult.Success(this, Currency.GBP)
            }
        }
    }
}