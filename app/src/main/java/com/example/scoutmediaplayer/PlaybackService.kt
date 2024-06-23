package com.example.scoutmediaplayer

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.scoutmediaplayer.data.Song

class PlaybackService : MediaSessionService() {

    companion object {
        private const val LOG_TAG = "PlaybackService"
        private const val INTENT_TYPE = "INTENT_TYPE"
        private const val INTENT_SONG_ID = "INTENT_SONG_ID"
        private const val INTENT_SONG = "INTENT_SONG"

        @JvmStatic
        fun createPlaybackIntent(
            context: Context,
            type: IntentType,
            id: Int = -1,
            song: Song?
        ): Intent {
            var intent = Intent(context, PlaybackService::class.java)
            intent.putExtra(INTENT_TYPE, type.toString())
            when (type) {
                //TODO handle resume if on pause?
                IntentType.PLAY -> {
                    intent.putExtra(INTENT_SONG_ID, id)
                    intent.putExtra(INTENT_SONG, song)
                }

                IntentType.PAUSE -> TODO()
                IntentType.PREV -> TODO()
                IntentType.NEXT -> TODO()
            }
            return intent
        }
    }

    private lateinit var mBinder: IBinder
    private lateinit var player: ExoPlayer
    private var mediaSession: MediaSession? = null

    inner class PlaybackServiceBinder : Binder() {
        fun getPlayer(): ExoPlayer {
            return this@PlaybackService.player
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG, "onStartCommand: $intent")
        if (intent != null && intent.hasExtra(INTENT_TYPE)) {
            handleCustomPlayback(intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun handleCustomPlayback(intent: Intent) {
        when (IntentType.valueOf(intent.getStringExtra(INTENT_TYPE)!!)) {
            IntentType.PLAY -> handlePlayIntent(intent)
            IntentType.PAUSE -> TODO()
            IntentType.PREV -> TODO()
            IntentType.NEXT -> TODO()
        }

//        player.seekTo(1, 0);
    }

    private fun handlePlayIntent(intent: Intent) {
        var song = intent.getParcelableExtra<Song>(INTENT_SONG)
        var songId = intent.getIntExtra(INTENT_SONG_ID, -1)
        if (song != null && !TextUtils.isEmpty(song.songUri) && songId != -1) {
            player.setMediaItem(MediaItem.fromUri(song.songUri))
            player.prepare()
        }

//        if (songId != -1) {
//            player.seekTo(songId, 0);
//        } else if (song != null) {
//            player.setMediaItem(MediaItem.fromUri(song.songUri))
//            player.prepare()
//        }
    }

    override fun onCreate() {
        super.onCreate()
        mBinder = PlaybackServiceBinder()
        Log.d(LOG_TAG, "Init PlaybackService")
        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()
        player.playWhenReady = true
        player.prepare()
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

    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
        //custom binder breaks media session controls
//        super.onBind(intent)
//        return mBinder
    }

    enum class IntentType {
        PLAY,
        PAUSE,
        PREV,
        NEXT
    }
}