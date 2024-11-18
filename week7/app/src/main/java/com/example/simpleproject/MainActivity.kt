package com.example.simpleproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var db: ProfileDatabase
    private var list = ArrayList<Profile>()
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ProfileDatabase.getInstance(this)!!
        Thread {
            val savedContacts = db.profileDao().getAll()
            if(savedContacts.isNotEmpty()) {
                list.addAll(savedContacts)
            }
        }.start()

        binding.mainBtn.setOnClickListener {
            Thread{
                val profile = Profile("name", "24", "010-1234-5678")
                list.add(profile)
                db.profileDao().insert(profile)

                val savedContacts = db.profileDao().getAll()
                Log.d("Inserted Primary Key", savedContacts[savedContacts.size - 1].id.toString())
            }.start()

            adapter.notifyDataSetChanged()
        }

        adapter = CustomAdapter(list, this)
        binding.mainProfileLv.adapter = adapter

    }
}