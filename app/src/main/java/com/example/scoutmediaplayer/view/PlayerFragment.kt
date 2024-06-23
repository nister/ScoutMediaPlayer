package com.example.scoutmediaplayer.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.exoplayer.ExoPlayer
import com.example.scoutmediaplayer.PlaybackService
import com.example.scoutmediaplayer.databinding.FragmentPlayerBinding
import com.example.scoutmediaplayer.domain.PlayerRepository
import com.example.scoutmediaplayer.domain.PlayerRepositoryImpl

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerFragment : Fragment() {

    private lateinit var playerRepository: PlayerRepository
    private lateinit var contract: PlayerFragmentContract
    private var binder: PlaybackService.PlaybackServiceBinder? = null
    private lateinit var player: ExoPlayer
    private lateinit var viewBinding: FragmentPlayerBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as PlaybackService.PlaybackServiceBinder
            Log.d(LOG_TAG, "PlayerFragment: bind to the service")
            player = binder!!.getPlayer();
//            var sessionToken = SessionToken(
//                requireActivity(),
//                ComponentName(requireActivity(), PlaybackService::class.java)
//            )
//            var controllerFuture =
//                MediaController.Builder(requireActivity(), sessionToken).buildAsync()
//            controllerFuture.addListener(
//                {
//                    playerView.setPlayer(controllerFuture.get())
//                },
//                MoreExecutors.directExecutor()
//            )
//            player.playWhenReady = true
//            player.setMediaItem(MediaItem.fromUri("https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"))
//            player.prepare()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
            //TODO start, stop, destroy
        }
    }

    interface PlayerFragmentContract {
        fun openPlaylist()
        fun openDefaultPlayer()
    }

    private fun bindToService() {
        if (binder == null) {
            requireActivity().bindService(
                Intent(context, PlaybackService::class.java),
                connection,
                Context.BIND_AUTO_CREATE
            )
//            PlaybackService.newIntent(this).also { intent ->
//                bindService(intent, connection, Context.BIND_AUTO_CREATE)
//            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "PlayerFragment: onCreate")
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        bindToService()
        playerRepository = PlayerRepositoryImpl()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = FragmentPlayerBinding.inflate(layoutInflater, container, false)
        viewBinding.playerPlaylist.setOnClickListener { contract.openPlaylist() }
        viewBinding.playerDefault.setOnClickListener { contract.openDefaultPlayer() }
        return viewBinding.root;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //TODO rewrite to repo?
        contract = context as PlayerFragmentContract
    }

    companion object {

        private const val LOG_TAG = "PlayerFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}