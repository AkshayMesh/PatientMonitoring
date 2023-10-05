package esp32app.testingesp32.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import esp32app.testingesp32.data.model.ESP32Model;
import esp32app.testingesp32.data.model.NotificationModel;
import esp32app.testingesp32.ui.user.device.UserDeviceViewModel;
import esp32app.testingesp32.util.NotificationUtil;
import esp32app.testingesp32.util.ObjectUtils;

public class FirebaseService extends Service {

    private static final int NOTIFICATION_ID = 1; // Unique ID for the foreground notification

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_NOT_STICKY;
        NotificationModel model = new NotificationModel();
        model.message = "Monitoring is active";
        model.title = "Patient Monitoring";
        Notification notification = NotificationUtil.createCustomNotification(this, model);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(
                    NOTIFICATION_ID,               // Notification ID
                    notification,                   // Notification instance
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE // Foreground service type
            );
        } else {
            startForeground(NOTIFICATION_ID, notification);
        }
        startObservers();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startObservers() {
        UserDeviceViewModel model = new UserDeviceViewModel(getApplication());
        model.observeDeviceChanges();
        model.getLiveDeviceModel().observeForever(item ->
                new CountDownTimer(5000, 2000) {
                    public void onTick(long l) {
                    }
                    public void onFinish() {//here mnext is the button from which we can get next question.
                        checkForAlerts(item);
                    }
                }.start());
    }

    private void checkForAlerts(ESP32Model model) {
//        notification.type = null;
        if (model.position.equalsIgnoreCase("falling")){
            NotificationModel notification = new NotificationModel();
            notification.deviceModel = ObjectUtils.objectToString(model);
            notification.title = "Emergency Alert";
            notification.message = "Patient's is falling";
            notification.type = "Position";
            NotificationUtil.setNotificationMessage(this, true, notification, true, null);
            NotificationUtil.addNotificationToDatabase(this, notification);
        }
        if (model.Avg >= 150){
            NotificationModel notification = new NotificationModel();
            notification.deviceModel = ObjectUtils.objectToString(model);
            notification.title = "Pulse rate Alert";
            notification.message = "Patient's pulse rate is very high";
            notification.type = "Pulse";
            NotificationUtil.setNotificationMessage(this, true, notification, true, null);
            NotificationUtil.addNotificationToDatabase(this, notification);
        }
        if (model.temperature >= 100){
            NotificationModel notification = new NotificationModel();
            notification.deviceModel = ObjectUtils.objectToString(model);
            notification.title = "Fever Alert";
            notification.message = "Patient's body temperature is very high";
            notification.type = "Temperature";
            NotificationUtil.setNotificationMessage(this, true, notification, true, null);
            NotificationUtil.addNotificationToDatabase(this, notification);
        }
        try {
            double spo = Double.parseDouble(model.Spo2);
            if (spo < 90){
                NotificationModel notification = new NotificationModel();
                notification.deviceModel = ObjectUtils.objectToString(model);
                notification.title = "Oxygen Alert";
                notification.message = "Patient's oxygen level is very low";
                notification.type = "Spo2";
                NotificationUtil.setNotificationMessage(this, true, notification, true, null);
                NotificationUtil.addNotificationToDatabase(this, notification);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
