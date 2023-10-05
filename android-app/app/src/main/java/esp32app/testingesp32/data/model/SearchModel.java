package esp32app.testingesp32.data.model;

import static esp32app.me.akshay.index.Index.DATABASE_ROOT;

import com.google.firebase.database.DatabaseReference;

import esp32app.me.akshay.index.Index;

public class SearchModel {
    public int id;
    public String name;
    public String profession;
    public String number;
    public String photoUrl;
    public String experience;
    public String gender;
    public String available;

    public SearchModel(int id, String name, String profession, String number, String photoUrl, String experience, String gender, String available) {
        this.id = id;
        this.name = name;
        this.profession = profession;
        this.number = number;
        this.photoUrl = photoUrl;
        this.experience = experience;
        this.gender = gender;
        this.available = available;
    }

    public SearchModel() {}

    public static DatabaseReference getSearchDb(){
        return DATABASE_ROOT.child(Index.Child.PROJECT_ROOT).child(Index.Child.DOCTORS);
    }
}
