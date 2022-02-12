package com.android.mainproj.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.mainproj.log.LogService;

public class SMSSendReceiver extends BroadcastReceiver
{
    public static final String ACTION_SEND_COMPLETE = "com.android.receiver.SEND_SMS";

    public static final String ACTION_DELIVERY_COMPLETE = "com.android.receiver.DELIVERY_SMS";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(ACTION_SEND_COMPLETE))
        {
            if (getResultCode() == Activity.RESULT_OK)
            {
                LogService.info((Activity) context, " 메세지 전송 성공!");
            }
            else
            {
                LogService.info((Activity) context, " 메세지 전송 실패!");
            }
        }
        else if (intent.getAction().equals(ACTION_DELIVERY_COMPLETE))
        {
            if (getResultCode() == Activity.RESULT_OK)
            {
                LogService.info((Activity) context, " 메세지 전달 성공!");
            }
            else if(getResultCode() == Activity.RESULT_CANCELED)
            {
                LogService.info((Activity) context, " 메세지 전달 실패!");
            }
            else
            {

            }
        }
    }
}
