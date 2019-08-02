package app.techsol.ipobiz.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import app.techsol.ipobiz.R;
import app.techsol.ipobiz.ViewPostActivity;

public class NotificationUtils {

    public static void createNewNotification(Context context,String title, String text){
        NotificationManager notificationManager=(NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(
                    "12",
                    "New O notification",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder builder=new NotificationCompat.Builder(
                context,
                "12")
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                //.addAction(performNotificationAction(context))
                .addAction(ignoreNotificationAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O ){

            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        }

        notificationManager.notify(12,builder.build());
    }

    private static NotificationCompat.Action ignoreNotificationAction(Context context){
        Intent ignoreNotificationIntent=new Intent(context,FirebaseService.class);
        ignoreNotificationIntent.setAction(NotificationTasks.IGNORE_NOTIFICATION);

        PendingIntent ignoreNotifiPendingIntent=PendingIntent.getService(
                context,
                11,
                ignoreNotificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action=new NotificationCompat.Action(
                R.drawable.common_google_signin_btn_icon_dark,
                "CLOSE",
                ignoreNotifiPendingIntent
        );

        return action;
    }

    public static NotificationCompat.Action performNotificationAction(Context context){
        Intent ignoreNotificationIntent=new Intent(context,FirebaseService.class);

        ignoreNotificationIntent.setAction(NotificationTasks.ACTION_ON_NOTIFICATION);

        PendingIntent ignoreNotifiPendingIntent=PendingIntent.getService(
                context,
                11,
                ignoreNotificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action=new NotificationCompat.Action(
                R.drawable.ic_launcher_foreground,
                "WATCH NOW",
                ignoreNotifiPendingIntent
        );
        return action;
    }


    private static PendingIntent contentIntent(Context context){
        Intent intent=new Intent(context, ViewPostActivity.class);

        return PendingIntent.getActivity(
                context,
                2,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private static Bitmap largeIcon(Context context){
        Resources res=context.getResources();

        Bitmap largeIcon=BitmapFactory.decodeResource(res, R.drawable.common_google_signin_btn_icon_dark);

        return largeIcon;
    }


    public static void clearAllNotifications(Context context){
        NotificationManager notificationManager=(NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.cancelAll();
    }
}
