package com.example.memo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.memo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var memo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.confirmBtn.setOnClickListener {
            val intent = Intent(this, ConfirmActivity::class.java)
            intent.putExtra("memo", binding.memoEt.text.toString())
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        memo?.let {
            binding.memoEt.setText(it)
        }
    }

    override fun onPause() {
        super.onPause()
        memo = binding.memoEt.text.toString()
    }

    override fun onRestart() {
        super.onRestart()
        AlertDialog.Builder(this)
            .setTitle("메모 재작성")
            .setMessage("이어서 작성하시겠습니까?")
            .setPositiveButton("네") { _, _ ->
                binding.memoEt.setText(memo)
            }
            .setNegativeButton("아니오") { _, _ ->
                memo = null
                binding.memoEt.setText("") }
            .show()
    }
}