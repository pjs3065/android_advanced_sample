package com.zum.contentprovidersample2

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import android.util.Log
import com.zum.contentprovidersample2.WordsOfTodayContract.Companion.TABLE_NAME
import com.zum.contentprovidersample2.WordsOfTodayContract.WordsOfTodayColumns.Companion.DATE
import com.zum.contentprovidersample2.WordsOfTodayContract.WordsOfTodayColumns.Companion.NAME
import com.zum.contentprovidersample2.WordsOfTodayContract.WordsOfTodayColumns.Companion.WORDS

class WordsOfTodayDbHelper(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int,
    errorHandler: DatabaseErrorHandler
) : SQLiteOpenHelper(context, name, factory, version, errorHandler) {

    companion object {
        val TAG = WordsOfToday::class.java.simpleName
        const val DB_NAME = "WordsOfToday.db"
        const val DB_VERSION = 1

        val SQL_CREATE_TABLE =
            String.format("CREATE TABLE %s (\n", TABLE_NAME) +
                    String.format("%s INTEGER PRIMARY KEY AUTOINCREMENT,\n", _ID) +
                    String.format("%s TEXT,\n", NAME) +
                    String.format("%s TEXT,\n", WORDS) +
                    String.format("%s TEXT);", DATE)

        val SQL_INSERT_INITIAL_DATA: Array<String> = arrayOf(
            String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES('Taiki','날씨 참 좋다','20151001')",
                TABLE_NAME,
                NAME,
                WORDS,
                DATE
            ),
            String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES('Osamu','앱 버그 수정함','20151001')",
                TABLE_NAME,
                NAME,
                WORDS,
                DATE
            ),
            String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES('Osamu','오늘도 앱 버그 잡기','20151002')",
                TABLE_NAME,
                NAME,
                WORDS,
                DATE
            ),
            String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES('Taiki','열심히 운동함','20151002')",
                TABLE_NAME,
                NAME,
                WORDS,
                DATE
            ),
            String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES('Ken','머리 짧게 깎음','20151002')",
                TABLE_NAME,
                NAME,
                WORDS,
                DATE
            ),
            String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES('Taiki','오늘 점심 맛있네','20151003')",
                TABLE_NAME,
                NAME,
                WORDS,
                DATE
            ),
            String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES('Taiki','아침 4시 30분에 일어났다','20151004')",
                TABLE_NAME,
                NAME,
                WORDS,
                DATE
            )
        )
    }


    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "onCreate")
        db!!.beginTransaction()

        try {
            execSQL(db, SQL_CREATE_TABLE)
            for (sql in SQL_INSERT_INITIAL_DATA) {
                execSQL(db, sql)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(TAG,"onUpgrade oldVersion=$oldVersion, newVersion=$newVersion")
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        Log.d(TAG, "onOpen")

    }

    private fun execSQL(db: SQLiteDatabase, sql: String) {
        Log.d(TAG, "execSQL sql=$sql")
        db.execSQL(sql)
    }
}