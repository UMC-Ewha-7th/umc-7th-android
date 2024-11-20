package com.example.flo_clone.music.ui

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.flo_clone.R
import com.example.flo_clone.databinding.ActivitySongBinding
import com.example.flo_clone.music.data.Song
import com.example.flo_clone.music.data.SongDatabase
import com.example.flo_clone.music.service.MusicService
import java.util.Locale

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    private lateinit var song: Song
    private var musicService: MusicService? = null
    private var isServiceBound = false

    private val progressReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == MusicService.ACTION_UPDATE_PROGRESS) {
                val progress = intent.getIntExtra(MusicService.EXTRA_PROGRESS, 0)
                binding.songProgressSb.progress = progress
                binding.songStartTimeTv.text = formatTime(progress * song.playTime / 100000)
            }
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicServiceBinder
            musicService = binder.getService()
            isServiceBound = true

            musicService?.fetchSongs()

            musicService?.getCurSong()?.let {
                song = it
                setPlayer(song)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceIntent = Intent(this, MusicService::class.java)
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE)

        initClickListener()
        ContextCompat.registerReceiver(
            this,
            progressReceiver,
            IntentFilter(MusicService.ACTION_UPDATE_PROGRESS),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onPause() {
        super.onPause()

        // song 정보 저장
        musicService?.updateSong()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            unbindService(serviceConnection)
            musicService = null
            isServiceBound = false
        }
    }

    private fun initClickListener() {
        binding.songDownIb.setOnClickListener {
            musicService?.updateSong()
            finish()
        }

        binding.songPlayIv.setOnClickListener {
            if (isServiceBound) {
                musicService?.play()
                setPlayerStatus(true)
            }
        }

        binding.songPauseIv.setOnClickListener {
            if (isServiceBound) {
                musicService?.pause()
                setPlayerStatus(false)
            }
        }

        binding.songNextIv.setOnClickListener {
            moveSong(1)
        }

        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }

        binding.songProgressSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicService?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                musicService?.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                musicService?.play()
            }
        })

        binding.songLikeIv.setOnClickListener {
            setLike(!song.isLike)
        }
    }

    private fun setLike(isLike: Boolean) {
        song.isLike = isLike
        if (isLike) {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        Thread {
            val songDB = SongDatabase.getInstance(this)!!
            songDB.songDao().updateLikeById(song.id, song.isLike)
        }.start()
    }

    private fun moveSong(direct: Int) {
        musicService?.moveSong(direct)?.let {
            song = it
            setPlayer(song)
        }
    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songAlbumIv.setImageResource(song.coverImg!!)

        binding.songStartTimeTv.text = formatTime(song.second)
        binding.songEndTimeTv.text = formatTime(song.playTime)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime) * 100

        if (song.isLike) {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying

        if (isPlaying) {
            binding.songPlayIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        } else {
            binding.songPlayIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    private fun formatTime(second: Int): String {
        return String.format(Locale.KOREA, "%02d:%02d", second / 60, second % 60)
    }
}