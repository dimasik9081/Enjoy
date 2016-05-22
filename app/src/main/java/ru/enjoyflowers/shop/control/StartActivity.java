package ru.enjoyflowers.shop.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.enjoyflowers.shop.R;
import ru.enjoyflowers.shop.server.DataLoader;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataLoader dataLoader = new DataLoader();
                dataLoader.load();
            }
        }).start();

    }
}
