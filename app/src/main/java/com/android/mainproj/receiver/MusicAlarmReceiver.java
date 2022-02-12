package com.android.mainproj.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.android.mainproj.service.LocalMusicService;
import com.android.mainproj.service.RestartMusicService;

public class MusicAlarmReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Intent in = new Intent(context, RestartMusicService.class);
            context.startService(in);
        }
        else
        {
            Intent in = new Intent(context, LocalMusicService.class);
            context.startService(in);
        }


    }
}
