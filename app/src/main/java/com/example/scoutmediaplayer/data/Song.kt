package com.example.scoutmediaplayer.data

import android.os.Parcel
import android.os.Parcelable

data class Song(
    val songArtist: String?,
    val songTitle: String?,
    val songDuration: String?,
    val songUri: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Song

        if (songArtist != other.songArtist) return false
        if (songTitle != other.songTitle) return false
        if (songDuration != other.songDuration) return false
        if (songUri != other.songUri) return false

        return true
    }

    override fun hashCode(): Int {
        var result = songArtist?.hashCode() ?: 0
        result = 31 * result + (songTitle?.hashCode() ?: 0)
        result = 31 * result + (songDuration?.hashCode() ?: 0)
        result = 31 * result + (songUri.hashCode())
        return result
    }

    override fun toString(): String {
        return "Song(songArtist='$songArtist', songTitle='$songTitle', songDuration='$songDuration', songUri='$songUri')"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(songArtist)
        parcel.writeString(songTitle)
        parcel.writeString(songDuration)
        parcel.writeString(songUri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song {
            return Song(parcel)
        }

        override fun newArray(size: Int): Array<Song?> {
            return arrayOfNulls(size)
        }
    }


}