package com.example.scoutmediaplayer.domain

import androidx.media3.exoplayer.ExoPlayer

interface PlayerRepository {
    fun getPlayer(): ExoPlayer
    fun play()
    fun pause()
    fun playPrevSong()
    fun playNextSong()
}