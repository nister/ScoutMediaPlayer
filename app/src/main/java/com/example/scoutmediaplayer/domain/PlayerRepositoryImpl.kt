package com.example.scoutmediaplayer.domain

import androidx.media3.exoplayer.ExoPlayer

class PlayerRepositoryImpl(private val player: ExoPlayer) : PlayerRepository {
    override fun getPlayer(): ExoPlayer {
        return player
    }

    override fun play() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun playPrevSong() {
        player.seekToPreviousMediaItem()
    }

    override fun playNextSong() {
        player.seekToNextMediaItem()
    }
}