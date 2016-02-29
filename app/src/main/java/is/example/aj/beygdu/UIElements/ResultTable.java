package is.example.aj.beygdu.UIElements;

import java.util.ArrayList;

/**
 * Created by arnar on 2/29/2016.
 */
public class ResultTable implements ResultObject {

    public static final int item_Type = 1;

    private String title;
    private String[] columnNames;
    private String[] rowNames;
    private ArrayList<String> content;


    private ResultTable() {

    }

    public static ResultTable create(String title, String[] columnNames, String[] rowNames, ArrayList<String> content) {
        ResultTable rTable = new ResultTable();
        rTable.setTitle(title);
        rTable.setColumnNames(columnNames);
        rTable.setRowNames(rowNames);
        rTable.setContent(content);
        return rTable;
    }

    @Override
    public int getType() {
        return item_Type;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getRowNames() {
        return rowNames;
    }

    public void setRowNames(String[] rowNames) {
        this.rowNames = rowNames;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }


}
