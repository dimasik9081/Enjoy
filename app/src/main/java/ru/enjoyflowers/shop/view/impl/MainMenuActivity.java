package ru.enjoyflowers.shop.view.impl;

import android.os.Bundle;

import ru.enjoyflowers.shop.R;
import ru.enjoyflowers.shop.presenter.IMainMenuPresenter;
import ru.enjoyflowers.shop.test.TestMainMenuPresenter;
import ru.enjoyflowers.shop.view.IMainMenuNavigator;
import ru.enjoyflowers.shop.view.IMainMenuView;

public class MainMenuActivity extends BaseActivity implements IMainMenuView,IMainMenuNavigator {

    private IMainMenuPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        presenter = new TestMainMenuPresenter(this);
    }
}
