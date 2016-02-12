package is.example.aj.beygdu.Parser;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by arnar on 2/9/2016.
 */
public class SubBlock implements Serializable {

    private String title;
    private ArrayList<Table> tables;

    public SubBlock(String title, ArrayList<Table> content) {
        this.title = title;
        this.tables = content;
    }

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
}
