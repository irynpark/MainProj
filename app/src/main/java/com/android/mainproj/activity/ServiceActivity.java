package com.android.mainproj.activity;

import static com.android.mainproj.service.LocalMusicService.FLAG_MUSIC_STOP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;
import com.android.mainproj.service.LocalMusicService;

/*
서비스는 특정 액티비티와 관계없이 백그라운드에 동작 할 수 있는 컴포넌트이다.
액티비티를 종료해도 지속적으로 동작해야하는 작업의 경우 서비스로 등록을 한다.
서비스는 두가지 서비스로 나뉘는데 로컬 서비스와 원격 서비스이다.
로컬 서비스는 onCreate -> onStartCommand -> onResume -> 서비스 실행 -> 서비스 중단 -> onDestroy -> 서비스 종료
원격 서비스는 onCreate -> onBind -> 서비스 실행 -> 서비스 중단 -> onUnbind -> onDestory -> 서비스 종료
와 같이 동작한다.

원격 서비스를 사용할 경우 AIDL(Android Interface Definition Language)라고 하는
클라이언트와 서비스가 모두 동의한 프로그램 인터페이스를 정의하여 IPC 통신을 하도록한다.
*/
public class ServiceActivity extends AppCompatActivity
{
    private Activity activity;

    private ImageButton btn_service_back;

    private Button btn_local_music_start, btn_local_music_stop;

    private Intent musicIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.activity_service);

            init();

            addListener();
        }
        catch (Exception ex)
        {
            LogService.error(this, ex.getMessage(), ex);
        }
    }

    private void init()
    {
        activity = this;

        btn_service_back = findViewById(R.id.btn_service_back);

        btn_local_music_start = findViewById(R.id.btn_local_music_start);

        btn_local_music_stop = findViewById(R.id.btn_local_music_stop);
    }

    private void addListener()
    {
        btn_service_back.setOnClickListener(listener_back_click);

        btn_local_music_start.setOnClickListener(listener_local_music_start);

        btn_local_music_stop.setOnClickListener(listener_local_music_stop);
    }

    private View.OnClickListener listener_back_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };

    private View.OnClickListener listener_local_music_start = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            checkLocalMusicIntent();

            startService(musicIntent);
        }
    };

    private View.OnClickListener listener_local_music_stop = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            checkLocalMusicIntent();

            LocalMusicService.intent.putExtra(FLAG_MUSIC_STOP, true);

            stopService(musicIntent);
        }
    };

    private void checkLocalMusicIntent()
    {
        if(LocalMusicService.intent == null)
        {
            musicIntent = new Intent(LocalMusicService.ACTION_NAME);
            musicIntent.setPackage(LocalMusicService.PACKAGE_NAME);
        }
        else
        {
            musicIntent = LocalMusicService.intent;
        }
    }
}