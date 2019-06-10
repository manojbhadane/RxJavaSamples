package com.manojbhadane.rxdemo1.app;

import android.app.Application;

import com.manojbhadane.easyretro.EasyRetro;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EasyRetro.init(this, "https://raw.githubusercontent.com");
    }
}
