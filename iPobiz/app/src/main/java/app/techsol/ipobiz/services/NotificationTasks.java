package app.techsol.ipobiz.services;

import android.content.Context;
import android.widget.Toast;

public class NotificationTasks {

    public static final String IGNORE_NOTIFICATION="ignore-notification";
    public static final String ACTION_ON_NOTIFICATION="action-on-notification";
    public static final String CREATE_NOTIFICATION_ON_TASK="create-notification-on-task";

    public static void execTask(Context context, String action){
        if (IGNORE_NOTIFICATION.equals(action)){
            NotificationUtils.clearAllNotifications(context);
        }else if (ACTION_ON_NOTIFICATION.equals(action)){
            doSomeTask(context);
        }else if (CREATE_NOTIFICATION_ON_TASK.equals(action)){
            issueTaskPerformAndNotify(context);
        }
    }

    public static void createNewNotification(Context context,String title, String text){
      NotificationUtils.createNewNotification(context,title,text);

    }
    private static void issueTaskPerformAndNotify(Context context) {
        //do work for notification generate and pass data to generate task on upcomming data(do work for generate notification)
       // NotificationUtils.createNewNotification(context);
    }

    private static void doSomeTask(Context context) {
        // here is action perform on notification click to update data

        NotificationUtils.clearAllNotifications(context);
    }
}
