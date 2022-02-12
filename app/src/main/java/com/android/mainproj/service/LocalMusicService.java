package com.android.mainproj.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.android.mainproj.R;
import com.android.mainproj.receiver.MusicAlarmReceiver;

import java.util.Calendar;

public class LocalMusicService extends Service
{
    public static Intent intent;

    private static MediaPlayer mediaPlayer = null;

    public static final String ACTION_NAME = "com.android.service.MUSIC";

    public static final String PACKAGE_NAME = "com.android.mainproj";

    public static final String FLAG_MUSIC_STOP = "MUSIC_EXIT_FLAG";

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        if(mediaPlayer == null)
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.tension);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        this.intent = intent;

        if(mediaPlayer != null && mediaPlayer.isPlaying() == false)
        {
            mediaPlayer.start();
        }

        // START_STICKY : 서비스가 강제종료되었을 경우 서비스를 재시작 하도록 설정
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if(intent.getBooleanExtra(FLAG_MUSIC_STOP, false))
        {
            if(mediaPlayer != null)
            {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
        else
        {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 1);
            Intent intent = new Intent(this, MusicAlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
    }
}
