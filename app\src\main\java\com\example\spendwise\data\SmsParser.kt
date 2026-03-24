package com.example.spendwise.data

import java.util.Date

object SmsParser {
    private val amountRegex = Regex("(?i)(?:rs|inr|\$|usd|amount)[^0-9\\-]*([0-9]{1,3}(?:[,\\s][0-9]{3})*(?:\\.[0-9]{1,2})?)")
    private val creditWords = listOf("credited", "deposit", "cr")
    private val debitWords = listOf("debited", "withdrawn", "payment", "pmt", "dr")

    fun parse(text: String): Transaction {
        val now = Date().time
        val amtMatch = amountRegex.find(text)
        val amount = amtMatch?.groups?.get(1)?.value?.replace(",", "")?.toDoubleOrNull() ?: 0.0
        val type = when {
            creditWords.any { text.contains(it, ignoreCase = true) } -> "credit"
            debitWords.any { text.contains(it, ignoreCase = true) } -> "debit"
            amount < 0 -> "debit"
            else -> "unknown"
        }
        val merchant = extractMerchant(text)
        return Transaction(0, now, amount, type, merchant, text)
    }

    private fun extractMerchant(text: String): String? {
        // Simple heuristic: look for 'at <merchant>' or 'to <merchant>'
        val atMatch = Regex("(?:at|to)\\s+([A-Za-z0-9 &\\-_.]+)", RegexOption.IGNORE_CASE).find(text)
        return atMatch?.groups?.get(1)?.value?.trim()?.take(60)
    }
}
