package com.example.scoutmediaplayer.domain

import com.example.scoutmediaplayer.data.Song

class SongsRepositoryImpl : SongsRepository {
    override fun getSongs(): ArrayList<Song> {
        val list = ArrayList<Song>()
        list.add(Song("Stab Artist",
            "Stab Title",
            "1:1",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stab Artist 2",
            "Stab Title 2",
            "22:22",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stab Artist 3",
            "Stab Title 3",
            "33:33",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stab Artist 4",
            "Stab Title 4",
            "4:4",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stab Artist 5",
            "Stab Title 5",
            "5:5",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stab Artist 6",
            "Stab Title 6",
            "6:6",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        list.add(Song("Stab Artist 7",
            "Stab Title 7",
            "7:7",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        return list
    }

    override fun getAssetSongs(): ArrayList<Song> {
        val list = ArrayList<Song>()
        return list
    }
}