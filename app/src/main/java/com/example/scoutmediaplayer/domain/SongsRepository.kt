package com.example.scoutmediaplayer.domain

import com.example.scoutmediaplayer.data.Song

interface SongsRepository {
    suspend fun getStubSongs() : ArrayList<Song>
    suspend fun getAssetSongs() : ArrayList<Song>
}