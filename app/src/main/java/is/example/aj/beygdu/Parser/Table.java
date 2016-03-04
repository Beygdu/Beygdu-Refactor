package is.example.aj.beygdu.Parser;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by arnar on 2/9/2016.
 */
public class Table implements Parcelable {

    // LayoutId for special cases
    public static final int LAYOUT_NORMAL = 0;
    public static final int LAYOUT_VERB_BIGBLOCK = 1;
    public static final int LAYOUT_VERB_SMALLBLOCK = 2;
    public static final int LAYOUT_VERB_SINGLEBLOCK = 3;
    public static final int LAYOUT_ACTION = 4; // adverb

    private String title;
    private String[] columnNames;
    private String[] rowNames;
    private ArrayList<String> content;
    private int layoutId;

    public Table(String title, String[] columnNames, String[] rowNames, ArrayList<String> content, int layoutId) {
        this.title = title;
        this.columnNames = columnNames;
        this.rowNames = rowNames;
        this.content = content;
        this.layoutId = layoutId;
    }

    protected Table(Parcel in) {
        title = in.readString();
        columnNames = in.createStringArray();
        rowNames = in.createStringArray();
        content = in.createStringArrayList();
        layoutId = in.readInt();
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public String[] getRowNames() {
        return rowNames;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return rowNames.length;
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
        dest.writeStringArray(columnNames);
        dest.writeStringArray(rowNames);
        dest.writeStringList(content);
        dest.writeInt(layoutId);
    }
}
