package com.zum.contentprovidersample2

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns

interface WordsOfTodayContract {
    companion object{
        val AUTHORITY = "com.advanced_android.wordoftoday2"
        val TABLE_NAME = "WordsOfToday"
        var CONTENT_URI =
            Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + TABLE_NAME)
        val MIME_TYPE_DIR = "vnd.android.cursor.dir/$AUTHORITY.$TABLE_NAME"
        val MIME_TYPE_ITEM = "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
    }

    interface WordsOfTodayColumns : BaseColumns {
        companion object {
            const val NAME = "name"
            const val WORDS = "words"
            const val DATE = "date"
        }
    }
}