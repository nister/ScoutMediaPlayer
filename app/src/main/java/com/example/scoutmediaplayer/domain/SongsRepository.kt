package com.example.scoutmediaplayer.domain

import com.example.scoutmediaplayer.data.Song

//TODO do async call
interface SongsRepository {
    suspend fun getSongs() : ArrayList<Song>
    suspend fun getAssetSongs() : ArrayList<Song>
}