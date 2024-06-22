package com.example.scoutmediaplayer

import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class PlaybackService : MediaSessionService() {

    private val LOG_TAG = "PlaybackService"
    private lateinit var player: ExoPlayer
    private var mediaSession : MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "Init PlaybackService")
        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()
//        player.prepare()
        player.setMediaItem(MediaItem.fromUri("https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
        player.prepare()
//        player.seekTo(1, 0);
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }
}