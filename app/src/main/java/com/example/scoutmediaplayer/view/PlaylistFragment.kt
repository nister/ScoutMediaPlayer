package com.example.scoutmediaplayer.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.scoutmediaplayer.data.Song
import com.example.scoutmediaplayer.databinding.FragmentPlaylistListBinding
import com.example.scoutmediaplayer.domain.SongsRepositoryImpl
import com.example.scoutmediaplayer.viewmodel.PlaylistFragmentViewModel

class PlaylistFragment : Fragment(), PlaylistFragmentViewModel.Contract {

    private lateinit var contract: Contract
    private lateinit var onSongSelectedListener: OnSongSelectedListener
    private lateinit var viewBinding: FragmentPlaylistListBinding

    interface Contract {
        fun togglePlaylist()
    }

    interface OnSongSelectedListener {
        fun onSongSelected(id: Int, song: Song)
    }

    companion object {
        const val LOG_TAG = "PlaylistFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onSongSelectedListener = context as OnSongSelectedListener
        contract = context as Contract
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentPlaylistListBinding.inflate(layoutInflater, container, false)
        val vm = ViewModelProvider(this)[PlaylistFragmentViewModel::class.java]
        vm.songRepository = SongsRepositoryImpl()
        vm.contract = this
        viewBinding.viewModel = vm
        val view = viewBinding.root
        return view
    }

    override fun togglePlaylist() {
        contract.togglePlaylist()
    }

    override fun onSongSelected(id: Int, song: Song) {
        onSongSelectedListener.onSongSelected(id, song)
    }

}