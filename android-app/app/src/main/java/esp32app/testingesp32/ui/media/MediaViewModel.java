package esp32app.testingesp32.ui.media;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import esp32app.testingesp32.data.model.MediaDescriptionModel;

public class MediaViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<MediaDescriptionModel>> videoListLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<MediaDescriptionModel>> audioListLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorData = new MutableLiveData<>();

    public LiveData<ArrayList<MediaDescriptionModel>> getVideoListLiveData() {
        return videoListLiveData;
    }

    public LiveData<ArrayList<MediaDescriptionModel>> getAudioListLiveData() {
        return audioListLiveData;
    }

    public LiveData<String> getErrorData() {
        return errorData;
    }

    public MediaViewModel(@NonNull Application application) {
        super(application);
    }

    public void observeDbForMedia(){

        MediaDescriptionModel.getVideoDb().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MediaDescriptionModel>recommendedList =  new ArrayList<>();
                try {
                    for (DataSnapshot postSnapshot:snapshot.getChildren()){
                        MediaDescriptionModel singleVideo = null;
                        try {
                            singleVideo = postSnapshot.getValue(MediaDescriptionModel.class);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if (singleVideo!=null)
                            recommendedList.add(singleVideo);
                    }
                    videoListLiveData.setValue(recommendedList);
                }catch (ClassCastException cce){
                    cce.printStackTrace();
                    errorData.setValue("Problem loading videos");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorData.setValue("Unable to connect to server");
            }
        });

        MediaDescriptionModel.getAudioDb().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MediaDescriptionModel> recommendedListAudio = new ArrayList<>();
                try {
                    for (DataSnapshot postSnapshot:snapshot.getChildren()){
                        MediaDescriptionModel singleVideo = postSnapshot.getValue(MediaDescriptionModel.class);
                        if (singleVideo!=null)
                            recommendedListAudio.add(singleVideo);
                    }
                    audioListLiveData.setValue(recommendedListAudio);
                }catch (ClassCastException cce){
                    cce.printStackTrace();
                    errorData.setValue("Problem loading audios");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorData.setValue("Unable to connect to server");
            }
        });
    }

}
