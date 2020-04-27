package com.zum.contentprovidersample

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //권한 체크
        checkPermission()
    }

    private fun checkPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 200)
        } else {
            initView()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            200 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initView()
                } else {
                    Toast.makeText(this, "스토리지에 접근 권한을 허가해주세요", Toast.LENGTH_SHORT).show()
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 200)
                }
            }
        }
    }

    private fun initView() {
        try {
            val cursor = getImage()
            if (cursor.moveToFirst()) {
                //1. 각 컬럼의 열 인덱스를 취득한다.
                val idColNum = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                val titleColNum = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.TITLE)
                val dateTakenColNum =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)

                //2. 인덱스를 바탕으로 데이터를 Cursor로부터 취득하기
                val id = cursor.getLong(idColNum)
                val title = cursor.getString(titleColNum)
                val dateTaken = cursor.getLong(dateTakenColNum)
                val imageUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                //3. 데이터를 View로 설정
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = dateTaken
                val text = DateFormat.format("yyyy/MM/dd (E) kk:mm:ss", calendar).toString()

                textView.text = "촬용일시: $text"
                imageView.setImageURI(imageUri)
            }
            cursor.close()
        } catch (e: SecurityException) {
            Toast.makeText(this, "스토리지에 접근 권한을 허가해주세요", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun getImage(): Cursor {
        val resolver = contentResolver
        var queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        //가져올 컬럼명
        val what = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.TITLE,
            MediaStore.Images.ImageColumns.DATE_TAKEN
        )

        //정렬
        val orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"

        //1건만 가져온다.
        queryUri = queryUri.buildUpon().appendQueryParameter("limit", "1").build()

        return resolver.query(queryUri, what, null, null, orderBy)!!
    }
}
