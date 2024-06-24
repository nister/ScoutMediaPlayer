package com.example.scoutmediaplayer.domain

import androidx.media3.exoplayer.ExoPlayer

interface PlayerRepository {
    fun play()
    fun pause()
    fun playPrevSong()
    fun playNextSong()
}