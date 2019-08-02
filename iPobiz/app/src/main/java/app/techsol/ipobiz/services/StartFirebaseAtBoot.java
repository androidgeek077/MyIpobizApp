package app.techsol.ipobiz.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartFirebaseAtBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,FirebaseService.class);
        context.startService(i);
    }
}
