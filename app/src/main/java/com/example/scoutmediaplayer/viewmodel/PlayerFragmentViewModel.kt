package com.example.scoutmediaplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoutmediaplayer.domain.PlayerRepository

//TODO DI
class PlayerFragmentViewModel : ViewModel() {

    lateinit var playerRepository: PlayerRepository

    val artist: MutableLiveData<String> by lazy {
        MutableLiveData<String>("author")
    }

    val title: MutableLiveData<String> by lazy {
        MutableLiveData<String>("trackName")
    }

    fun restorePrevSession() {
        //
    }
}