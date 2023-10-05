package esp32app.testingesp32.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import java.util.concurrent.ThreadLocalRandom;

import esp32app.testingesp32.R;
import esp32app.testingesp32.data.model.NotificationModel;
import esp32app.testingesp32.data.room.database.RoomDatabaseMain;
import esp32app.testingesp32.ui.user.home.UserHomeActivity;

public class NotificationUtil {

    private final static int notificationID = 8090;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void setupChannels(NotificationManager notificationManager, String channelId, String channelName, int importance) {
        String adminChannelDescription = "Notifications";
        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(channelId, channelName, importance);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.GREEN);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.cancel(notificationID);
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

    public static NotificationCompat.Builder setNotificationMessage(@NonNull Context context,
            boolean emergency, NotificationModel model, boolean notify, PendingIntent pendingIntent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager, context.getString(R.string.app_name), "Alerts", NotificationManagerCompat.IMPORTANCE_MAX);
        }
        NotificationCompat.Builder builder = CreateNotification(context, emergency, context.getString(R.string.app_name), model.title, model.message, pendingIntent);
        if (notify){
            notifyNotification(notificationManager, builder.build());
        }
        return builder;
    }

    private static NotificationCompat.Builder CreateNotification(Context context, boolean isHighPriority, String channelId, String title, String message,  PendingIntent intents) {
        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder;

        notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setStyle(new NotificationCompat.BigPictureStyle())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(null)
                .setAutoCancel(true);
        if (isHighPriority){
            notificationBuilder.setPriority(Notification.PRIORITY_MAX);
        }
        if (intents!=null) {
            notificationBuilder.setContentIntent(intents);
        }
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        return notificationBuilder;
    }

    public static void notifyNotification(NotificationManager notificationManager, Notification notification){
        notificationManager.notify(ThreadLocalRandom.current().nextInt(9999, 99999 + 1), notification);
    }

    public static void addNotificationToDatabase(Context context, NotificationModel model) {
        model.timestamp = System.currentTimeMillis();
        RoomDatabaseMain db = Room.databaseBuilder(context, RoomDatabaseMain.class, "Account-Video").build();
        AsyncTask.execute(() -> db.notificationDAO().insertAll(model));
    }

    public static Notification createCustomNotification(Context context, NotificationModel notificationModel) {
        Intent notificationIntent = new Intent(context, UserHomeActivity.class); // Change to your activity
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
        );
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager, "foreground", "Monitoring", NotificationManagerCompat.IMPORTANCE_MIN);
        }
        return new NotificationCompat.Builder(context, "foreground")
                .setContentTitle(notificationModel.title)
                .setContentText(notificationModel.message)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setSound(null) // Remove sound
                .build();
    }
}