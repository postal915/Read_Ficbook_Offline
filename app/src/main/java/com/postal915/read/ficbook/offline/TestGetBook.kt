package com.postal915.read.ficbook.offline

import com.postal915.read.ficbook.offline.data.Book
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun main() {
//    testBookProject()
    testJsoupConfiguration()
//    test()
}

private fun test(){
    val url = urlsTestBook()[1]



    val getDocumentJsoup = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36")
        .ignoreHttpErrors(true)
        .timeout(10000)
        .get()
//    val sizeBookElements = getDocumentJsoup.select("div[class=mb-5]").first().text()
    val sizeBookElements = getDocumentJsoup.select("div[class=mb-5]").text()
    print(getDocumentJsoup)
}

//private fun getBookSize(doc: Document): Array<Any> {
//    val sizeBookElements = doc.select("div[class=mb-5]").first().text()
//    val bookSize = sizeBookElements.split(",")[0].split(" ").last()
//    val pages = sizeBookElements.split(",")[1].replace("\\D".toRegex(), "").toInt()
//    val chapters = sizeBookElements.split(",")[2].replace("\\D".toRegex(), "").toInt()
//    val manyChaptersBoolean = isManyChapters(chapters)
//    return arrayOf(bookSize, pages, chapters, manyChaptersBoolean)
//}

private fun testJsoupConfiguration() {
    val url = "https://ficbook.net";

//    val response: Connection.Response = Jsoup.connect(url)
//        .userAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5")
//        .timeout(10000)
//        .followRedirects(false)
//        .ignoreHttpErrors(true) // <--- Underlined red in eclipse plus the error msg
//        .execute()
//    val cookies = response.cookies()
//    println(cookies)
//    println(response.statusCode())


    val getDocumentJsoup = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5")
        .cookie("__cfduid", "d638e8fcea810971ad469329bab139ab71619300951")
        .ignoreHttpErrors(true)
        .get()

    println(getDocumentJsoup)
}

fun testBookProject() {
//    val book: Book = RequestBook(urlsTestBook()[4]).getBook()
    val book: Book = RequestBook(urlsTestBook()[1]).getBook()

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