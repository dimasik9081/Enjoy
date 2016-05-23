package ru.enjoyflowers.shop.control;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.enjoyflowers.shop.R;
import ru.enjoyflowers.shop.server.DataLoader;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_wait_screen);

        final StartActivity startActivity = this;
        final Handler h = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                startActivity.setContentView(R.layout.activity_menu);;
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
