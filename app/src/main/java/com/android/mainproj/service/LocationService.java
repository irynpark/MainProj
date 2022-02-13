package com.android.mainproj.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.mainproj.R;
import com.android.mainproj.log.LogService;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service
{
	public static final String ACTION_NAME = "com.android.service.LOCATION";

	public static Intent intent;

	private static LocationRequest locationRequest = null;

	private final int LOCATION_SERVICE_ID = 101;

	private NotificationManager notificationManager;

	private NotificationCompat.Builder builder;

	@Nullable
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		LocationService.intent = intent;

		if(locationRequest != null)
		{
			startLocationBackService();
		}

		return START_STICKY;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();

		stopLocationBackService();
	}

	@SuppressLint("MissingPermission")
	private void startLocationBackService()
	{
		locationRequest.setInterval(1000);

		locationRequest.setFastestInterval(1000);

		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

		builder = getDefaultBuilder();

		startForeground(LOCATION_SERVICE_ID, builder.build());
	}

	private final LocationCallback locationCallback = new LocationCallback()
	{
		@Override
		public void onLocationResult(@NonNull LocationResult locationResult)
		{
			super.onLocationResult(locationResult);

			double latitude = locationResult.getLastLocation().getLatitude();

			double longitude = locationResult.getLastLocation().getLongitude();

			Log.i(this.getClass().getName(), "위도 : " + latitude + "경도 : " + longitude);
		}
	};

	private void stopLocationBackService()
	{
		LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);

		stopForeground(true);
	}

	private NotificationCompat.Builder getDefaultBuilder()
	{
		String channelID = "loc_notification_channel";

		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
			if(notificationManager != null && notificationManager.getNotificationChannel(channelID) == null)
			{
				NotificationChannel notifcationChannel = new NotificationChannel
						(
								channelID,
								"location Notification Channel",
								NotificationManager.IMPORTANCE_NONE
						);

				notifcationChannel.setDescription("지도 알림 채널");
				notifcationChannel.setSound(null, null);
				notifcationChannel.setShowBadge(false);
				notifcationChannel.setVibrationPattern(new long[]{0});
				notifcationChannel.enableVibration(true);

				notificationManager.createNotificationChannel(notifcationChannel);
			}
		}

		Intent resultIntent = new Intent();

		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID);

		builder.setSmallIcon(R.mipmap.ic_launcher);

		builder.setContentText("맵");

		builder.setDefaults(NotificationCompat.DEFAULT_SOUND);

		builder.setVibrate(new long[]{0});

		builder.setOnlyAlertOnce(true);

		builder.setContentText("맵 정보를 호출중입니다.");

		builder.setContentIntent(pendingIntent);

		builder.setAutoCancel(false);

		builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

		return builder;
	}
}
