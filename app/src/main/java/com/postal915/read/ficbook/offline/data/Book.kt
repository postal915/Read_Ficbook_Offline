package com.postal915.read.ficbook.offline.data

data class Book(
    val author: Author,
    val bookName: String?,
    val bookSize: String,
    val pages: Int,
    val chapters: Int,
    val isManyChapters: Boolean,
    val description: String?,
    val chapter: MutableList<Chapter>
)