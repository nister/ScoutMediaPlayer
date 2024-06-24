package com.example.scoutmediaplayer.domain

import com.example.scoutmediaplayer.data.Song

interface SongsRepository {
    fun getSongs() : ArrayList<Song>
    fun getAssetSongs() : ArrayList<Song>
}