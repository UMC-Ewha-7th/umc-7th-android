package com.example.flo_clone

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.flo_clone.databinding.ActivityMainBinding
import com.example.flo_clone.home.ui.HomeFragment
import com.example.flo_clone.locker.ui.LockerFragment
import com.example.flo_clone.look.ui.LookFragment
import com.example.flo_clone.music.data.Song
import com.example.flo_clone.music.service.MusicService
import com.example.flo_clone.music.ui.SongActivity
import com.example.flo_clone.search.ui.SearchFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var song: Song
    private var musicService: MusicService? = null
    private var isServiceBound = false

    private val progressReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == MusicService.ACTION_UPDATE_PROGRESS) {
                val progress = intent.getIntExtra(MusicService.EXTRA_PROGRESS, 0)
                binding.mainPlayerSeekbar.progress = progress
            }
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicServiceBinder
            musicService = binder.getService()
            isServiceBound = true

            initSong()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceIntent = Intent(this, MusicService::class.java)
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE)

        initBottomNavigation()
        initClickListener()
        ContextCompat.registerReceiver(
            this,
            progressReceiver,
            IntentFilter(MusicService.ACTION_UPDATE_PROGRESS),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onResume() {
        super.onResume()
        ContextCompat.registerReceiver(
            this,
            progressReceiver,
            IntentFilter(MusicService.ACTION_UPDATE_PROGRESS),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        musicService?.getCurSong()?.let {
            song = it
            setMiniPlayer(song)
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(progressReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            unbindService(serviceConnection)
            musicService = null
            isServiceBound = false
        }
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, HomeFragment())
            .commitAllowingStateLoss()

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, HomeFragment())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.navigation_look -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, LookFragment())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.navigation_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, SearchFragment())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.navigation_locker -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, LockerFragment())
                        .commitAllowingStateLoss()
                    true
                }

                else -> false
            }
        }
    }

    private fun initSong() {
        val spf = getSharedPreferences("song", Context.MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        musicService?.setCurSong(songId, false)
        song = musicService?.getCurSong() ?: return
        setMiniPlayer(song)
    }

    private fun initClickListener() {
        binding.mainPlayerCl.setOnClickListener {
            musicService?.updateSongProgress()
            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }

        binding.mainPlayBtn.setOnClickListener {
            if (isServiceBound) {
                musicService?.play()
                setPlayerStatus(true)
            }
        }

        binding.mainPauseBtn.setOnClickListener {
            if (isServiceBound) {
                musicService?.pause()
                setPlayerStatus(false)
            }
        }

        binding.mainPreviousBtn.setOnClickListener {
            moveSong(-1)

        }

        binding.mainNextBtn.setOnClickListener {
            moveSong(1)
        }
    }

    private fun moveSong(direct: Int) {
        musicService?.moveSong(direct)?.let {
            song = it
            setMiniPlayer(song)
        }
    }

    fun setSongsAndPlay(songList: ArrayList<Song>) {
        musicService?.setSongs(songList)
        musicService?.seekTo(0)
        musicService?.setCurSong(songList[0].id, true)
        setMiniPlayer(songList[0])
        song = songList[0]
    }

    fun showBottomSheet(isActive: Boolean) {
        if (isActive) {
            binding.mainPlayerCl.visibility = View.GONE
            binding.navView.visibility = View.GONE
        } else {
            binding.mainPlayerCl.visibility = View.VISIBLE
            binding.navView.visibility = View.VISIBLE
        }
    }

    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainPlayerSeekbar.progress = (song.second * 1000 / song.playTime) * 100

        setPlayerStatus(song.isPlaying)
        Log.d("MainActivity", "setMiniPlayer: $song")
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying

        if (isPlaying) {
            binding.mainPlayBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
        } else {
            binding.mainPlayBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
        }
    }
}