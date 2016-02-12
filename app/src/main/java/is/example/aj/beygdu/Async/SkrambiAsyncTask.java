package is.example.aj.beygdu.Async;

import android.content.Context;
import android.os.AsyncTask;

import is.example.aj.beygdu.Skrambi.RequestHandler;
import is.example.aj.beygdu.Utils.NetworkListener;

/**
 * Created by arnar on 2/11/2016.
 */
public class SkrambiAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;
    private String url = "http://skrambi.arnastofnun.is/checkDocument";

    public SkrambiAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        if(!new NetworkListener(context).isActive()) {
            return null;
        }

        try {
            RequestHandler requestHandler = new RequestHandler(
                    url, params[0], "text/plain", "en-US",
                    false, true, true);

            return requestHandler.sendRequest();

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String arg) {
        super.onPostExecute(arg);
    }
}
