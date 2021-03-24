package com.postal915.read.ficbook.offline

import com.postal915.read.ficbook.offline.data.Book

fun main() {

    val book: Book = RequestBook(urlsTestBook()[4]).getBook()

    // TODO написать тесты
    // TODO увиличить быстродействие при нескольких главах
    // TODO вызывает ошибку !!!!!! https://ficbook.net/readfic/6570064

    println(book.author.nameAuthor)
    println(book.author.urlAuthor)
    println(book.bookName)
    println(book.bookSize)
    println(book.pages)
    println(book.chapters)
    println(book.isManyChapters)
    println(book.description)
    println(book.chapter.first().chapterTitle)
//    println(book.chapter.first().chapterCreationDate)
//    println(book.chapter.first().chapterLink)
//    println(book.chapter.first().chapterText)

}

fun urlsTestBook(): Array<String> {
    return arrayOf(
        // книги на много глав
        "https://ficbook.net/readfic/9823853",
        "https://ficbook.net/readfic/8355744",
//        // 680 страниц, 91 часть
        "https://ficbook.net/readfic/7861843",
//        // 1211 страниц, 98 частей
        // TODO вызывает ошибку !!!!!!
        "https://ficbook.net/readfic/6570064",
        // книги на одну главу
        "https://ficbook.net/readfic/7533482"
//        "C:/Users/sts/Desktop/%D0%92%D1%82%D0%BE%D1%80%D0%B0%D1%8F%20%D0%B6%D0%B8%D0%B7%D0%BD%D1%8C%20%D0%90%D1%80%D1%82%D1%83%D1%80%D0%B0%20%E2%80%94%20%D0%BA%D0%BE%D0%BF%D0%B8%D1%8F.html"
    )
}