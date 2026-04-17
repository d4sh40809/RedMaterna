package com.Dasha.redmaterna

data class SymptomEntry(
    val content: String,
    val type: String,
    val timestamp: Long = System.currentTimeMillis()
)
