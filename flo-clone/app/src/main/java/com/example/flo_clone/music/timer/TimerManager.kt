package com.example.flo_clone.music.timer

interface TimerManager {
    fun start()
    fun pause()
    fun stop()
    fun updateProgress(millis: Long)
    fun isRunning(): Boolean
    fun seekTo(position: Int)
}