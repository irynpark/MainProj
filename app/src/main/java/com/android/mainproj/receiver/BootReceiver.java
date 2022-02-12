package com.android.mainproj.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.mainproj.activity.BroadCastReceiverActivity;

public class BootReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent i = new Intent(context, BroadCastReceiverActivity.class);

        //액티비티 아닌 곳에서 화면 띄우려 할때
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }
}
