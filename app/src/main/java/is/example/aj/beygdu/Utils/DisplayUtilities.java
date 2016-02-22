package is.example.aj.beygdu.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by arnar on 2/14/2016.
 */
public class DisplayUtilities {
    /**
     * This method converts device specific pixels to density independent pixels.
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static int getScreenWidth(Display display) {
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
