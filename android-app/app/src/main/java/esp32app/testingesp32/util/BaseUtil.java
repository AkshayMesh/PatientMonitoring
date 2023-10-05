package esp32app.testingesp32.util;

import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class BaseUtil {
    /**
     * abstract class for Launch Activity for some results
     */
    public static abstract class LaunchActivityForResult {
        public abstract void onResult(ActivityResult result);

        public ActivityResultLauncher<Intent> setLauncher(AppCompatActivity activity) {
            return activity.registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(), this::onResult);
        }
    }

    public static String getFirstName(String fullName) {
        return fullName.substring(0, fullName.indexOf(' '));
    }
}