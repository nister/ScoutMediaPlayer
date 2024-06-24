package com.example.scoutmediaplayer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.scoutmediaplayer.data.Song
import com.example.scoutmediaplayer.view.PlaylistFragment.Companion.LOG_TAG
import com.example.scoutmediaplayer.view.PlaylistFragment.OnSongSelectedListener

class SongListItemViewModel(val id: Int, val song: Song, private val listener: OnSongSelectedListener) : ViewModel() {

    fun itemClick() {
        Log.e(LOG_TAG, "onItemClick: $id")
        listener.onSongSelected(id, song)
    }
}