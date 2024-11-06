package com.example.umc_android_week5;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TextView textViewDisplayMemo = findViewById(R.id.textViewDisplayMemo);

        // MainActivity로부터 전달받은 메모 내용을 TextView에 표시
        String memo = getIntent().getStringExtra("memo");
        textViewDisplayMemo.setText(memo);
    }
}