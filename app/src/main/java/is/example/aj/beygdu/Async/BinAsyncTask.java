package is.example.aj.beygdu.Async;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;

import is.example.aj.beygdu.Parser.BinHelper;
import is.example.aj.beygdu.Parser.WordResult;
import is.example.aj.beygdu.Utils.NetworkListener;

/**
 * @author Arnar Jonsson
 * @version 0.1
 * @since 14.4.2015
 *
 * BinAsyncTask - creates result from the bin search
 */
public class BinAsyncTask extends AsyncTask<String, Void, WordResult> {

    private Context context;

    public BinAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected WordResult doInBackground(String... params) {

        if(!new NetworkListener(context).isActive()) {
            return null;
        }

        try {
            BinHelper helper = new BinHelper();
            return helper.createResult(params[0]);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(WordResult arg) {
        super.onPostExecute(arg);
    }
}
