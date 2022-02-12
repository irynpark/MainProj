package com.android.mainproj.log;

import android.app.Activity;
import android.app.Service;
import android.util.Log;

public class LogService
{
    public static void verbose(Activity activity, String msg)
    {
        Log.v(activity.getPackageName() + " [" + activity.getClass().getSimpleName() + "]", msg);
    }

    public static void debug(Activity activity, String msg)
    {
        Log.d(activity.getPackageName() + " [" + activity.getClass().getSimpleName() + "]", msg);
    }

    public static void info(Activity activity, String msg)
    {
        Log.i(activity.getPackageName() + " [" + activity.getClass().getSimpleName() + "]", msg);
    }

    public static void warn(Activity activity, String msg)
    {
        Log.w(activity.getPackageName() + " [" + activity.getClass().getSimpleName() + "]", msg);
    }

    public static void warn(Activity activity, String msg, Throwable ex)
    {
        Log.w(activity.getPackageName() + " [" + activity.getClass().getSimpleName() + "]", msg, ex);
    }

    public static void error(Activity activity, String msg)
    {
        Log.e(activity.getPackageName() + " [" + activity.getClass().getSimpleName() + "]", msg);
    }

    public static void error(Activity activity, String msg, Throwable ex)
    {
        Log.e(activity.getPackageName() + " [" + activity.getClass().getSimpleName() + "]", msg, ex);
    }

    public static void verbose(Service service, String msg)
    {
        Log.v(service.getPackageName() + " [" + service.getClass().getSimpleName() + "]", msg);
    }

    public static void debug(Service service, String msg)
    {
        Log.d(service.getPackageName() + " [" + service.getClass().getSimpleName() + "]", msg);
    }

    public static void info(Service service, String msg)
    {
        Log.i(service.getPackageName() + " [" + service.getClass().getSimpleName() + "]", msg);
    }

    public static void warn(Service service, String msg)
    {
        Log.w(service.getPackageName() + " [" + service.getClass().getSimpleName() + "]", msg);
    }

    public static void warn(Service service, String msg, Throwable ex)
    {
        Log.w(service.getPackageName() + " [" + service.getClass().getSimpleName() + "]", msg, ex);
    }

    public static void error(Service service, String msg)
    {
        Log.e(service.getPackageName() + " [" + service.getClass().getSimpleName() + "]", msg);
    }

    public static void error(Service service, String msg, Throwable ex)
    {
        Log.e(service.getPackageName() + " [" + service.getClass().getSimpleName() + "]", msg, ex);
    }
}
