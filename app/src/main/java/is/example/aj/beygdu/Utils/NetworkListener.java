package is.example.aj.beygdu.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Arnar
 * @since ??.??
 * @version 1.0
 *
 * A simple helper class that handles network checks
 */
public class NetworkListener {

    private Context context;
    private ConnectivityManager connectivityManager;

    public NetworkListener(Context context) {
        this.context = context;

    }

    // TODO : find out why ConnectiviyManager returns null
    public boolean isActive() {
        //connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        //NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        //return info != null;
        return true;
    }
}
