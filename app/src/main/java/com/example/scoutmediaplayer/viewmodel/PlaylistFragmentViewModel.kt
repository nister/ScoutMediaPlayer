package com.example.scoutmediaplayer.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.example.scoutmediaplayer.domain.SongsRepository
import com.example.scoutmediaplayer.view.PlaylistFragment.OnSongSelectedListener

class PlaylistFragmentViewModel : ViewModel() {

    lateinit var songRepository: SongsRepository
    lateinit var onSongSelectedListener: OnSongSelectedListener

    val songsViewModels: ObservableList<SongListItemViewModel> = ObservableArrayList()

    fun addSongs() {
        val newSongs = songRepository.getSongs()
        songsViewModels.clear()
        songsViewModels.addAll(newSongs.map {
            SongListItemViewModel(
                newSongs.indexOf(it),
                it,
                onSongSelectedListener
            )
        })
    }

    fun clearPlaylist() {
        songsViewModels.clear()
    }
}