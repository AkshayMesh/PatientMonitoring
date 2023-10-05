package esp32app.testingesp32.data.model;

import static esp32app.me.akshay.index.Index.DATABASE_ROOT;
import static esp32app.me.akshay.index.Index.STORAGE_ROOT;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import esp32app.me.akshay.index.Index;

public class MediaDescriptionModel {
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("category")
    @Expose
    public String category;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("description")
    @Expose
    public String desc;

    @SerializedName("url")
    @Expose
    public String url;

    public static DatabaseReference getVideoDb() {
        return DATABASE_ROOT.child(Index.Child.PROJECT_ROOT).child(Index.Child.MEDIA_VIDEOS);
    }

    public static DatabaseReference getAudioDb(){
        return DATABASE_ROOT.child(Index.Child.PROJECT_ROOT).child(Index.Child.MEDIA_AUDIO);
    }

    public static StorageReference getAudioStorageDir(){
        return STORAGE_ROOT.child(Index.Child.PROJECT_ROOT).child(Index.Child.MEDIA_AUDIO);
    }

    public static StorageReference getVideoStorageDir(){
        return STORAGE_ROOT.child(Index.Child.PROJECT_ROOT).child(Index.Child.MEDIA_VIDEOS);
    }
}
