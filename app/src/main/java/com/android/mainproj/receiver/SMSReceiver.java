package com.android.mainproj.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.android.mainproj.log.LogService;

public class SMSReceiver extends BroadcastReceiver
{
    public static final String ACTION_RECEIVE_COMPLETE = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            String sender = "";
            String message = "";

            String action = intent.getAction();



            if (action.equals(ACTION_RECEIVE_COMPLETE))
            {
                Bundle bundle = intent.getExtras();

                if (bundle != null)
                {
                    Object[] pdus = (Object[]) bundle.get("pdus");

                    String format = bundle.getString("format");

                    for (Object pdu : pdus)
                    {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdu, format);
                        message += smsMessage.getMessageBody();

                        if (sender.equals(""))
                        {
                            sender = smsMessage.getOriginatingAddress();
                        }

                    }
                    Toast.makeText(context, sender + " : " + message, Toast.LENGTH_SHORT).show();

                    // 현재 수신한 브로드캐스트를 추가하여 다른 브로드캐스트 리시버가 더 이상
                    // 같은 브로드캐스트를 수신할수 없도록 설정
                    abortBroadcast();
                }
            }
        }
        catch (Exception ex)
        {
            LogService.error((Activity) context, ex.getMessage(), ex);
        }
    }
}
