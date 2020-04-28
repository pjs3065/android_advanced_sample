package com.zum.contentprovidersample2

import android.os.Parcel
import android.os.Parcelable

class WordsOfToday:Parcelable {
    private var _id:Long = 0
    private var name:String = ""
    private var words:String = ""
    private var date:Int = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<WordsOfToday> = object: Parcelable.Creator<WordsOfToday>{
            override fun createFromParcel(parcel:Parcel): WordsOfToday {
                return WordsOfToday(parcel)
            }
            override fun newArray(size:Int): Array<WordsOfToday?> {
                return arrayOfNulls(size)
            }
        }
    }

    constructor() {}

    constructor(id:Long, name:String, words:String, date:Int) {
        this._id = id
        this.name = name
        this.words = words
        this.date = date
    }

    constructor(parcel:Parcel) {
        _id = parcel.readLong()
        name = parcel.readString().toString()
        words = parcel.readString().toString()
        date = parcel.readInt()
    }

    override fun writeToParcel(dest:Parcel, flags:Int) {
        dest.writeLong(_id)
        dest.writeString(name)
        dest.writeString(words)
        dest.writeInt(date)
    }

    override fun describeContents():Int {
        return 0
    }

    public override fun toString():String {
        return ("WordsOfToday _id=" + _id + ", name=" + name +
                ", words=" + words + ", date=" + date)
    }
}