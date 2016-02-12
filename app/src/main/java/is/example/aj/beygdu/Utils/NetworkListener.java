package is.example.aj.beygdu.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by arnar on 2/10/2016.
 */
public class NetworkListener {

    private Context context;
    private ConnectivityManager connectivityManager;

    public NetworkListener(Context context) {
        this.context = context;

    }

    public boolean isActive() {
        //connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        //NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        //return info != null;
        return true;
    }
}
