package com.Dasha.redmaterna

data class ForumPost(
    val title: String = "",
    val content: String = "",
    val name: String = "",
    val timestamp: Long = System.currentTimeMillis()
)