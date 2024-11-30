package com.example.flo_clone

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.flo.SongDatabase
import com.example.flo_clone.databinding.ActivityMainBinding
import com.example.flo_clone.ui.home.HomeFragment
import com.example.flo_clone.ui.locker.LockerFragment
import com.example.flo_clone.ui.look.LookFragment
import com.example.flo_clone.ui.search.SearchFragment
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var song:Song = Song()
    private var gson: Gson = Gson()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                Toast.makeText(this, data?.getStringExtra("title"), Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        inputDummyAlbums()
        initBottomNavigation()


        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getJwt(): String? {
        val spf = this.getSharedPreferences("auth2", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("accessToken","")
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

    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainProgressSb.progress = (song.second*100000)/song.playTime
    }

    @OptIn(UnstableApi::class)
    override fun onStart() {
        super.onStart()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData", null)
//
//        song = if(songJson == null) {
//            Song("Mango Sundive", "Unknown", 0, 60, false, "mango_sundive")
//        } else {
//            gson.fromJson(songJson, Song::class.java)
//        }

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if (songId == 0) {
            songDB.songDao().getSongs(1)
        } else {
            songDB.songDao().getSongs(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)


    }

    @OptIn(UnstableApi::class)
    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)
        val songs = songDB?.songDao()?.getSongs()

        if (songs?.isNotEmpty() == true) return

        songDB?.songDao()?.insert(
            Song(
                "Butter",
                "방탄소년단",
                0,
                235,
                false,
                "mango_sundive",
                R.drawable.img_album_exp2
            )
        )

        songDB?.songDao()?.insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                235,
                false,
                "laststop",
                R.drawable.img_album_exp2
            )
        )

        songDB?.songDao()?.insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                235,
                false,
                "slowlife",
                R.drawable.img_album_exp2
            )
        )

        val _songs = songDB?.songDao()?.getSongs()
        Log.d("DB data", _songs.toString())


    }

    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(this)
        val albums = songDB?.albumDao()?.getAlbums()

        if (albums?.isNotEmpty() == true) return

        songDB?.albumDao()?.insert(
            Album(
                0,
                "Butter",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp2
            )
        )

        songDB?.albumDao()?.insert(
            Album(
                1,
                "IU 5th Album 'LILAC",
                "아이유 (IU)",
                R.drawable.img_album_exp2
            )
        )

        songDB?.albumDao()?.insert(
            Album(
                2,
                "iScreaM Vol.10 : Next Level Remixes",
                "에스파 (AESPA)",
                R.drawable.img_album_exp2
            )
        )

        val _songs = songDB?.songDao()?.getSongs()



    }


}