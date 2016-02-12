package is.example.aj.beygdu.Parser;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import is.example.aj.beygdu.Utils.Bstring;

/**
 * Created by arnar on 2/9/2016.
 */
public class WordResult implements Serializable {

    // Variables
    private String searchWord;
    private String description;

    // Multi-result variables
    private String[] multiHitDescriptions;
    private int[] multiHitIds;

    // Single result variables
    private String title;
    private String warning;
    private ArrayList<Block> result;

    // Debug variables
    private ArrayList<Bstring> debug;

    public WordResult() {
        // Empty
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getMultiHitDescriptions() {
        return multiHitDescriptions;
    }

    public void setMultiHitDescriptions(String[] multiHitDescriptions) {
        this.multiHitDescriptions = multiHitDescriptions;
    }

    public int[] getMultiHitIds() {
        return multiHitIds;
    }

    public void setMultiHitIds(int[] multiHitIds) {
        this.multiHitIds = multiHitIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public ArrayList<Block> getResult() {
        return result;
    }

    public void setResult(ArrayList<Block> result) {
        this.result = result;
    }

    public ArrayList<Bstring> getDebug() {
        return debug;
    }

    public void setDebug(ArrayList<Bstring> debug) {
        this.debug = debug;
    }
}
