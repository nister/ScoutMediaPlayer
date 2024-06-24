package com.example.scoutmediaplayer.view

import android.content.ComponentName
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import com.example.scoutmediaplayer.PlaybackService
import com.example.scoutmediaplayer.R
import com.example.scoutmediaplayer.databinding.FragmentDefaultPlayerViewBinding
import com.example.scoutmediaplayer.domain.PlayerRepositoryImpl
import com.example.scoutmediaplayer.viewmodel.PlayerFragmentViewModel
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DefaultPlayerViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DefaultPlayerViewFragment : Fragment() {

    private lateinit var viewBinding: FragmentDefaultPlayerViewBinding
    private lateinit var playerView: PlayerView
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private lateinit var sessionToken: SessionToken

    companion object {
        const val LOG_TAG = "PlaylistFragment"
    }

    override fun onStart() {
        super.onStart()
        sessionToken = SessionToken(requireActivity(), ComponentName(requireActivity(), PlaybackService::class.java))
        controllerFuture = MediaController.Builder(requireActivity(), sessionToken).buildAsync()

        controllerFuture.addListener(
            {
                playerView.setPlayer(controllerFuture.get())
                var player = playerView.player as MediaController
                val listener = object : Player.Listener {
                    override fun onEvents(player: Player, events: Player.Events) {
                        super.onEvents(player, events)
                        Log.d(LOG_TAG, "onEvents: $events")
                    }

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                        Log.d(LOG_TAG, "onPlaybackStateChanged: $playbackState")
                    }

                    override fun onTracksChanged(tracks: Tracks) {
                        super.onTracksChanged(tracks)
                        Log.d(LOG_TAG, "onPlaybackStateChanged: $tracks")
                    }
                }
                player.addListener(listener)
                player.playWhenReady = true
                player.setMediaItem(MediaItem.fromUri("https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
                player.prepare()
            },
            MoreExecutors.directExecutor()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerView = view.findViewById(R.id.player_view)
//        playerView.setShowFastForwardButton(false);
//        playerView.setShowPreviousButton(false);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDefaultPlayerViewBinding.inflate(layoutInflater, container, false)
        val vm = ViewModelProvider(this)[PlayerFragmentViewModel::class.java]
        vm.playerRepository = PlayerRepositoryImpl(requireActivity())
        viewBinding.viewModel = vm
        return viewBinding.root
    }
}