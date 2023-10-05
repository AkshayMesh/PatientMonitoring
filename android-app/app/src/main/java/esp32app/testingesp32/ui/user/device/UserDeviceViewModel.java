package esp32app.testingesp32.ui.user.device;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import esp32app.testingesp32.data.model.ESP32Model;
import esp32app.testingesp32.util.ObjectUtils;

public class UserDeviceViewModel extends AndroidViewModel {

    private MutableLiveData<ESP32Model> liveDeviceModel = new MutableLiveData<>();
    public UserDeviceViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ESP32Model> getLiveDeviceModel() {
        return liveDeviceModel;
    }

    public void observeDeviceChanges(){
        ESP32Model.getEsp32Db().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue()!=null) {
                        String rawValue = snapshot.getValue().toString();
                        String jsonCompatibleData = ObjectUtils.convertToValidJSON(rawValue);
                        ESP32Model mainDeviceModel = (ESP32Model) ObjectUtils.stringToObject(jsonCompatibleData, ESP32Model.class);
                        if (mainDeviceModel != null) {
                            mainDeviceModel.Spo2 = mainDeviceModel.Spo2.substring(0, mainDeviceModel.Spo2.length()-1);
                            liveDeviceModel.setValue(mainDeviceModel);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplication().getApplicationContext(), "Unable to connect to server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
