package is.example.aj.beygdu.Skrambi;

import android.content.Context;
import android.util.Log;

import java.io.StringReader;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import is.example.aj.beygdu.Async.SkrambiAsyncTask;

/**
 * Created by arnar on 2/11/2016.
 */
public class SkrambiWT {

    private String searchWord;
    private String word;
    private Context context;

    public SkrambiWT(Context context, String searchWord) {
        this.context = context;
        this.searchWord = searchWord;
    }

    public String[] extractCorrections() {
        try {
           return new String[] { new SkrambiAsyncTask(context).execute(searchWord).get(), "" };
        }
        catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        /*
        if(checkWrappings() && checkKeywords()) {
            return manageOutput();
        }

        return null;
        */
    }

    private String[] encode(String[] args) {
        String[] decodingArray = new String[args.length];
        for(int i = 0; i < args.length; i++) {
            try {
                Properties p = new Properties();
                p.load(new StringReader("key="+args[i]));
                decodingArray[i] = p.getProperty("key");
            }
            catch ( Exception e ) {
                Log.w("Exception", e);
                decodingArray[i] = args[i];
            }
        }
        return decodingArray;
    }

    private String[] prepareResults(String[] arr) {
        String[] corrections = new String[arr.length];
        int count = 0;
        for( String str : arr ) {
            str = str.replaceAll("\"", "");
            str = str.replaceAll(",", "");
            corrections[count++] = str;
        }

        return corrections;
    }

    private String[] manageOutput() {

        String temp = word;

        try {
            // Remove starting and stopping bracket
            temp = temp.substring(1, temp.length()-1);
            // Extract possible corrections --- [EXAMP1, EXAMP2, EXAMP3, ...]
            temp = temp.substring(temp.indexOf("[")+1, temp.indexOf("]"));

            String[] results = prepareResults(temp.split(" "));
            return encode(results);

        }
        catch (Exception e) {
            return null;
        }

    }

    private boolean checkWrappings() {
        if(word.contains("[{") && word.contains("]}")) {
            if (word.indexOf("[{") == 0) {
                if (word.indexOf("]}") == word.length() - 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkKeywords() {
        return word.contains("charStart")
                && word.contains("charEnd")
                && word.contains("targetWord")
                && word.contains("suggestions")
                && word.contains("errorClass");
    }


}
