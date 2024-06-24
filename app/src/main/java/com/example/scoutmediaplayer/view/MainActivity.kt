package com.example.scoutmediaplayer.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.scoutmediaplayer.PlaybackService
import com.example.scoutmediaplayer.R
import com.example.scoutmediaplayer.data.Song

class MainActivity : AppCompatActivity(), PlaylistFragment.OnSongSelectedListener,
    PlaylistFragment.Contract {

    companion object {
        private const val LOG_TAG = "MainActivity"
    }

    enum class FragmentType {
        PLAYER,
        PLAYLIST,
        DEFAULT_PLAYER
    }

    // TODO: Try Compose UI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        addFragment(FragmentType.DEFAULT_PLAYER, R.id.main_fragment_container)
        addFragment(FragmentType.PLAYLIST, R.id.playlist_fragment_container)
    }

    private fun addFragment(type: FragmentType, layoutId: Int) {
        supportFragmentManager
            .beginTransaction()
            .add(layoutId, createFragment(type), type.toString())
            .commit()
    }

    private fun createFragment(type: FragmentType): Fragment {
        return when (type) {
            FragmentType.PLAYER -> PlayerFragment()
            FragmentType.PLAYLIST -> PlaylistFragment()
            FragmentType.DEFAULT_PLAYER -> DefaultPlayerViewFragment()
        }
    }

    override fun onSongSelected(id: Int, song: Song) {
        var list = ArrayList<Song>()
        list.add(song)
        startService(
            PlaybackService.createPlaybackIntent(
                this,
                PlaybackService.IntentType.PLAY,
                id,
                list
            )
        )
    }

    override fun togglePlaylist() {
        var playlistFragment =
            supportFragmentManager.findFragmentByTag(FragmentType.DEFAULT_PLAYER.toString())
        if (playlistFragment != null) {
            if (playlistFragment.isVisible) {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .hide(playlistFragment)
                    .commit();
            } else {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(playlistFragment)
                    .commit();
            }
        }
    }
}