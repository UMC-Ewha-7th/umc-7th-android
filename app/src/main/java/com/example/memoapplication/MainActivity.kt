package com.example.memoapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.memoapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private var memo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnMain.setOnClickListener{
            var intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("data", binding.edtMain.text.toString())
            startActivity(intent)
        }

        val create = Toast.makeText(this.applicationContext, "onCreate", Toast.LENGTH_SHORT)
        create.show()
    }

    override fun onStop() {
        super.onStop()
        memo = binding.edtMain.text.toString()
        binding.edtMain.text.clear()

        val stop = Toast.makeText(this.applicationContext, "onStop", Toast.LENGTH_SHORT)
        stop.show()
    }

    override fun onRestart() {
        super.onRestart()
        AlertDialog.Builder(this)
            .setTitle("잠깐!")
            .setMessage("이어서 작성하시겠습니까?")
            .setPositiveButton("예") {dialog, which ->
                binding.edtMain.setText(memo)
            }
            .setNeutralButton("아니오") {dialog, which ->
                binding.edtMain.text.clear()
            }
            .create()
            .show()

        val restart = Toast.makeText(this.applicationContext, "onRestart", Toast.LENGTH_SHORT)
        restart.show()
    }
}