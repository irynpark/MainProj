package com.android.mainproj.lifecycle;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.android.mainproj.log.LogService;

public class LoginLifeCycle implements DefaultLifecycleObserver
{
    @Override
    public void onStart(@NonNull LifecycleOwner owner)
    {
        LogService.debug((Activity)owner, "onStart 실행");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner)
    {
        LogService.debug((Activity)owner, "onResume 실행");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner)
    {
        LogService.debug((Activity)owner, "onPause 실행");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner)
    {
        LogService.debug((Activity)owner, "onStop 실행");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner)
    {
        LogService.debug((Activity)owner, "onDestroy 실행");
    }

}
