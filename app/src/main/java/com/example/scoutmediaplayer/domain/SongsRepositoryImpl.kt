package com.example.scoutmediaplayer.domain

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaMetadataRetriever
import android.text.TextUtils
import android.util.Log
import com.example.scoutmediaplayer.data.Song
import java.util.concurrent.TimeUnit


class SongsRepositoryImpl(private val applicationContext: Context) : SongsRepository {

    companion object {
        const val LOG_TAG = "PlaylistFragment"
    }

    override suspend fun getStubSongs(): ArrayList<Song> {
        val list = ArrayList<Song>()
        list.add(
            Song(
                "Kai Engel",
                "Blizzard (PON I)",
                "03:00",
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"
            )
        )
        return list
    }

    override suspend fun getAssetSongs(): ArrayList<Song> {
        val list = ArrayList<Song>()
        applicationContext.assets.list("")!!.forEach {
            Log.d(LOG_TAG, "getAssetSongs: $it")
            if (it != null && it.contains(".mp3")) {
                val fd = applicationContext.assets.openFd(it)
                list.add(readSogFromAsset("asset:///android_asset/$it", fd))
                fd.close()
            }
        }
        return list
    }

    private fun readSogFromAsset(path: String, file: AssetFileDescriptor): Song {
        val metadataRetriever = MediaMetadataRetriever()
        metadataRetriever.setDataSource(file.fileDescriptor, file.startOffset, file.length)
        val artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        val title = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        var durationMetadata =
            metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        var durationString = ""
        if (!TextUtils.isEmpty(durationMetadata)) {
            val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(durationMetadata!!.toLong())
            val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(durationMetadata!!.toLong()) % 60
            durationString = String.format("%02d:%02d", minutes, seconds)
        }
        var song = Song(artist, title, durationString, path)
        Log.d(LOG_TAG, "readSogFromAsset: $song")
        return song
    }
}