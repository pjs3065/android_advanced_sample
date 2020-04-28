package com.zum.contentprovidersample2

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.os.Bundle
import android.provider.BaseColumns._ID
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import com.zum.contentprovidersample2.WordsOfTodayContract.WordsOfTodayColumns.Companion.DATE
import com.zum.contentprovidersample2.WordsOfTodayContract.WordsOfTodayColumns.Companion.NAME
import com.zum.contentprovidersample2.WordsOfTodayContract.WordsOfTodayColumns.Companion.WORDS
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val LOADER_ID = 1

        private val PROJECTIONS = arrayOf(_ID, NAME, WORDS, DATE)
        private lateinit var mAdapter: CursorAdapter
        private lateinit var mObserver: ContentObserver
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(BuildConfig.DEBUG){
            val context = applicationContext
            Stetho.initializeWithDefaults(this)
        }
        setContentView(R.layout.activity_main)

        mAdapter = object:CursorAdapter(applicationContext, null, false){
            override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
                Log.d(TAG, "newView")
                return View.inflate(context, R.layout.list_item, null)
            }

            override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
                cursor?.let {
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                    val words = cursor.getString(cursor.getColumnIndexOrThrow(WORDS))
                    var date = cursor.getString(cursor.getColumnIndexOrThrow(DATE))

                    date =
                        date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6)
                    Log.d(TAG, "bindView name=$name, dateText=$date")

                    val nameText = view!!.findViewById<View>(R.id.nameText) as TextView
                    val wordsText = view.findViewById<View>(R.id.wordsText) as TextView
                    val dateText = view.findViewById<View>(R.id.dateText) as TextView

                    nameText.text = name
                    wordsText.text = words
                    dateText.text = date
                }
            }
        }

        listView.adapter = mAdapter
        mAdapter.notifyDataSetChanged()

        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        Log.d(TAG, "onCreateLoader")
        return CursorLoader(this, WordsOfTodayContract.CONTENT_URI, PROJECTIONS, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        Log.d(TAG, "onLoadFinished")
        data!!.setNotificationUri(contentResolver, WordsOfTodayContract.CONTENT_URI)
        mAdapter.swapCursor(data)
        mAdapter.notifyDataSetChanged()

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        mAdapter.swapCursor(null)
    }
}
