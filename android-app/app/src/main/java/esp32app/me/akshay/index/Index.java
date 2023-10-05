package esp32app.me.akshay.index;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

@SuppressLint("StaticFieldLeak")
public class Index {

    private static final Index instance = new Index();
    private boolean connected = false;
    private static Context context;
    public static final String FINE_LOCATION   = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    public static final DatabaseReference DATABASE_ROOT = FirebaseDatabase.getInstance().getReference();
    public static final StorageReference STORAGE_ROOT = FirebaseStorage.getInstance().getReference();

    public static Index getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Network nw = connectivityManager.getActiveNetwork();
                connected = nw != null ;
                NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
                connected =  actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
            } else {
                NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
                connected =  nwInfo != null && nwInfo.isAvailable() &&nwInfo.isConnected();
            }
            return connected;
        } catch (Exception e) {
            Log.v("connectivity", e.toString());
        }
        return connected;
    }

    public interface Constants{
        int SPLASH_DELAY = 7000;
        int RC_SIGN_IN = 9001;
        int ID_MAX = 9999;
        int ID_MIN = 1000;
        int LOAD_VEHICLE_IMAGE = 1111;
        int LOAD_DOC1 = 2222;
        int LOAD_DOC2 = 3333;
        int LOAD_DOC3 = 4444;
        int LOAD_DOC4 = 5555;
        float DEFAULT_ZOOM  = 17.3f;
    }

    public interface StringConstants{
        String APP_KEY          = "app_terms";
        String REGISTRATION     = "entered";
        String PROFILE          = "photograph";
        String COUNTRY_CODE     = "+91";
        String EMPTY_KEY        = "";
        String BASE             = "DATA";
        String USER             = "customer";
        String AGREE_TERMS      = "agreement";
        String GOOGLE_LOGIN     = "google";
        String PHONE_LOGIN      = "phone";
        String NEW_USER         = "new_user";
        String LOCALE_NO        = "locale_number";
        String USER_LOC         = "user_location";
        String ADMINISTRATOR    = "Admin";
    }

    public interface IntentKeys{
        String PHONE = "phone_key";
        String MEDIA = "media";
    }

    public interface Child{
        String PROJECT_ROOT = "android";
        String MEDIA_VIDEOS = "videos";
        String MEDIA_AUDIO = "audios";
        String DEVICE = "ESP32";
        String DOCTORS = "doctors";
    }

}
