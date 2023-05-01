package com.example.picturevote;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//레이아웃 지정


        getSupportActionBar().setDisplayShowHomeEnabled(true);//홈버튼 표시
        getSupportActionBar().setIcon(R.drawable.image10);// 액션바 아이콘 지정
        setTitle("명화 선호도 투표");//액션바 제목

        final int voteCount[] = new int[9];//이미지 버튼을 저장할 배열
        for (int i = 0; i < 9; i++)//배열 초기화
            voteCount[i] = 0;


        ImageView image[] = new ImageView[9];


        Integer imageId[] = { R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5,
                R.id.iv6, R.id.iv7, R.id.iv8, R.id.iv9 };

        final String imgName[] = { "그림1", "그림2", "그림3", "그림4", "그림5", "그림6", "그림7", "그림8", "그림9" };

        for (int i = 0; i < imageId.length; i++) {
            final int index;
            index = i;

            image[index] = (ImageView) findViewById(imageId[index]);


            image[index].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    voteCount[index]++;
                    Toast.makeText(getApplicationContext(),
                            imgName[index] + ": 총 " + voteCount[index] + " 표",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        Button btnFinish = (Button) findViewById(R.id.btnResult);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("VoteCount", voteCount);
                intent.putExtra("ImageName", imgName);
                startActivity(intent);
            }
        });

    }
}