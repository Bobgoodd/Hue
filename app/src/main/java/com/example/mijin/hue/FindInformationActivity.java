package com.example.mijin.hue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by mijin on 2017-11-15.
 */

public class FindInformationActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_information);

        Button id = (Button) findViewById(R.id.id);
        Button pw = (Button) findViewById(R.id.password);

        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindInformationActivity.this, FindIdActivity.class);
                startActivity(intent);
            }
        });

        pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindInformationActivity.this, FindPwActivity.class);
                startActivity(intent);
            }
        });
    }
}
