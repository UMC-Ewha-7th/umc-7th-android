package com.example.flo_clone.ui.locker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LockerViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is locker Fragment"
    }
    val text: LiveData<String> = _text
}