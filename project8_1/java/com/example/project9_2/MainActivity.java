package com.example.project9_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 이미지를 확대, 축소, 회전, 밝기 조절, 색상 조절할 수 있는 버튼 객체와 MyGraphicView 객체 생성
    ImageButton ibZoomin, ibZoomout, ibRotate, ibBright, ibDark, ibGray;
    MyGraphicView graphicView;

    // 초기값 지정
    static float scaleX = 1, scaleY = 1;
    static float angle = 0;
    static float color = 1;
    static float satur = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("미니 포토샵");

        // LinearLayout 객체 생성 후 그 위에 MyGraphicView 객체 추가
        LinearLayout pictureLayout = (LinearLayout)findViewById(R.id.pictureLayout);
        graphicView = (MyGraphicView) new MyGraphicView(this);
        pictureLayout.addView(graphicView);

        // 버튼 객체에 클릭 이벤트 리스너 등록
        clickIcons();
    }

    private void clickIcons() {
        // Zoom in 버튼에 클릭 이벤트 리스너 등록
        ibZoomin = (ImageButton)findViewById(R.id.ibZoomin);
        ibZoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지를 확대하기 위해 scaleX, scaleY 값 증가
                scaleX += 0.2f;
                scaleY += 0.2f;
                graphicView.invalidate(); // 뷰 다시 그리기
            }
        });

        // Zoom out 버튼에 클릭 이벤트 리스너 등록
        ibZoomout = (ImageButton)findViewById(R.id.ibZoomout);
        ibZoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지를 축소하기 위해 scaleX, scaleY 값 감소
                scaleX -= 0.2f;
                scaleY -= 0.2f;
                graphicView.invalidate(); // 뷰 다시 그리기
            }
        });

        // Rotate 버튼에 클릭 이벤트 리스너 등록
        ibRotate = (ImageButton)findViewById(R.id.ibRotate);
        ibRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지를 회전하기 위해 angle 값 증가
                angle += 20;
                graphicView.invalidate(); // 뷰 다시 그리기
            }
        });

        // Brightness up 버튼과 이벤트 리스너 등록
        ibBright = (ImageButton)findViewById(R.id.ibBright);
        ibBright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 색 밝기 값 증가
                color += 0.2f;
                // 그래픽 뷰 갱신
                graphicView.invalidate();
            }
        });

        // Brightness down 버튼과 이벤트 리스너 등록
        ibDark = (ImageButton)findViewById(R.id.ibDark);
        ibDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 색 밝기 값 감소
                color -= 0.2f;
                // 그래픽 뷰 갱신
                graphicView.invalidate();
            }
        });

// Gray 버튼과 이벤트 리스너 등록
        ibGray = (ImageButton)findViewById(R.id.ibGray);
        ibGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (satur == 0) {
                    // 현재 채도 값이 0인 경우 1로 변경
                    satur = 1;
                }
                else {
                    // 현재 채도 값이 1인 경우 0으로 변경
                    satur = 0;
                }
                // 그래픽 뷰 갱신
                graphicView.invalidate();
            }
        });
    }

    private static class MyGraphicView extends View {
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int cenX = this.getWidth() / 2;  // View의 중심 X 좌표
            int cenY = this.getHeight() / 2;  // View의 중심 Y 좌표
            canvas.scale(scaleX, scaleY, cenX, cenY);  // scaleX, scaleY 비율로 View 크기 조정
            canvas.rotate(angle, cenX, cenY);  // angle만큼 View 회전

            Paint paint = new Paint();  // 그림을 그리기 위한 Paint 객체 생성
            float[] array = {  // 컬러 매트릭스 배열 생성
                    color, 0, 0, 0, 0,
                    0, color, 0, 0, 0,
                    0, 0, color, 0, 0,
                    0, 0, 0, 1, 0
            };
            ColorMatrix cm = new ColorMatrix(array);  // 컬러 매트릭스 객체 생성

            if (satur == 0)  // 만약 satur가 0이면
                cm.setSaturation(satur);  // 컬러 매트릭스의 채도를 0으로 조정

            paint.setColorFilter(new ColorMatrixColorFilter(cm));  // 컬러 필터 설정

            Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.images);  // 이미지 리소스를 비트맵으로 디코딩
            int picX = (this.getWidth() - picture.getWidth()) / 2;  // 이미지를 그릴 X 좌표
            int picY = (this.getHeight() - picture.getHeight()) / 2;  // 이미지를 그릴 Y 좌표
            canvas.drawBitmap(picture, picX, picY, paint);  // 비트맵 이미지를 그림
            picture.recycle();  // 비트맵 메모리 해제
        }
    }
}