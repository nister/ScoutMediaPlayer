package com.example.scoutmediaplayer.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.example.scoutmediaplayer.data.Song
import com.example.scoutmediaplayer.domain.PlayerRepositoryImpl
import com.example.scoutmediaplayer.domain.SongsRepository

//TODO DI
class PlaylistFragmentViewModel : ViewModel() {

    lateinit var playerRepository: PlayerRepositoryImpl
    lateinit var songRepository: SongsRepository
    lateinit var contract: Contract

    val songsViewModels: ObservableList<SongListItemViewModel> = ObservableArrayList()

    interface Contract {
        fun togglePlaylist()
        fun onSongSelected(id: Int, song: Song)
    }

    fun addSongs() {
        var listener = object : SongListItemViewModel.OnSongSelectedListener {
            override fun onSongSelected(id: Int, song: Song) {
                contract.onSongSelected(id, song)
            }
        }
        val newSongs = songRepository.getSongs()
        playerRepository.play(0, newSongs)
        songsViewModels.clear()
        songsViewModels.addAll(newSongs.map {
            SongListItemViewModel(
                newSongs.indexOf(it),
                it,
                listener
            )
        })
    }

    fun clearPlaylist() {
        songsViewModels.clear()
    }

    fun togglePlaylist() {
        contract.togglePlaylist()
    }
}