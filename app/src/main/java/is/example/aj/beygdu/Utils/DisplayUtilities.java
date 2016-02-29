package is.example.aj.beygdu.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Arnar
 * @since 01.16
 * @version 1.0
 *
 * Houses useful methods that where housed originally in their respected activities
 *
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

    /**
     *
     * @param display Display
     * @return width of the display
     */
    public static int getScreenWidth(Display display) {
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }

    public static int getScreenHeigth(Display display) {
        Point size = new Point();
        display.getSize(size);
        int heigth = size.y;
        return heigth;
    }

    // Helper for view id generation
    private static final AtomicInteger nextGeneratedId = new AtomicInteger(1);

    /**
     * Creates a valid ID for a view subclass object
     * Needed due to the fact that the targeted API of the application is 15
     * In never versions this can be replaced by the call View.SetId()
     * @return a valid id for a view subclass
     */
    public static int generateViewId() {
        for (;;) {
            final int result = nextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (nextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }

    }

    public static int integerToDp(Context context, int dpValue) {
        float d = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * d); // margin in pixels
    }
}
