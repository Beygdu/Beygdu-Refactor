package is.example.aj.beygdu.Parser;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by arnar on 2/9/2016.
 */
public class SubBlock implements Parcelable {

    private String title;
    private ArrayList<Table> tables;

    public SubBlock(String title, ArrayList<Table> content) {
        this.title = title;
        this.tables = content;
    }

    protected SubBlock(Parcel in) {
        title = in.readString();
        tables = in.createTypedArrayList(Table.CREATOR);
    }

    public static final Creator<SubBlock> CREATOR = new Creator<SubBlock>() {
        @Override
        public SubBlock createFromParcel(Parcel in) {
            return new SubBlock(in);
        }

        @Override
        public SubBlock[] newArray(int size) {
            return new SubBlock[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public int getBlockSize() {
        return tables.size();
    }

    public boolean hasTitle() {
        return !title.equals("");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeTypedList(tables);
    }
}
