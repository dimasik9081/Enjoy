package ru.enjoyflowers.shop.view.impl;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import ru.enjoyflowers.shop.R;
import ru.enjoyflowers.shop.server.DataLoader;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_wait_screen);

        final StartActivity startActivity = this;
        final Handler h = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Intent intent = new Intent(startActivity, MainMenuActivity.class);
                startActivity(intent);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataLoader dataLoader = new DataLoader();
                dataLoader.load();
                h.sendEmptyMessage(1);
            }
        }).start();

    }
}
