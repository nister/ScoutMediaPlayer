package com.example.scoutmediaplayer.domain

import com.example.scoutmediaplayer.data.Song

interface PlayerRepository {
    fun play()
    fun pause()
    fun playPrevSong()
    fun playNextSong()
    fun play(id: Int, songs: ArrayList<Song>)
}