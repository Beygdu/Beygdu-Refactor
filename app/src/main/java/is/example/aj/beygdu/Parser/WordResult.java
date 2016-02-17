package is.example.aj.beygdu.Parser;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import is.example.aj.beygdu.Utils.Bstring;

/**
 * Created by arnar on 2/9/2016.
 */
public class WordResult implements Parcelable {

    // Control strings
    public static String singleHit = "Single-Hit";
    public static String multiHit = "Multi-Hit";
    public static String miss = "Miss";

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

    protected WordResult(Parcel in) {
        searchWord = in.readString();
        description = in.readString();
        multiHitDescriptions = in.createStringArray();
        multiHitIds = in.createIntArray();
        title = in.readString();
        warning = in.readString();
        result = in.createTypedArrayList(Block.CREATOR);
    }

    public static final Creator<WordResult> CREATOR = new Creator<WordResult>() {
        @Override
        public WordResult createFromParcel(Parcel in) {
            return new WordResult(in);
        }

        @Override
        public WordResult[] newArray(int size) {
            return new WordResult[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(searchWord);
        dest.writeString(description);
        dest.writeStringArray(multiHitDescriptions);
        dest.writeIntArray(multiHitIds);
        dest.writeString(title);
        dest.writeString(warning);
        dest.writeTypedList(result);
    }
}
