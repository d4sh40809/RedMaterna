package com.Dasha.redmaterna

data class JournalEntry(
    val mood: String,
    val content: String,
    val tag: String,
    val timestamp: Long = System.currentTimeMillis()
)
