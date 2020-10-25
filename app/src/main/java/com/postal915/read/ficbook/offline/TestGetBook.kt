package com.postal915.read.ficbook.offline

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun main() {
    getBook(urlsTestBook()[1])
}

fun urlsTestBook(): Array<String> {
    return arrayOf(
        // книги на много глав
        "https://ficbook.net/readfic/8355744",
        "https://ficbook.net/readfic/9823853",
        // книги на одну главу
        "https://ficbook.net/readfic/7533482"
    )
}

fun getBook(url: String) {
    val doc: Document = Jsoup.connect(url).get()

    getAuthor(doc)
    getBookName(doc)
    getDescription(doc)
    getBookSize(doc)
}

fun getAuthor(doc: Document): String? {
    // получения автора книги
    val authorElement = doc.select("a[itemprop=author]").text()
    println("author = $authorElement")
    return authorElement
}

fun getBookName(doc: Document): String? {
    // получение названия книги
    val bookNameElement = doc.select("h1[class=mb-10]").text()
    println("bookName = $bookNameElement")
    return bookNameElement
}

fun getDescription(doc: Document): String? {
    // получение описания книги
    val descriptionElement = doc.select("div[itemprop=description]").text()
    println("description = $descriptionElement")
    return descriptionElement
}

fun getBookSize(doc: Document): Array<Any> {
    // получения размера книги например Макси или Мини, колличество частей, колличества страниц
    val sizeBookElements = doc.select("div[class=mb-5]").first().text()
    val bookSize = sizeBookElements.split(",")[0].split(" ").last()
    val pages = sizeBookElements.split(",")[1].replace("\\D".toRegex(), "").toInt()
    val chapters = sizeBookElements.split(",")[2].replace("\\D".toRegex(), "").toInt()
    val manyChaptersBoolean = isManyChapters(chapters)
    println("bookSize = $bookSize")
    println("pages = $pages")
    println("chapters = $chapters")
    println("manyChapters = $manyChaptersBoolean")

    return arrayOf(bookSize, pages, chapters, manyChaptersBoolean)
}

fun isManyChapters(chapters: Int): Boolean {
    // проверка на колличество глав
    // если false то одна глва если true то больше одной
    return if (chapters == 1) {
        println("Только одна глава")
        false
    } else {
        println("Больше чем одна глава")
        true
    }
}

fun getOneChapterText(doc: Document): String? {
    // получение только одной главы
    val chapterTextElement = doc.select("div[id=content]").text()
    println("chapterText = $chapterTextElement")
    return chapterTextElement
}

