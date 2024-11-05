package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.DialogInterface
import android.content.Intent
import android.text.Editable
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var data: String? = null
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        viewBinding.button.setOnClickListener {

            var intent = Intent(this, SecondActivity::class.java)//SecondActivity로 전환, intent는 자바 클래스 타입 요구->.java
            intent.putExtra("data", viewBinding.edit.text.toString())//EditText의 입력 값을 text속성으로 받고 toString() 으로 문자열로 변환
            startActivity(intent)
        }

    }

    override fun onResume(){
        super.onResume()
        if(data!=null){
            viewBinding.edit.setText(data)
        }
    }


    override fun onPause(){
        super.onPause()

            data = viewBinding.edit.text.toString()

    }

    override fun onRestart() {
        super.onRestart()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("임시 저장")
            .setMessage("이어서 작성할까요?")
            .setPositiveButton("예",
                DialogInterface.OnClickListener { dialog, id ->
                    viewBinding.edit.setText(data)
                    if(data == null){
                        viewBinding.edit.setText("")
                    }
                })
            .setNegativeButton("아니요",
                DialogInterface.OnClickListener { dialog, id ->
                    viewBinding.edit.setText("")
                })
        builder.show()

    }
}