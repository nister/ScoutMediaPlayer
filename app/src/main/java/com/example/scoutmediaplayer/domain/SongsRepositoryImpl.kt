package com.example.scoutmediaplayer.domain

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaMetadataRetriever
import android.util.Log
import com.example.scoutmediaplayer.data.Song


class SongsRepositoryImpl(private val applicationContext: Context) : SongsRepository {

    companion object {
        const val LOG_TAG = "PlaylistFragment"
    }

    override suspend fun getStubSongs(): ArrayList<Song> {
        val list = ArrayList<Song>()
        list.add(Song("Stub Artist",
            "Stub Title",
            "1:1",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stub Artist 2",
            "Stub Title 2",
            "22:22",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stub Artist 3",
            "Stub Title 3",
            "33:33",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stub Artist 4",
            "Stub Title 4",
            "4:4",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stub Artist 5",
            "Stub Title 5",
            "5:5",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stub Artist 6",
            "Stub Title 6",
            "6:6",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stub Artist 7",
            "Stub Title 7",
            "7:7",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        return list
    }

    override suspend fun getAssetSongs(): ArrayList<Song> {
        val list = ArrayList<Song>()
        applicationContext.assets.list("")!!.forEach {
            Log.d(LOG_TAG, "getAssetSongs: $it")
            if (it != null && it.contains(".mp3")) {
                val fd = applicationContext.assets.openFd(it)
                list.add(readSogFromAsset("asset:///android_asset/$it", fd))
                }
        }
        return list
    }

    private fun readSogFromAsset(path: String, file: AssetFileDescriptor): Song {
//    private fun readSogFromAsset(filename: String): Song {
        val metadataRetriever = MediaMetadataRetriever()
//        var uri = "file:///android_asset/$filename"
        metadataRetriever.setDataSource(file.fileDescriptor, file.startOffset, file.length)
        metadataRetriever.setDataSource(file.fileDescriptor, file.startOffset, file.length)
        val artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        val title = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        val duration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        var song = Song(artist, title, duration, path)
        Log.d(LOG_TAG, "readSogFromAsset: $song")
        return song
    }
}