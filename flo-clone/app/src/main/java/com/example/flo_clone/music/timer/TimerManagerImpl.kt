package com.example.flo_clone.music.timer

import android.util.Log

class TimerManagerImpl(
    private val playTime: Int,
    private val onUpdate: (progress: Int) -> Unit
) : TimerManager {
    private var isPlaying = false
    private var millis: Long = 0L
    private var thread: Thread? = null

    override fun start() {
        isPlaying = true
        thread = Thread {
            try {
                while (isPlaying && millis < playTime * 1000) {
                    Thread.sleep(50)
                    millis += 50
                    onUpdate(((millis / playTime) * 100).toInt())
                }
            } catch (e: InterruptedException) {
                Log.d("TimerManager", "Timer 스레드 종료...")
            }
        }.apply { start() }
    }

    override fun pause() {
        isPlaying = false
    }

    override fun stop() {
        isPlaying = false
        thread?.interrupt()
        millis = 0L
    }

    override fun updateProgress(millis: Long) {
        this.millis = millis
    }

    override fun isRunning(): Boolean = thread?.isAlive ?: false

    override fun seekTo(position: Int) {
        millis = (position * playTime / 100).toLong()
    }
}