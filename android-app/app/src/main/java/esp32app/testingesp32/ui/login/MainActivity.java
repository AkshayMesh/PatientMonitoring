package esp32app.testingesp32.ui.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Objects;

import esp32app.testingesp32.data.model.AccountDetails;
import esp32app.testingesp32.data.preference.SharedMain;
import esp32app.testingesp32.data.room.database.RoomDatabaseMain;
import esp32app.testingesp32.databinding.ActivityMainBinding;
import esp32app.testingesp32.ui.admin.home.AdminHomeActivity;
import esp32app.testingesp32.ui.user.home.UserHomeActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String enteredUserId, enteredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        onClickEvents();
    }

    private void onClickEvents() {
        binding.btnLogin.setOnClickListener(v->{
            if (inputsValid()){
               tryToLogin();
            }
        });
    }

    private void tryToLogin() {
        RoomDatabaseMain db = Room.databaseBuilder(getApplicationContext(),
                RoomDatabaseMain.class, "Account-Video").build();
        AsyncTask.execute(()->{
            AccountDetails account = db.accountsDao().loginUser(enteredUserId, enteredPassword);
            if (account!=null){
                openAccordingToAccount(account);
            } else {
                runOnUiThread(()-> Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void openAccordingToAccount(AccountDetails account) {
        Intent intent;
        switch (account.uid){
            case -55:
                intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                break;
            case 1:
                intent = new Intent(MainActivity.this, UserHomeActivity.class);
                break;
            default:
                intent = null;
        }
        if (intent!=null) {
            SharedMain.newInstance(this).setLoggedIn(true).setId(account.uid);
            startActivity(intent);
            finish();
        } else {
            runOnUiThread(()-> Toast.makeText(this, "Wrong account", Toast.LENGTH_SHORT).show());
        }
    }

    private boolean inputsValid() {
        enteredPassword = Objects.requireNonNull(binding.etPassword.getText()).toString().trim();
        enteredUserId = Objects.requireNonNull(binding.etUsername.getText()).toString().trim();
        if (enteredUserId.length()<1){
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            binding.etUsername.setError("Required");
            return false;
        } else if (enteredPassword.length()<1){
            binding.etUsername.setError(null);
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            binding.etPassword.setError("Required");
            return false;
        }
        return true;
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    private void initViews() {
        SharedMain sharedPref = SharedMain.newInstance(this);
        boolean isLoggedIn = sharedPref.isLoggedIn();

        if (!isLoggedIn){
            return;
        } else {
            int id = sharedPref.getId();
            if (id == -1){
                return;
            } else {
                Intent intent;
                if (id == -55){
                    intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, UserHomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }
    }
}