package com.example.scoutmediaplayer.domain

import com.example.scoutmediaplayer.data.Song

class SongsRepositoryImpl : SongsRepository {
    override suspend fun getSongs(): ArrayList<Song> {
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

        return list
    }
}