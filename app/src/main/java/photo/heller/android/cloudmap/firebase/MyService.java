package photo.heller.android.cloudmap.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {

    private final String TAG = MyService.class.getSimpleName();

    public MyService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "AEH onMessageReceived: from " + remoteMessage.getFrom());
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "AEH onMessageReceived: " + remoteMessage.getNotification().getBody());
        }
    }
}
