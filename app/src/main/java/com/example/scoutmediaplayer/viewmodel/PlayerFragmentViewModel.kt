package com.example.scoutmediaplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerFragmentViewModel : ViewModel() {

    val artist: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    val title: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
}