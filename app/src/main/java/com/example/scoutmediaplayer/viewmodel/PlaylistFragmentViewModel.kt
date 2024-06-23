package com.example.scoutmediaplayer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.scoutmediaplayer.data.Song
import com.example.scoutmediaplayer.domain.SongsRepository

class PlaylistFragmentViewModel : ViewModel {


    constructor(repo: SongsRepository) : super() {
        songRepository = repo
    }

    private lateinit var songRepository: SongsRepository
    private var songsList: ArrayList<Song> = ArrayList<Song>()

    fun addSongs() {
        val newSongs = songRepository.getSongs()
        songsList.clear()
        songsList.addAll(newSongs)
//        adapter.notifyDataSetChanged()
    }

    fun clearPlaylist() {

    }
}