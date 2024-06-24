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


class PlayerFragment : Fragment() {

    private var binder: PlaybackService.PlaybackServiceBinder? = null
    private lateinit var player: ExoPlayer
    private lateinit var viewBinding: FragmentPlayerBinding

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as PlaybackService.PlaybackServiceBinder
            Log.d(LOG_TAG, "PlayerFragment: bind to the service")
            player = binder!!.getPlayer();
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
            //TODO start, stop, destroy
        }
    }

    private fun bindToService() {
        if (binder == null) {
            requireActivity().bindService(
                Intent(context, PlaybackService::class.java),
                connection,
                Context.BIND_AUTO_CREATE
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "PlayerFragment: onCreate")
        super.onCreate(savedInstanceState)
        bindToService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = FragmentPlayerBinding.inflate(layoutInflater, container, false)
//        viewBinding.playerPlaylist.setOnClickListener { contract.openPlaylist() }
//        viewBinding.playerDefault.setOnClickListener { contract.openDefaultPlayer() }

//        val vm = ViewModelProvider(this)[PlayerFragmentViewModel::class.java]
//        vm.playerRepository = PlayerRepositoryImpl(requireActivity())
//        viewBinding.viewModel = vm

        return viewBinding.root;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    companion object {

        private const val LOG_TAG = "PlayerFragment"
    }
}