package com.example.scoutmediaplayer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.scoutmediaplayer.data.Song
import com.example.scoutmediaplayer.view.PlaylistFragment.Companion.LOG_TAG
import com.example.scoutmediaplayer.view.PlaylistFragment.PlaylistFragmentListener

class PlaylistFragmentListItemViewModel(val id: Int, val song: Song, val fragmentListener: PlaylistFragmentListener) : ViewModel() {

    fun itemClick() {
        Log.e(LOG_TAG, "onItemClick: $id")
        fragmentListener.onSongSelected(id, song)
    }
}