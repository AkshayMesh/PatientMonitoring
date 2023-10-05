package esp32app.testingesp32.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import esp32app.testingesp32.data.model.AccountDetails;

@Dao
public interface AccountDAO {
    @Query("SELECT * FROM AccountDetails")
    List<AccountDetails> getAll();

    @Query("SELECT * FROM AccountDetails WHERE uid == :id LIMIT 1")
    AccountDetails loadAccountById(int id);

    @Query("SELECT * FROM AccountDetails WHERE user_name LIKE :first AND " +
            "password LIKE :last LIMIT 1")
    AccountDetails loginUser(String first, String last);

    @Query("SELECT * FROM AccountDetails WHERE uid != -55")
    List<AccountDetails> loadNonAdminUsers();

    @Insert
    void insertAll(AccountDetails... accountDetails);

    @Query("DELETE FROM Accountdetails")
    void nukeTable();
}
