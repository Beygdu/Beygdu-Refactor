package is.example.aj.beygdu.UIElements;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import is.example.aj.beygdu.Parser.Table;
import is.example.aj.beygdu.R;
import is.example.aj.beygdu.Utils.DisplayUtilities;

/**
 * Created by arnar on 3/1/2016.
 */
public class CrapTable {

    // Application context
    private Context context;

    private int tableMargins;
    private int tablePadding;

    private int screenWidth;

    private int titleLayoutPadding;

    private int headerLayoutWidth;
    private int headerLayoutPadding;

    private int cellLayoutPadding;

    private int titleTextSize = 20;
    int headerTextSize = 18, cellTextSize = 16;

    private boolean hasTitle, hasRowHeaders, hasColumnHeaders = true;


    public CrapTable(Context context) {

        this.context = context;

        WindowManager wM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = DisplayUtilities.getScreenWidth(wM.getDefaultDisplay());

        int orientation = context.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            headerLayoutWidth = getDp(40);
        }
        else {
            headerLayoutWidth = getDp(35);
        }

        tableMargins = getDp(15);

        titleLayoutPadding = getDp(10);

        headerLayoutPadding = getDp(5);

        cellLayoutPadding = getDp(5);


    }

    public void setTitle(String title) {

    }

    public void setTitleTextSize(int size) {
        this.titleTextSize = size;
    }

    public void setTitleBackgroundResource(Color color) {

    }




    private LinearLayout[] getSpecialInstance(String title, String[] rowHeaders, String[] columnHeaders, ArrayList<String> content, int layoutId) {

        LinearLayout[] tableRows;
        int cellLayoutWidth;

        switch (layoutId) {
            // Verb big-block
            case Table.LAYOUT_VERB_BIGBLOCK:
                Log.w("SpecialBIG", "Content length is " + content.size());
                Log.w("SpecialBig", "rowHeaders are :"+rowHeaders.length);
                Log.w("SpecialBIg", "columnHeaders are :"+columnHeaders.length);
                int contentCounter = 0;

                tableRows = new LinearLayout[rowHeaders.length+1]; // +1 for title
                for(int i = 0; i < rowHeaders.length+1; i++) {
                    tableRows[i] = new LinearLayout(context);
                    tableRows[i].setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                }

                cellLayoutWidth = calculateCellLayoutWith(screenWidth, columnHeaders.length);

                for(int j = 0; j < tableRows.length; j++) {
                    // Title
                    if(j == 0) {
                        TextView tV = new TextView(context);
                        tV.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        tV.setPadding(titleLayoutPadding, titleLayoutPadding, titleLayoutPadding, titleLayoutPadding);
                        tV.setTextSize(titleTextSize);
                        tV.setText(title);
                        tableRows[j].addView(tV);

                    }
                    // Column headers
                    else if(j == 1) {
                        TextView[] tVs = createTextViews(columnHeaders.length);
                        for(int i = 0; i < columnHeaders.length; i++) {
                            // Empty header
                            if(i==0) {
                                tVs[i].setLayoutParams(new LinearLayout.LayoutParams(
                                        headerLayoutWidth,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));

                            }
                            //columnHeaders
                            else {
                                tVs[i].setLayoutParams(new LinearLayout.LayoutParams(
                                        cellLayoutWidth,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                //tVs[i].setPadding(headerLayoutPadding, headerLayoutPadding, headerLayoutPadding, headerLayoutPadding);
                                tVs[i].setTextSize(headerTextSize);
                                tVs[i].setText(columnHeaders[i]); // first header is blank so no need for offset
                                tVs[i].setBackgroundColor(context.getResources().getColor(R.color.green));
                            }
                        }
                        addViews(tableRows[j], tVs);
                    }
                    // Rows and content
                    else {
                        TextView[] tVs = createTextViews(columnHeaders.length);
                        for(int i = 0; i < columnHeaders.length; i++) {
                            // Manage row header
                            if(i==0) {
                                tVs[i].setLayoutParams(new LinearLayout.LayoutParams(
                                        headerLayoutWidth,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                //tVs[i].setPadding(headerLayoutPadding, headerLayoutPadding, headerLayoutPadding, headerLayoutPadding);
                                tVs[i].setTextSize(headerTextSize);
                                tVs[i].setText(rowHeaders[j - 1]); // offset
                                tVs[i].setBackgroundColor(context.getResources().getColor(R.color.blue));
                            }
                            // Manage content
                            else {
                                tVs[i].setLayoutParams(new LinearLayout.LayoutParams(
                                        cellLayoutWidth,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                //tVs[i].setPadding(headerLayoutPadding, headerLayoutPadding, headerLayoutPadding, headerLayoutPadding);
                                tVs[i].setTextSize(cellTextSize);
                                tVs[i].setText(content.get(contentCounter++));
                            }
                            //addViews(tableRows[j], tVs);
                        }
                        addViews(tableRows[j], tVs);

                    }
                }
                return tableRows;
            // Verb small-block
            case Table.LAYOUT_VERB_SMALLBLOCK:

                tableRows = new LinearLayout[2];
                for(int i = 0; i < 2; i++) {
                    tableRows[i] = new LinearLayout(context);
                    tableRows[i].setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                }

                cellLayoutWidth = calculateCellLayoutWith(screenWidth, content.size()+1); // +1 for offset

                for(int j = 0; j < 2; j++) {
                    TextView[] tVs = new TextView[content.size()];
                    if(j == 0) {
                        for(int k = 1; k < content.size()+1; k++) { // start 1 and +1 for offset
                            tVs[k-1] = new TextView(context);
                            tVs[k-1].setLayoutParams(new LinearLayout.LayoutParams(
                                    cellLayoutWidth,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            ));
                            tVs[k-1].setTextSize(headerTextSize);
                            tVs[k-1].setText(columnHeaders[k]);
                        }
                    }
                    else {
                        for(int k = 0; k < content.size(); k++) {
                            tVs[k] = new TextView(context);
                            tVs[k].setLayoutParams(new LinearLayout.LayoutParams(
                                    cellLayoutWidth,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            ));
                            tVs[k].setTextSize(headerTextSize);
                            tVs[k].setText(content.get(k));
                        }
                    }
                    addViews(tableRows[j], tVs);

                }

                return tableRows;
            // Verb singleBlock
            case Table.LAYOUT_VERB_SINGLEBLOCK:

                tableRows = new LinearLayout[1];
                tableRows[0] = new LinearLayout(context);
                tableRows[0].setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                cellLayoutWidth = calculateCellLayoutWith(screenWidth, content.size()); // +1 for offset

                TextView tV = new TextView(context);
                tV.setLayoutParams(new LinearLayout.LayoutParams(
                    cellLayoutWidth,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                tV.setText(content.get(0));
                tV.setTextSize(headerTextSize);

                tableRows[0].addView(tV);

                return tableRows;
            // Adverb
            case Table.LAYOUT_ACTION:

                tableRows = new LinearLayout[2];
                for(int i = 0; i < 2; i++) {
                    tableRows[i] = new LinearLayout(context);
                    tableRows[i].setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                }

                cellLayoutWidth = calculateCellLayoutWith(screenWidth, content.size()+1); // +1 for offset

                for(int j = 0; j < 2; j++) {
                    TextView[] tVs = new TextView[content.size()];
                    if(j == 0) {
                        for(int k = 1; k < content.size()+1; k++) { // start 1 and +1 for offset
                            tVs[k-1] = new TextView(context);
                            tVs[k-1].setLayoutParams(new LinearLayout.LayoutParams(
                                    cellLayoutWidth,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            ));
                            tVs[k-1].setTextSize(headerTextSize);
                            tVs[k-1].setText(columnHeaders[k]);
                        }
                    }
                    else {
                        for(int k = 0; k < content.size(); k++) {
                            tVs[k] = new TextView(context);
                            tVs[k].setLayoutParams(new LinearLayout.LayoutParams(
                                    cellLayoutWidth,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            ));
                            tVs[k].setTextSize(headerTextSize);
                            tVs[k].setText(content.get(k));
                        }
                    }
                    addViews(tableRows[j], tVs);

                }
                return tableRows;
        }

        return null;
    }

    public LinearLayout[] getInstance(String title, String[] rowHeaders, String[] columnHeaders, ArrayList<String> content, int layoutId) {

        // TODO : special cases
        if(layoutId != 0) {
            return getSpecialInstance(title, rowHeaders, columnHeaders, content, layoutId);
        }

        int rowCount = rowHeaders.length+1; // +1 for title
        int columnCount = columnHeaders.length;

        Log.w("Content size:", ""+content.size());

        LinearLayout[] tableRows = new LinearLayout[rowCount];

        for(int l = 0; l < rowCount; l++) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            linearLayout.setGravity(Gravity.CENTER);
            tableRows[l] = linearLayout;
        }

        int cellLayoutWidth = calculateCellLayoutWith(screenWidth, columnCount);

        int contentCounter = 0;
        for(int j = 0; j < tableRows.length; j++) {

            // Manage title
            if(j == 0) {
                TextView tV = new TextView(context);
                tV.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                tV.setPadding(titleLayoutPadding, titleLayoutPadding, titleLayoutPadding, titleLayoutPadding);
                tV.setTextSize(titleTextSize);
                tV.setText(title);
                tableRows[j].addView(tV);
            }
            // Manage column headers
            else if(j == 1) {
                TextView[] tVs = createTextViews(columnCount);
                for(int i = 0; i < columnCount; i++) {
                    // Empty header
                    if(i==0) {
                        tVs[i].setLayoutParams(new LinearLayout.LayoutParams(
                                headerLayoutWidth,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));

                    }
                    //columnHeaders
                    else {
                        tVs[i].setLayoutParams(new LinearLayout.LayoutParams(
                                cellLayoutWidth,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        //tVs[i].setPadding(headerLayoutPadding, headerLayoutPadding, headerLayoutPadding, headerLayoutPadding);
                        tVs[i].setTextSize(headerTextSize);
                        tVs[i].setText(columnHeaders[i]); // first header is blank so no need for offset
                        tVs[i].setBackgroundColor(context.getResources().getColor(R.color.green));
                    }
                }
                addViews(tableRows[j], tVs);
            }
            // Manage row headers & content
            else {
                TextView[] tVs = createTextViews(columnCount);
                for(int i = 0; i < columnCount; i++) {
                    // Manage row header
                    if(i==0) {
                        tVs[i].setLayoutParams(new LinearLayout.LayoutParams(
                                headerLayoutWidth,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        //tVs[i].setPadding(headerLayoutPadding, headerLayoutPadding, headerLayoutPadding, headerLayoutPadding);
                        tVs[i].setTextSize(headerTextSize);
                        tVs[i].setText(rowHeaders[j - 1]); // offset
                        tVs[i].setBackgroundColor(context.getResources().getColor(R.color.blue));
                    }
                    // Manage content
                    else {
                        tVs[i].setLayoutParams(new LinearLayout.LayoutParams(
                                cellLayoutWidth,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        //tVs[i].setPadding(headerLayoutPadding, headerLayoutPadding, headerLayoutPadding, headerLayoutPadding);
                        tVs[i].setTextSize(cellTextSize);
                        tVs[i].setText(content.get(contentCounter++));
                    }
                    //addViews(tableRows[j], tVs);
                }
                addViews(tableRows[j], tVs);

            }

        }

        //addViews(view ,tableRows);
        return tableRows;
    }

    private void addViews(LinearLayout view, LinearLayout[] linearLayouts) {
        for(LinearLayout layout : linearLayouts) {
            layout.setGravity(Gravity.CENTER);
            view.addView(layout);
        }
    }

    private void addViews(LinearLayout linearLayout, TextView[] textViews) {
        for(TextView view : textViews) {
            view.setGravity(Gravity.CENTER);
            linearLayout.addView(view);
        }
    }

    private TextView[] createTextViews(int columnCount) {
        TextView[] tVs = new TextView[columnCount];
        for(int i=0; i < columnCount; i++) {
            tVs[i] = new TextView(context);
        }
        return tVs;
    }

    private int getDp(int i) {
        return DisplayUtilities.integerToDp(context, i);
    }

    private int calculateCellLayoutWith(int screenWidth, int columnCount) {
        return (screenWidth - (2*tableMargins) - headerLayoutWidth )/ (columnCount == 1 ? 1 : columnCount-1);
    }
}
