package com.example.scoutmediaplayer.view

import android.content.ComponentName
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import com.example.scoutmediaplayer.PlaybackService
import com.example.scoutmediaplayer.R
import com.example.scoutmediaplayer.databinding.FragmentDefaultPlayerViewBinding
import com.example.scoutmediaplayer.viewmodel.PlayerFragmentViewModel
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

class DefaultPlayerViewFragment : Fragment() {

    private lateinit var player: MediaController
    private lateinit var vm: PlayerFragmentViewModel
    private lateinit var viewBinding: FragmentDefaultPlayerViewBinding
    private lateinit var playerView: PlayerView
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private lateinit var sessionToken: SessionToken

    companion object {
        const val LOG_TAG = "DefaultPlayerViewFragment"
    }

    override fun onStart() {
        super.onStart()
        setupPlayer()
    }

    private fun setupPlayer() {
        sessionToken = SessionToken(
            requireActivity(),
            ComponentName(requireActivity(), PlaybackService::class.java)
        )
        controllerFuture = MediaController.Builder(requireActivity(), sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                playerView.setPlayer(controllerFuture.get())
                player = playerView.player as MediaController
                player.addListener(createPlayerListener())
            },
            MoreExecutors.directExecutor()
        )
    }

    private fun createPlayerListener(): Player.Listener {
        return object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                Log.d(LOG_TAG, "onEvents: $events.")
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                Log.d(LOG_TAG, "onPlaybackStateChanged: $playbackState")
                updateMetadata()
            }

            override fun onTracksChanged(tracks: Tracks) {
                super.onTracksChanged(tracks)
                Log.d(LOG_TAG, "onPlaybackStateChanged: $tracks")
            }
        }
    }

    private fun updateMetadata() {
        val metadataRetriever = MediaMetadataRetriever()
        val uriStr = player.currentMediaItem?.localConfiguration?.uri.toString()
        if (uriStr.startsWith("asset")) {
            var file =
                requireActivity().assets.openFd(uriStr.replace("asset:///android_asset/", ""))
            metadataRetriever.setDataSource(file.fileDescriptor, file.startOffset, file.length)
        } else {
            metadataRetriever.setDataSource(player.currentMediaItem?.localConfiguration?.uri.toString())
        }

        val artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        val title = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        Log.d(LOG_TAG, "updateMetadata artist: $artist")
        Log.d(LOG_TAG, "updateMetadata title: $title")
        vm.artist.value = artist
        vm.title.value = title
    }

    @OptIn(UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerView = view.findViewById(R.id.player_view)
        // Disabled due to the requirements
        playerView.setShowFastForwardButton(false);
        playerView.setShowRewindButton(false);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDefaultPlayerViewBinding.inflate(layoutInflater, container, false)
        viewBinding.setLifecycleOwner(this);
        vm = ViewModelProvider(this)[PlayerFragmentViewModel::class.java]
        viewBinding.viewModel = vm
        return viewBinding.root
    }
}