package esp32app.testingesp32.data.model;

import static esp32app.me.akshay.index.Index.DATABASE_ROOT;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import esp32app.me.akshay.index.Index;

public class ESP32Model {
    @SerializedName("Avg")
    @Expose
    public double Avg;

    @SerializedName("Current position:")
    @Expose
    public String position;
    @SerializedName("Spo2")
    @Expose
    public String Spo2;
    @SerializedName("{Temperature")
    @Expose
    public double temperature;

    public static DatabaseReference getEsp32Db(){
        return DATABASE_ROOT.child(Index.Child.DEVICE);
    }
}
