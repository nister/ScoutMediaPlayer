package com.example.scoutmediaplayer.ui


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.scoutmediaplayer.PlaybackService
import com.example.scoutmediaplayer.R
import com.example.scoutmediaplayer.data.Song

class MainActivity : AppCompatActivity(), PlayerFragment.PlayerFragmentContract,
    PlaylistFragment.PlaylistFragmentListener {

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
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
//        switchFragment(FragmentType.PLAYER)
//        switchFragment(FragmentType.DEFAULT_PLAYER)
        switchFragment(FragmentType.PLAYLIST)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun switchFragment(type: FragmentType) {
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(type.toString())
        if (fragment == null) {
            fragment = createFragment(type)
        }
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(type.toString())
            .replace(R.id.main_fragment_container, fragment, type.toString())
            .commit()
    }

    private fun createFragment(type: FragmentType): Fragment {
        return when (type) {
            FragmentType.PLAYER -> PlayerFragment()

            FragmentType.PLAYLIST -> PlaylistFragment()

            FragmentType.DEFAULT_PLAYER -> DefaultPlayerViewFragment()
        }
    }

    override fun openPlaylist() {
        switchFragment(FragmentType.PLAYLIST)
    }

    override fun openDefaultPlayer() {
        switchFragment(FragmentType.DEFAULT_PLAYER)
    }

    override fun onSongSelected(id: Int, song: Song) {
        val serviceIntent = Intent(this, PlaybackService::class.java)
        serviceIntent.putExtra("COMMAND", "PLAY");
        serviceIntent.putExtra("ID", id);
        serviceIntent.putExtra("URI", song.songUri);
        startService(PlaybackService.createPlaybackIntent(this, PlaybackService.IntentType.PLAY, id, song));
    }
}