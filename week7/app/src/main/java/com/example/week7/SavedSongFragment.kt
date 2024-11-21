package com.example.week7

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SavedSongFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var adapter: SongAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setLayoutManager(LinearLayoutManager(context))

        val db = AppDatabase.getInstance(requireContext())

        // 좋아요 곡 가져오기
        Thread {
            val songDao = db!!.songDao()
            val likedSongs = songDao!!.likedSongs

            // RecyclerView에 데이터 설정
            requireActivity().runOnUiThread {
                adapter = SongAdapter(likedSongs,
                    SongAdapter.OnItemClickListener { song: Song? ->
                        // 좋아요 클릭 이벤트 처리
                        Thread { songDao.updateSong(song) }.start()
                    })
                recyclerView.setAdapter(adapter)
            }
        }.start()
    }
}
