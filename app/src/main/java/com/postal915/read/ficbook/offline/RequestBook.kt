package com.postal915.read.ficbook.offline

import com.postal915.read.ficbook.offline.data.Author
import com.postal915.read.ficbook.offline.data.Book
import com.postal915.read.ficbook.offline.data.Chapter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class RequestBook(private val urlBook: String) {

    private fun getDocumentJsoup(url: String): Document {
        return Jsoup.connect(url).get()
    }

    fun getBook(): Book {
        val doc: Document = getDocumentJsoup(urlBook)

        val bookAuthor = getBookAuthor(doc)
        val bookName = getBookName(doc)
        val description = getDescription(doc)
        val bookSize = getBookSize(doc)
        val isManyChapters = bookSize[3]
        val chapter = getChapter(doc, isManyChapters as Boolean)

        return Book(
            bookAuthor,
            bookName,
            bookSize[0] as String,
            bookSize[1] as Int,
            bookSize[2] as Int,
            bookSize[3] as Boolean,
            description,
            chapter
        )
    }

    private fun getBookAuthor(doc: Document): Author {
        val authorNameElement = doc.select("a[itemprop=author]").text()
        val authorUrlHref = doc.select("a[itemprop=author]").attr("abs:href")
        return Author(authorNameElement, authorUrlHref)
    }

    private fun getBookName(doc: Document): String? {
        return doc.select("h1[class=mb-10]").text()
    }

    private fun getDescription(doc: Document): String? {
        return doc.select("div[itemprop=description]").text()
    }

    private fun getBookSize(doc: Document): Array<Any> {
        val sizeBookElements = doc.select("div[class=mb-5]").first().text()
        val bookSize = sizeBookElements.split(",")[0].split(" ").last()
        val pages = sizeBookElements.split(",")[1].replace("\\D".toRegex(), "").toInt()
        val chapters = sizeBookElements.split(",")[2].replace("\\D".toRegex(), "").toInt()
        val manyChaptersBoolean = isManyChapters(chapters)
        return arrayOf(bookSize, pages, chapters, manyChaptersBoolean)
    }

    private fun isManyChapters(chapters: Int): Boolean {
        return chapters != 1
    }

    private fun getChapter(doc: Document, chapterBoolean: Boolean): MutableList<Chapter> {
        return if (chapterBoolean) {
            getManyChapters(doc)
        } else {
            getOneChapterText(doc)
        }
    }

    private fun getOneChapterText(doc: Document): MutableList<Chapter> {
        var chaptersList: MutableList<Chapter> = ArrayList<Chapter>()
        val chapterText = doc.select("div[id=content]").text()
        val chapterCreationDate = doc.select("div[class=part-date]").select("span").text()
        val chapterTitle = doc.select("title").text().split(",").first()
        chaptersList.add(Chapter(chapterTitle, chapterCreationDate, urlBook, chapterText))
        return chaptersList
    }

    private fun getManyChapters(doc: Document): MutableList<Chapter> {
        var chaptersList: MutableList<Chapter> = ArrayList<Chapter>()
        val chaptersElements = doc.select("li[class=part]")
        chaptersElements.forEach {
            val chapterName = it.select("h3").text()
            val dateChapterCreate = it.getElementsByTag("span").attr("title")
            val absoluteHref = it.select("a").attr("abs:href").split("#").first()
            val chapterText = getHighlightedChapter(absoluteHref)
            val chapter = Chapter(chapterName, dateChapterCreate, absoluteHref, chapterText)
            chaptersList.add(chapter)
        }
        return chaptersList
    }

    private fun getHighlightedChapter(urlHighlightedChapter: String): String {
        val documentChapter: Document = getDocumentJsoup(urlHighlightedChapter)
        return documentChapter.select("div[id=content]").text()
    }
}