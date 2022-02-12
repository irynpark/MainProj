package com.android.mainproj.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseCloudMessagingService extends FirebaseMessagingService
{
    @Override
    public void onNewToken(@NonNull String token)
    {
        super.onNewToken(token);

        // 앱 내 토큰이 없을 시 내부적으로 FCM 서버에 토큰 요청을 한 뒤
        // 토큰 전달받았을 경우 onNewToken 콜백 함수가 호출된다.
        // 기본적으로 보통 이곳에서 전달받은 토큰 값을 자체 구축한 서버에다가 전달하여 저장한다.
        // 앱 삭제 시 토큰 값이 삭제되므로 토큰값을 확인하지 못하였다면 삭제했다가 다시 설치면 됨.
        LogService.info(this, "token : " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage != null && remoteMessage.getData().size() > 0)
        {
            sendNotification(remoteMessage);
        }
    }

    private void sendNotification(RemoteMessage remoteMessage)
    {
        String title = remoteMessage.getData().get("title");

        String message = remoteMessage.getData().get("message");

        final String CHANNEL_ID = "FCM_ChannelID";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            final String CHANNEL_NAME = "FCM_CHANNEL";
            final String CHANNEL_DESCRIPTION = "FCM Receive Channel";
            final int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            notificationChannel.setDescription(CHANNEL_DESCRIPTION);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100,200,100,200});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setAutoCancel(true);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(message);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
        {
            builder.setVibrate(new long[]{500,500});
        }

        notificationManager.notify(0, builder.build());

    }
}
