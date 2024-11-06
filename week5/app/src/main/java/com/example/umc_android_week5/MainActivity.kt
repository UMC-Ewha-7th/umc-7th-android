package com.example.umc_android_week5

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var editTextMemo: EditText? = null
    private var memoText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextMemo = findViewById(R.id.editTextMemo)
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        // 버튼 클릭 시 DisplayActivity로 이동
        buttonSave.setOnClickListener { v: View? ->
            val intent =
                Intent(
                    this@MainActivity,
                    DisplayActivity::class.java
                )
            intent.putExtra("memo", editTextMemo.getText().toString())
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!memoText.isEmpty()) {
            editTextMemo!!.setText(memoText)
        }
    }

    override fun onPause() {
        super.onPause()
        memoText = editTextMemo!!.text.toString()
    }

    override fun onRestart() {
        super.onRestart()
        AlertDialog.Builder(this)
            .setTitle("다시 작성하시겠습니까?")
            .setMessage("이전에 작성한 내용을 삭제하고 새로 작성하시겠습니까?")
            .setPositiveButton("예") { dialog: DialogInterface?, which: Int ->
                memoText = ""
                editTextMemo!!.setText("")
            }
            .setNegativeButton("아니요", null)
            .show()
    }
}