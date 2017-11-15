package com.example.mijin.hue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;


/**
 * Created by mijin on 2017-11-15.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
/*
        try{
            Thread.sleep(1500);
        }catch (Exception e){
            e.printStackTrace();
        }
*/

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler() , 3000);

    }

    private class splashhandler implements Runnable{
        public void run() {
            startActivity(new Intent(getApplication(),LoginActivity.class)); // 로딩이 끝난후 이동할 Activity
            SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
        }
    }

}
