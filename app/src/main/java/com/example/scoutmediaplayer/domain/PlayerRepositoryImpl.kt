package com.example.scoutmediaplayer.domain

import android.content.Context
import com.example.scoutmediaplayer.PlaybackService
import com.example.scoutmediaplayer.data.Song

class PlayerRepositoryImpl(var context: Context) : PlayerRepository {

    //TODO context dependency is baaad. Need DI Player dependencies.
    //TODO use it for custom player view implementation

    override fun play() {
//        player.play()
    }

    override fun play(id: Int, song: ArrayList<Song>) {
        context.startService(
            PlaybackService.createPlaybackIntent(
                context,
                PlaybackService.IntentType.PLAY,
                id,
                song
            )
        )
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