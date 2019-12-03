package com.Team3_6.kifu;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import org.chat21.android.core.ChatManager;

public class AppContext extends Application {
    private static final String TAG = AppContext.class.getName();

    private static AppContext instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initChatSDK();
    }

    private void initChatSDK() {
        /*ChatManager.Configuration mChatConfiguration =
                new ChatManager.Configuration.Builder(<APP_ID>)
                    .firebaseUrl(<FIREBASE_DATABASE_URL>)
                    .storageBucket(<STORAGE_BUCKET>)
                    .build();

        if (DummyDataManager.getLoggedUser() != null) {
            ChatManager.start(<CONTEXT>, mChatConfiguration, <LOGGED_USER>);
            Log.i(TAG, "chat has been initialized with success");
        } else {
            Log.w(TAG, "chat can't be initialized because chatUser is null");
        }*/
    }
}