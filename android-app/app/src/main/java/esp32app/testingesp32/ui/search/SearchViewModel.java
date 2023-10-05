package esp32app.testingesp32.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import esp32app.testingesp32.data.model.SearchModel;

public class SearchViewModel extends AndroidViewModel {

    private final MutableLiveData<List<SearchModel>> liveSearchData = new MutableLiveData<>();
    private final MutableLiveData<String> errorData = new MutableLiveData<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
        setSearchLiveData();
    }

    public LiveData<String> getErrorData() {
        return errorData;
    }

    private void setSearchLiveData() {
        //generateSampleSearchModels(50);
        SearchModel.getSearchDb().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<SearchModel> searchModelArrayList = new ArrayList<>();
                try {
                    for (DataSnapshot postSnapshot:snapshot.getChildren()){
                        SearchModel oneDoctor = postSnapshot.getValue(SearchModel.class);
                        if (oneDoctor!=null)
                            searchModelArrayList.add(oneDoctor);
                    }
                    liveSearchData.setValue(searchModelArrayList);
                }catch (ClassCastException cce){
                    cce.printStackTrace();
                    errorData.setValue("Problem loading search data");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorData.setValue("Unable to connect to server");
            }
        });
    }

    public LiveData<List<SearchModel>> getLiveSearchData() {
        return liveSearchData;
    }

    @SuppressWarnings("SameParameterValue")
    private ArrayList<SearchModel> generateSampleSearchModels(int size) {
        ArrayList<SearchModel> searchModels = new ArrayList<>();
        Random rand = new Random();

        for (int i = 1; i <= size; i++) {
            String gender = rand.nextBoolean() ? "Male" : "Female";
            String profession = getDoctorProfession();
            String fullName = getRandomFullName(gender);

            searchModels.add(new SearchModel(
                    i,
                    fullName,
                    profession,
                    "Number" + i,
                    "https://example.com/photo" + i + ".jpg",
                    "Experience: " + rand.nextInt(30) + " years",
                    gender,
                    rand.nextBoolean() ? "Available" : "Unavailable"
            ));
        }

        return searchModels;
    }

    private String getRandomFullName(String gender) {
        String[] maleFirstNames = {"John", "Michael", "David", "Robert", "William", "James", "Joseph", "Daniel", "Richard", "Thomas"};
        String[] femaleFirstNames = {"Mary", "Jennifer", "Linda", "Patricia", "Elizabeth", "Susan", "Jessica", "Sarah", "Karen", "Nancy"};

        String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor"};

        String[] firstNames = (gender.equals("Male")) ? maleFirstNames : femaleFirstNames;
        String firstName = firstNames[new Random().nextInt(firstNames.length)];
        String lastName = lastNames[new Random().nextInt(lastNames.length)];
        return firstName + " " + lastName;
    }

    private String getDoctorProfession() {
        String[] professions = {"General Physician", "Surgeon", "Dermatologist", "Pediatrician", "Gynecologist", "Cardiologist", "Orthopedic Surgeon"};
        return professions[new Random().nextInt(professions.length)];
    }
}
