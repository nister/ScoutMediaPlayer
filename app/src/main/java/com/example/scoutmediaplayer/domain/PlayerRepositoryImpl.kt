package com.example.scoutmediaplayer.domain

import android.content.Context

class PlayerRepositoryImpl(context: Context?) : PlayerRepository {

    //TODO context dependency is baaad
    override fun play() {
//        player.play()
    }

    override fun pause() {
//        player.pause()
    }

    override fun playPrevSong() {
//        player.seekToPreviousMediaItem()
    }

    override fun playNextSong() {
//        player.seekToNextMediaItem()
    }
}