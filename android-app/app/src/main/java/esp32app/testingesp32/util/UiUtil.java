package esp32app.testingesp32.util;

import android.view.View;
import android.widget.ImageView;

import androidx.core.widget.ImageViewCompat;

public class UiUtil {
    /**
     * hiding views from layout
     *
     * @param views Views
     */
    public static void hideViews(View... views){
        for (View view : views){
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * removing views from layout
     *
     * @param views Views
     */
    public static void removeViews(View... views){
        for (View view : views){
            view.setVisibility(View.GONE);
        }
    }

    /**
     * un hiding views from layout
     *
     * @param views Views
     */
    public static void showViews(View... views){
        for (View view : views){
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void setNoTint(ImageView imageView){
        imageView.setColorFilter(null);
        imageView.clearColorFilter();
        imageView.getDrawable().setTintList(null);
        ImageViewCompat.setImageTintList(imageView, null);
    }
}
