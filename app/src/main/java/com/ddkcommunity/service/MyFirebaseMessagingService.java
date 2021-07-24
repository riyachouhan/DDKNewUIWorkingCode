package com.ddkcommunity.service;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.activities.SplashActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONObject;
import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    Context ctx;
    int i = 0;
    int notification_count = 0;
    int CHANNEL_ID = 0;
    JSONObject j;
    String title = "";
    String currentDateandTime;
    private boolean foregroundValue = false;
    private boolean isNotificationShowed = false;
    RemoteMessage.Builder builder1;
    String ride_status = "";
    String notification_type = "";
    String driver_id = "";
    String username = "";
    String  CHANNEL_NAME= "Simplified Coding";
    // Override onMessageReceived() method to extract the
    // title and
    // body from the message passed in FCM
    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage)
    {
        try
        {
          //  Log.d("notifiaction data",remoteMessage.getData().toString());
        //Log.d("notifiaction noti",remoteMessage.getNotification().toString());

        // First case when notifications are received via
        // data event
        // Here, 'title' and 'message' are the assumed names
        // of JSON
        // attributes. Since here we do not have any data
        // payload, This section is commented out. It is
        // here only for reference purposes.
        if(remoteMessage.getData().size()>0)
        {
            try {
              //  JSONObject jsonobje = new JSONObject(remoteMessage.getData().toString());
               // String titlevalue = jsonobje.get("title").toString();
                String titlevalue = "Smart Asset Manager";
               // String messagevalue =jsonobje.get("message").toString();
                String messagevalue =remoteMessage.getData().get("message").toString();
                showNotification(titlevalue, messagevalue);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        // Second case when notification payload is
        // received.
        if (remoteMessage.getNotification() != null) {
            // Since the notification is received directly from
            // FCM, the title and the body can be fetched
            // directly as below.
            try {
               // JSONObject jsonobje = new JSONObject(remoteMessage.getNotification().toString());
               // String titlevalue = jsonobje.get("title").toString();
                String titlevalue = "Smart Asset Manager";
               // String messagevalue = jsonobje.get("message").toString();
                String messagevalue =remoteMessage.getNotification().getBody().toString();
                showNotification(titlevalue, messagevalue);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Method to get the custom Design for the display of
    // notification.

    private RemoteViews getCustomDesign(String title,
                                        String message) {
        RemoteViews remoteViews = new RemoteViews(
                getApplicationContext().getPackageName(),
                R.layout.nav_header_main);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
        remoteViews.setImageViewResource(R.id.icon,
                R.drawable.sam_logo_icon);
        return remoteViews;
    }

    // Method to display the notifications
    public void showNotification(String title,
                                 String message)
    {
        try
        {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
            Intent ii = new Intent(getApplicationContext(), MyFirebaseMessagingService.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, ii, 0);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            //bigText.bigText(notificationsTextDetailMode); //detail mode is the "expanded" notification
            //bigText.setBigContentTitle(notificationTitleDetailMode);
            //bigText.setSummaryText(usuallyAppVersionOrNumberOfNotifications); //small text under notification
            mBuilder.setAutoCancel(false);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher); //notification icon
            mBuilder.setContentTitle(title); //main title
            mBuilder.setContentText(message); //main text when you "haven't expanded" the notification yet
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setStyle(bigText);

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = new NotificationChannel("notify_001",
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
            }
            if (mNotificationManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mNotificationManager.createNotificationChannel(channel);
                }
            }

            if (mNotificationManager != null) {
                mNotificationManager.notify(0, mBuilder.build());
            }

            /*Intent intent
                    = new Intent(this, SplashActivity.class);
            // Assign channel ID
            String channel_id = "notification_channel";
            //creating notification channel if android version is greater than or equals to oreo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                NotificationChannel channel = new NotificationChannel(channel_id, channel_id, NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }
            // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
            // the activities present in the activity stack,
            // on the top of the Activity that is to be launched
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Pass the intent to PendingIntent to start the
            // next Activity
            PendingIntent pendingIntent
                    = PendingIntent.getActivity(
                    this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notification_channel")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            mBuilder.setOngoing(true);
            NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(getApplicationContext());
            mNotificationMgr.notify(11, mBuilder.build());
*/
            //......
        }catch (Exception e)
        {
            e.printStackTrace();
        }
       }

}
/*
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        final Map<String, String> data = remoteMessage.getData();
        System.out.println("====rider=="+remoteMessage.getData());
        System.out.println("====rider=="+remoteMessage.getData());
        */
/*Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String strMain = data.toString();
                    String[] arrSplit = strMain.split("=");
                    String dataStr = arrSplit[1];
                    JSONObject jsonObject = new JSONObject(dataStr);
                  try
                  {
                      notification_type = jsonObject.optString("notification_type");

                  }
                  catch (Exception c)
                  {

                  }

                    if (notification_type.equalsIgnoreCase("chat"))
                    {
                        driver_id = jsonObject.optString("driver_id");
                        username = jsonObject.optString("driver_name");
                        title = jsonObject.optString("message");
                     //   Constant.user_id = jsonObject.optString("sender_id");
                      //  com.ddkcommunity.rider.Constant.driver_id = jsonObject.optString("sender_id");
                        share = SharedPreference.getInstance(getApplicationContext());
                        share.setValue("chat_user_id",driver_id);
                        share.setValue("chat_username",username);
                        share.setValue("chat_activity","false");
                        share.setValue(com.ddkcommunity.rider.Constant.driver_id_key, driver_id);
                        try
                        {
                            Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception c)
                        {
                        }
                    }
                    else
                    {
                        String ride_id = jsonObject.optString("ride_id");
                        String driver_id = jsonObject.optString("driver_id");
                        String profile_img = jsonObject.optString("profile_img");
                        String name = jsonObject.optString("name");
                        String mobile = jsonObject.optString("mobile");
                        String rating = jsonObject.optString("rating");
                        ride_status = jsonObject.optString("ride_status");
                        title = jsonObject.optString("title");
                        String messageStr = jsonObject.optString("message");

                        try
                        {
                            Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();

                        }
                        catch (Exception c)
                        {

                        }





                        System.out.println("ride status======"+ride_status);

                        share = SharedPreference.getInstance(getApplicationContext());
                     //   share.setValue(com.ddkcommunity.rider.Constant.ride_id_key,ride_id);
                        share.setValue(com.ddkcommunity.rider.Constant.driver_id_key,driver_id);
                        share.setValue(com.ddkcommunity.rider.Constant.d_profile,profile_img);
                        share.setValue(com.ddkcommunity.rider.Constant.driver_name_key,name);
                        share.setValue(com.ddkcommunity.rider.Constant.d_mobile,mobile);
                        share.setValue(com.ddkcommunity.rider.Constant.driver_rating_key,rating);
                        share.setValue(com.ddkcommunity.rider.Constant.ride_status_key,ride_status);
                        share.setValue(com.ddkcommunity.rider.Constant.ride_title_key,title);
                    }

                  //  Toast.makeText(ctx, ""+mess, Toast.LENGTH_SHORT).show();
                }
                catch (Exception x)
                {
                    x.printStackTrace();
                }
                Random rnd = new Random();
                i=100 + rnd.nextInt(90000);
                ctx = getApplicationContext();
                SharedPreferences settings1 = PreferenceManager.getDefaultSharedPreferences(ctx);
                Constant.user_id = settings1.getString(Constant.USER_ID, "");
                try
                {
                    notification_count  = Integer.parseInt(settings1.getString("notification", "0"));
                }
                catch (Exception c)
                {
                    c.printStackTrace();
                }
                share.setValue(com.ddkcommunity.rider.Constant.ride_status_key,ride_status);
                SharedPreference sharedPreference = SharedPreference.getInstance(getApplicationContext());
                ride_status = sharedPreference.getValue(com.ddkcommunity.rider.Constant.ride_status_key);
                Intent in  = null;
                if(notification_type.equalsIgnoreCase("chat")) {
                   in = new Intent(getApplicationContext(), ChatActivity.class);
                                  }
                else

                if (ride_status.equalsIgnoreCase(""))
                {
                     in = new Intent(getApplicationContext(), MapsActivity.class);
                }
               else
                if (ride_status.equalsIgnoreCase(com.ddkcommunity.rider.Constant.Completed))
                {
                     in = new Intent(getApplicationContext(), BillActivity.class);
                }
                 else
                {
                     in = new Intent(getApplicationContext(), GoogleMapWithBottomSheet.class);

                }

                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, in, 0);
                String CHANNEL_ID = "my_channel_01";// The id of the channel.
                CharSequence name = "My Channel";// The user-visible name of the channel.
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                }
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Notification notification;
                notification = mBuilder
                        .setAutoCancel(true)
                        .setContentTitle("SAM Rider")
                        .setContentIntent(pIntent)
                        .setSound(defaultSoundUri)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setSmallIcon(R.drawable.ic_launcher_app_logo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_app_logo))
                        .setContentText("")
                        .setChannelId(CHANNEL_ID)
                        .build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(mChannel);
                }
                notificationManager.notify(0, notification);

            }
        });
*//*

        try {
            Intent in = new Intent("NotificationPushReceived");
            sendBroadcast(in);
        } catch (Exception cv) {
            cv.printStackTrace();
        }

    }
*/


