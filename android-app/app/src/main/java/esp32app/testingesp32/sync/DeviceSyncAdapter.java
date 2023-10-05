package esp32app.testingesp32.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

public class DeviceSyncAdapter extends AbstractThreadedSyncAdapter {

    ContentResolver contentResolver;
    public DeviceSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        contentResolver = context.getContentResolver();
    }

    public DeviceSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs, ContentResolver contentResolver) {
        super(context, autoInitialize, allowParallelSyncs);
        this.contentResolver = contentResolver;
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

    }
}
