package app.techsol.ipobiz.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import app.techsol.ipobiz.PostModel;


public class FirebaseService extends Service {
    private DatabaseReference mConversationRef;
    private ChildEventListener getAllConversationChildListener;

    private ChildEventListener getAllUsersListener;

    List<PostModel> PostList;

    @Override
    public void onCreate() {
        super.onCreate();

        PostList=new ArrayList<>();
        mConversationRef= FirebaseDatabase.getInstance().getReference().child("Post");


        getAllConversationChildListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final PostModel post=dataSnapshot.getValue(PostModel.class);

                //messageList.add(message);

                boolean foregroundCheck =false;
                try {
                    foregroundCheck = new ForegroundCheckTask().execute(getApplicationContext()).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!foregroundCheck)
                    NotificationTasks.createNewNotification(FirebaseService.this,"New Post",post.getPosttitle());




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mConversationRef.addChildEventListener(getAllConversationChildListener);






            //NotificationUtils.createNewNotification(this,"Waleed","Hello! how are you?");
       // Toast.makeText(this, "backgroud toast", Toast.LENGTH_SHORT).show();
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String action = intent.getAction();
        NotificationTasks.execTask(this,action);
        return null;
    }

    class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... params) {
            final Context context = params[0].getApplicationContext();
            return isAppOnForeground(context);
        }

        private boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }

}
