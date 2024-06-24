package com.example.scoutmediaplayer.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.example.scoutmediaplayer.data.Song
import com.example.scoutmediaplayer.domain.SongsRepository
import com.example.scoutmediaplayer.view.PlaylistFragment.PlaylistFragmentListener

class PlaylistFragmentViewModel : ViewModel {

    constructor(repo: SongsRepository, fragmentListener: PlaylistFragmentListener) : super() {
        songRepository = repo
        this.fragmentListener = fragmentListener
    }

    private var songRepository: SongsRepository
    private var fragmentListener: PlaylistFragmentListener

    val songsViewModels: ObservableList<PlaylistFragmentListItemViewModel> = ObservableArrayList()

    fun addSongs() {
        val newSongs = songRepository.getSongs()
        songsViewModels.clear()
        songsViewModels.addAll(newSongs.map { PlaylistFragmentListItemViewModel(newSongs.indexOf(it), it, fragmentListener) })
    }

    fun clearPlaylist() {

    }
}