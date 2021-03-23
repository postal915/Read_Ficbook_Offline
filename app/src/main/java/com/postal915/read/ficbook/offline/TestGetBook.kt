package com.postal915.read.ficbook.offline

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun main() {
    getBook(urlsTestBook()[0])
}

fun urlsTestBook(): Array<String> {
    return arrayOf(
        // книги на много глав
        "https://ficbook.net/readfic/9823853"
//        ,"https://ficbook.net/readfic/8355744",
//        // 680 страниц, 91 часть
//        "https://ficbook.net/readfic/7861843",
//        // 1211 страниц, 98 частей
//        "https://ficbook.net/readfic/6570064",
//        // книги на одну главу
//        "https://ficbook.net/readfic/7533482",
//        "C:/Users/sts/Desktop/%D0%92%D1%82%D0%BE%D1%80%D0%B0%D1%8F%20%D0%B6%D0%B8%D0%B7%D0%BD%D1%8C%20%D0%90%D1%80%D1%82%D1%83%D1%80%D0%B0%20%E2%80%94%20%D0%BA%D0%BE%D0%BF%D0%B8%D1%8F.html"
    )
}

fun getBook(url: String) {
    val doc: Document = Jsoup.connect(url).get()

//    val (nameAuthor, urlAuthor) = getAuthor(doc)
    val author = getAuthor(doc)
    println(author)
    getBookName(doc)
    getDescription(doc)
    val bookSize = getBookSize(doc)
    val isManyChapters = bookSize[3]
    getChapter(doc, isManyChapters as Boolean)
}

fun getAuthor(doc: Document): Author {
    // получения автора книги и ссылки на профиль автора
    val authorNameElement = doc.select("a[itemprop=author]").text()
    val authorUrlHref = doc.select("a[itemprop=author]").attr("abs:href")
    return Author(authorNameElement, authorUrlHref)
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

fun getChapter(doc: Document, chapterBoolean: Boolean) {
    if (chapterBoolean) {
        getManyChapters(doc)
    } else {
        getOneChapterText(doc)
    }
}

fun getOneChapterText(doc: Document): String? {
    // получение только одной главы
    val chapterTextElement = doc.select("div[id=content]").text()
    println("chapterText = $chapterTextElement")
    return chapterTextElement
}

fun getManyChapters(doc: Document) {

//    val chapt = doc.select("ul[class=list-unstyled list-of-fanfic-parts clearfix]")
    val chaptersElements = doc.select("li[class=part]")
//    println(chaptersElements.first())
    chaptersElements.forEach {
//        println("it = $it")
        val chapterName = it.select("h3").text()
        val dateChapterCreate = it.getElementsByTag("span").attr("title")
        val absoluteHref = it.select("a").attr("abs:href").split("#").first()
        println("chapterName = $chapterName")
        println("dateChapterCreate = $dateChapterCreate")
        println("absoluteHref = $absoluteHref")

    }
    // название главы
    // юрл
    // дату
//    println(select)

}

