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


    public static int integerToDp(Context context, int dpValue) {
        float d = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * d); // margin in pixels
    }
}
