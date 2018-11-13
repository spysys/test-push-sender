import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

public class TestMain {
    public static void main(String args[]) {
        FileInputStream serviceAccount =
                null;
        FirebaseOptions options = null;

        try {
            //секретный ключ(файл генерируется в firebase)
            serviceAccount = new FileInputStream("/Users/andrey/Development/tuts/firebase/mts-test-push-firebase-adminsdk-r09jw-1fee401b4c.json");


            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://mts-test-push.firebaseio.com")
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp defaultApp = FirebaseApp.initializeApp(options);
        // This registration token comes from the client FCM SDKs.
        FirebaseAuth defaultAuth = FirebaseAuth.getInstance(defaultApp);
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance(defaultApp);

// ... or use the equivalent shorthand notation
        defaultAuth = FirebaseAuth.getInstance();
        defaultDatabase = FirebaseDatabase.getInstance();

        //токен мобильного приложения
        String registrationToken = "dyGTKLQ2n5Y:APA91bG1_jkgh_U5pDU8OnzSUV7g-uISnbJz7K2RTX2S6_UTRRiGL82i2Y3h7AYGbxDHJhiz9qq3bx-8HdXw7VlWGyQIl34sk6YO9TvQbo_HqRvz79LjB6MeCvIEmZD05YDTRDRzBdsI";

// See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setToken(registrationToken)
                .build();

// Send a message to the device corresponding to the provided
// registration token.
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
// Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
    }
}
