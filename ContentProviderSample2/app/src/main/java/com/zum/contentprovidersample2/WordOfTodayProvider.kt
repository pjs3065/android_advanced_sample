package com.zum.contentprovidersample2

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.net.Uri
import android.provider.BaseColumns._ID
import android.util.Log
import com.zum.contentprovidersample2.WordsOfTodayContract.Companion.AUTHORITY
import com.zum.contentprovidersample2.WordsOfTodayContract.Companion.MIME_TYPE_DIR
import com.zum.contentprovidersample2.WordsOfTodayContract.Companion.MIME_TYPE_ITEM
import com.zum.contentprovidersample2.WordsOfTodayContract.Companion.TABLE_NAME
import com.zum.contentprovidersample2.WordsOfTodayDbHelper.Companion.DB_NAME
import com.zum.contentprovidersample2.WordsOfTodayDbHelper.Companion.DB_VERSION

class WordOfTodayProvider : ContentProvider() {

    companion object {
        private val TAG = WordsOfToday::class.java.simpleName

        private val ROW_DIR = 1
        private val ROW_ITEM = 2

        private val sLastId = 0

        private val sUriMatcher by lazy {
            UriMatcher(UriMatcher.NO_MATCH).apply {
                addURI(AUTHORITY, TABLE_NAME, ROW_DIR)
                addURI(AUTHORITY, "$TABLE_NAME/#", ROW_ITEM)
            }
        }
    }

    private lateinit var mDbHelper: WordsOfTodayDbHelper

    /**
     * 초기화 처리
     */
    override fun onCreate(): Boolean {
        Log.d(TAG, "WordsOfTodayProvider onCreate")

        mDbHelper = WordsOfTodayDbHelper(context!!, DB_NAME, null, DB_VERSION,
            DatabaseErrorHandler { dbObj ->
                Log.d(TAG, "onCorruption")
                val path = dbObj.path
                context!!.deleteFile(path)
            })
        return true
    }

    /**
     * 인수로 전달된 URI에 대응하는 MIME 타입을 반환
     */
    override fun getType(uri: Uri): String? {
        return when (sUriMatcher.match(uri)) {
            ROW_DIR -> MIME_TYPE_DIR

            ROW_ITEM -> MIME_TYPE_ITEM

            else -> null
        }
    }

    /**
     * 레코드 추가
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val resultUri: Uri
        when (sUriMatcher.match(uri)) {
            ROW_DIR -> {
                synchronized(mDbHelper) {
                    val db = mDbHelper.writableDatabase
                    val lastId = db.insert(TABLE_NAME, null, values)

                    resultUri = ContentUris.withAppendedId(uri, lastId)
                    Log.d(TAG, "WordsOfTodayProvider insert $values")

                    //변경통지
                    context!!.contentResolver.notifyChange(resultUri, null)
                }
            }

            else -> {
                throw IllegalAccessException("인수의 URI가 틀렸습니다.")
            }
        }
        return resultUri
    }

    /**
     * 레코드 검색, 취득
     */
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var cursor: Cursor
        when (sUriMatcher.match(uri)) {
            ROW_DIR -> {
                Log.d(TAG, "query(dir) uri=$uri")
                synchronized(mDbHelper) {
                    val db = mDbHelper.writableDatabase
                    cursor = db.query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                    )
                }
            }

            ROW_ITEM -> {
                Log.d(TAG, "query(item) uri=$uri")
                synchronized(mDbHelper) {
                    val id = ContentUris.parseId(uri)
                    val db = mDbHelper.writableDatabase
                    cursor = db.query(
                        TABLE_NAME,
                        projection,
                        _ID,
                        arrayOf(id.toString()),
                        null,
                        null,
                        null
                    )
                }
            }

            else -> {
                throw IllegalAccessException("인수의 URI가 틀렸습니다.")
            }
        }

        return cursor
    }

    /**
     * 레코드 갱신
     */
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        var count = 0

        when (sUriMatcher.match(uri)) {
            ROW_ITEM -> {
                Log.d(TAG, "update(item) value $values")
                val id = ContentUris.parseId(uri).toInt()

                synchronized(mDbHelper) {
                    val db = mDbHelper.writableDatabase
                    count = db.update(TABLE_NAME, values, "$_ID=?", arrayOf(id.toString()))
                    if (count > 0) {
                        context!!.contentResolver.notifyChange(uri, null)
                    }
                }
            }

            ROW_DIR -> {
                Log.d(TAG, "update(dir) value $values")
                synchronized(mDbHelper) {
                    val db = mDbHelper.writableDatabase
                    count = db.update(TABLE_NAME, values, selection, selectionArgs)
                    if (count > 0) {
                        //변경통지
                        context!!.contentResolver.notifyChange(uri, null)
                    }
                }
            }

            else -> {
                throw IllegalAccessException("인수의 URI가 틀렸습니다.")
            }
        }

        return count
    }

    /**
     * 레코드 삭제
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var count = 0

        when (sUriMatcher.match(uri)) {
            ROW_ITEM -> {
                val id = ContentUris.parseId(uri).toInt()

                synchronized(mDbHelper) {
                    Log.d(TAG, "delete(item) id= $id")
                    val db = mDbHelper.writableDatabase

                    count = db.delete(TABLE_NAME, "$_ID=?", arrayOf(id.toString()))
                    if (count > 0) {
                        context!!.contentResolver.notifyChange(uri, null)
                    }
                }
            }

            ROW_DIR -> {
                synchronized(mDbHelper) {
                    Log.d(TAG, "delete(dir)")
                    val db = mDbHelper.writableDatabase
                    count = db.delete(TABLE_NAME, selection, selectionArgs)
                    if (count > 0) {
                        //변경통지
                        context!!.contentResolver.notifyChange(uri, null)
                    }
                }
            }

            else -> {
                throw IllegalAccessException("인수의 URI가 틀렸습니다.")
            }
        }
        return count
    }
}
