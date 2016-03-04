package is.example.aj.beygdu.Utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by arnar on 3/3/2016.
 */
public class FontManager {

    public static final int LATO_BOLD = 0;
    public static final int LATO_SEMIBOLD = 1;
    public static final int LATO_LIGHT = 2;

    public static Typeface getFont(Context context, int id) {
        switch (id) {
            case LATO_BOLD:
                return Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
            case LATO_SEMIBOLD:
                return Typeface.createFromAsset(context.getAssets(), "Lato-Semibold.ttf");
            case LATO_LIGHT:
                return Typeface.createFromAsset(context.getAssets(), "Lato-Light.ttf");
            default:
                return null;
        }
    }
}
