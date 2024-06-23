package com.example.scoutmediaplayer.view

import android.content.ComponentName
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import com.example.scoutmediaplayer.PlaybackService
import com.example.scoutmediaplayer.R
import com.example.scoutmediaplayer.databinding.FragmentDefaultPlayerViewBinding
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

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onStart() {
        super.onStart()
        sessionToken = SessionToken(requireActivity(), ComponentName(requireActivity(), PlaybackService::class.java))
        controllerFuture = MediaController.Builder(requireActivity(), sessionToken).buildAsync()

        controllerFuture.addListener(
            {
                playerView.setPlayer(controllerFuture.get())
                var player = playerView.player as MediaController
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
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentDefaultPlayerViewBinding.inflate(layoutInflater, container, false)
        viewBinding.viewModel = PlayerFragmentViewModel()
        return viewBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DefaultPlayerViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DefaultPlayerViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}