package com.example.scoutmediaplayer

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
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
        private const val INTENT_SONG_LIST = "INTENT_SONG_LIST"

        @JvmStatic
        fun createPlaybackIntent(
            context: Context,
            type: IntentType,
            id: Int = -1,
            songList: ArrayList<Song>?
        ): Intent {
            var intent = Intent(context, PlaybackService::class.java)
            intent.putExtra(INTENT_TYPE, type.toString())
            when (type) {
                IntentType.PLAY -> {
                    intent.putExtra(INTENT_SONG_ID, id)
                    intent.putParcelableArrayListExtra(INTENT_SONG_LIST, songList)
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
    }

    private fun handlePlayIntent(intent: Intent) {
        var songId = intent.getIntExtra(INTENT_SONG_ID, -1)

        val songsList: java.util.ArrayList<Song>? =
            intent.getParcelableArrayListExtra(INTENT_SONG_LIST)
        if (songsList != null) {

            player.clearMediaItems()
            player.addMediaItems(songsList.map {
                Log.d(LOG_TAG, "handlePlayIntent.addMediaItems: $it.songUri")
                MediaItem.fromUri(it.songUri) })
        }
        if (songId != -1) {
            player.seekTo(songId, 0)
            player.prepare()
        }
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