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
        if (remoteMessage.getNotification() != null) {

        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}
