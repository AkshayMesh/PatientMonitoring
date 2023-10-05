package esp32app.testingesp32.util;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

public class FileUtil {

    /**
     * @param uri image
     * @return extension of file
     */
    public static String getFileExtension(Uri uri, Context context){
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
