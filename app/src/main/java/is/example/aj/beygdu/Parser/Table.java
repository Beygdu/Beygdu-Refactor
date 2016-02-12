package is.example.aj.beygdu.Parser;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by arnar on 2/9/2016.
 */
public class Table implements Serializable {

    private String title;
    private String[] columnNames;
    private String[] rowNames;
    private ArrayList<String> content;

    public Table(String title, String[] columnNames, String[] rowNames, ArrayList<String> content) {
        this.title = title;
        this.columnNames = columnNames;
        this.rowNames = rowNames;
        this.content = content;
    }

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

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return rowNames.length;
    }

    public boolean hasTitle() {
        return !title.equals("");
    }

}
