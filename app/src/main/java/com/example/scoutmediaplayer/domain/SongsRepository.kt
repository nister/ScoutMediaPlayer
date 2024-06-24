package com.example.scoutmediaplayer.domain

import com.example.scoutmediaplayer.data.Song

interface SongsRepository {
    fun getSongs() : List<Song>
}