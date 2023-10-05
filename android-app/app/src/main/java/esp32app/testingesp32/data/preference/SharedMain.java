package esp32app.testingesp32.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedMain {

    private final SharedMain instance;
    private final SharedPreferences preference;
    private final SharedPreferences.Editor editor;
    private static final String MAIN_PREF = "main";

    public interface KEYS{
        String LOGIN = "login";
        String ID = "id";
    }

    public SharedMain(Context context) {
        this.instance = this;
        preference = context.getSharedPreferences(MAIN_PREF, Context.MODE_PRIVATE);
        editor = preference.edit();
    }

    public static SharedMain newInstance(Context context) {
        SharedMain fragment = new SharedMain(context);
        return fragment.instance;
    }

    public boolean isLoggedIn(){
        return preference.getBoolean(KEYS.LOGIN, false);
    }

    public SharedMain setLoggedIn(boolean status){
        editor.putBoolean(KEYS.LOGIN, status);
        editor.commit();
        return instance;
    }

    public int getId(){
        return preference.getInt(KEYS.ID, -1);
    }

    public void setId(int id){
        editor.putInt(KEYS.ID, id);
        editor.commit();
    }

}
