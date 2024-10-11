package com.example.umc_android_study;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment_home.xml을 인플레이트
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Edge-to-Edge 설정
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            // WindowInsetsCompat로 변환하지 않고 바로 Insets를 사용
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // 패딩 설정
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });



        // 각 ImageView를 찾아서 클릭 이벤트 설정
        ImageView imageView = view.findViewById(R.id.imageView);
        ImageView imageView2 = view.findViewById(R.id.imageView2);
        ImageView imageView3 = view.findViewById(R.id.imageView3);
        ImageView imageView4 = view.findViewById(R.id.imageView4);
        ImageView imageView5 = view.findViewById(R.id.imageView5);

        // ImageView 클릭 이벤트
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), nextpage.class);
            startActivity(intent);
        });

        imageView2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), nextpage.class);
            startActivity(intent);
        });

        imageView3.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), nextpage.class);
            startActivity(intent);
        });

        imageView4.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), nextpage.class);
            startActivity(intent);
        });

        imageView5.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), nextpage.class);
            startActivity(intent);
        });
    }
}
