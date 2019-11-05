package com.example.moodlight;

import android.app.Activity;
import android.widget.Toast;

public class BackPressCloseHandler {
    private long backKeyPressedTime = 0;    // '뒤로'버튼 처음 클릭시간
    private long TIME_INTERVAL = 2000;      // 첫번째 버튼 클릭과 두번째 버튼 클릭 사이의 종료를 위한 시간차 정의
    private Toast toast;
    private Activity activity;

    public BackPressCloseHandler(Activity v){
        this.activity = v;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + TIME_INTERVAL) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(activity, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else {
            toast.cancel();
            activity.finish();
        }
    }
}
