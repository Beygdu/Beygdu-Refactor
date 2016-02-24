package is.example.aj.beygdu.Async;

import android.content.Context;
import android.os.AsyncTask;

import is.example.aj.beygdu.Cache.DBController;
import is.example.aj.beygdu.Parser.WordResult;

/**
 * Created by arnar on 2/23/2016.
 */
public class CacheAsyncTask extends AsyncTask<Object, Void, Object> {

    private Context context;
    private static String INSERT_SUCCESS = "success";
    private static String INSERT_FAILURE = "failure";


    public CacheAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object... params) {

        if( params[0].equals("0")) {
            try {
                DBController controller = new DBController(context);
                controller.insert((WordResult) params[1]);
                return INSERT_SUCCESS;
            }
            catch (Exception e) {
                e.printStackTrace();
                return INSERT_FAILURE;
            }
        }
        else {
            try {
                DBController controller = new DBController(context);
                return controller.fetchAllWords();
            }
            catch (Exception e) {
                e.printStackTrace();
                return INSERT_FAILURE;
            }
        }
    }

    @Override
    protected void onPostExecute(Object arg) {
        super.onPostExecute(arg);
    }
}
