package com.example.scoutmediaplayer.domain

import com.example.scoutmediaplayer.data.Song

class SongsRepositoryImpl : SongsRepository {
    override fun getSongs(): List<Song> {
        val list = ArrayList<Song>()
        list.add(Song("Stab Artist",
            "Stab Title",
            "1:1",
            "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        return list
    }
}