package com.example.scoutmediaplayer.viewmodel

import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoutmediaplayer.domain.PlayerRepository

class PlayerFragmentViewModel() : ViewModel() {

    lateinit var playerRepository: PlayerRepository

    val author: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    val trackName: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    val cover: MutableLiveData<BitmapDrawable> by lazy {
        MutableLiveData<BitmapDrawable>(BitmapDrawable())
    }

    fun openPlaylist() {

    }

    fun openDefaultPlayer() {

    }

    fun restorePrevSession() {
        //
    }
}