package is.example.aj.beygdu.UIElements;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import is.example.aj.beygdu.Parser.Table;
import is.example.aj.beygdu.R;
import is.example.aj.beygdu.Utils.DisplayUtilities;

/**
 * Created by arnar on 2/29/2016.
 */
public class ResultAdapter extends ArrayAdapter<ResultObject> {

    private LayoutInflater inflater;

    private Typeface LatoBold;
    private Typeface LatoSemiBold;
    private Typeface LatoLight;

    private float width;
    private float height;

    public ResultAdapter(Context context, int resource, List<ResultObject> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        WindowManager wManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wManager.getDefaultDisplay();

        width = DisplayUtilities.getScreenWidth(display);
        height = DisplayUtilities.getScreenHeigth(display);

        // Typefaces
        LatoBold = Typeface.createFromAsset(getContext().getAssets(), "Lato-Bold.ttf");
        LatoSemiBold = Typeface.createFromAsset(getContext().getAssets(), "Lato-Semibold.ttf");
        LatoLight = Typeface.createFromAsset(getContext().getAssets(), "Lato-Light.ttf");

        ResultObject item = this.getItem(position);
        if(item.getType() == ResultTitle.item_Type) {
            view = getTitleView(convertView, parent, item);
        }
        else {
            view = getTableView(convertView, parent, item);
        }
        return view;
    }

    private View getTitleView(View view, ViewGroup parent, ResultObject item) {
        ResultTitle title = (ResultTitle) item;
        ResultTitleContainer itemContainer = null;

        if(view == null) {
            view = inflater.inflate(R.layout.result_title, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.result_textview);

            itemContainer = new ResultTitleContainer();
            itemContainer.textView = textView;

            view.setTag(itemContainer);
        }

        if(itemContainer == null) {
            itemContainer = (ResultTitleContainer) view.getTag();
        }

        itemContainer.textView.setText(item.getTitle());


        return view;
    }

    private View getTableView(View view, ViewGroup parent, ResultObject item) {
        ResultTable table = (ResultTable) item;
        ResultTableContainer itemContainer = null;

        if(view == null) {
            view = inflater.inflate(R.layout.result_table, parent, false);
            TableLayout tableLayout = (TableLayout) view.findViewById(R.id.result_tablelayout);

            tableLayout = createTableLayouts(tableLayout, table);

            itemContainer = new ResultTableContainer();
            itemContainer.tableLayout = tableLayout;

            view.setTag(itemContainer);
        }

        if(itemContainer == null) {
            //itemContainer = (ResultTableContainer) view.getTag();
        }

        return view;
    }

    private static class ResultTitleContainer {
        private TextView textView;
    }

    private static class ResultTableContainer {
        private TableLayout tableLayout;
    }

    private TableLayout createTableLayouts(TableLayout layout, ResultTable item) {

        int rowCount = item.getRowNames().length;
        int columnCount = item.getColumnNames().length;

        Log.w("createTableLayouts", "Rows are :" + rowCount + " Cols are :" + columnCount);
        String[] rowHeaders = item.getRowNames();
        String[] columnHeaders = item.getColumnNames();

        ArrayList<String> content = item.getContent();

        TextView title = new TextView(getContext());
        title.setText(item.getTitle());

        TableRow titleRow = new TableRow(getContext());
        titleRow.addView(title);
        layout.addView(titleRow);

        int rowCounter = 1;
        int contentCounter = 0;
        switch (item.getTableType()) {
            // case default
            case 0:
                for(int i = 0; i < rowCount; i++) {

                    TableRow tableRow = new TableRow(getContext());
                    /*
                    if(i == 0) {
                        for(int j = 0; j < columnCount; j++) {
                            TextView textView = new TextView(getContext());
                            textView = manageTitleLayoutParams(textView, 5);
                            textView.setText(columnHeaders[j]);
                            tableRow.addView(textView);
                        }
                        //layout.addView(tableRow);
                    }
                    else {
                        for(int j = 1; j < rowCount; j++) {
                            TextView textView = new TextView(getContext());
                            textView = manageTitleLayoutParams(textView, 5);
                            textView.setText(rowHeaders[j]);
                            tableRow.addView(textView);

                            for(int k = 0; k < columnCount-1; k++) {
                                TextView cellView = new TextView(getContext());
                                cellView = manageTitleLayoutParams(cellView, 5);
                                try {
                                    cellView.setText(content.get(contentCounter++));
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                tableRow.addView(cellView);
                            }
//                            layout.addView(tableRow);
                        }
                        //layout.addView(tableRow);
                    }
                    layout.addView(tableRow);
                    */

                    if(i == 0) {
                        for(int j = 0; j < columnCount; j++) {
                            TextView tV = new TextView(getContext());
                            tV.setText(columnHeaders[j]);
                            tableRow.addView(tV);
                        }
                    }
                    else {
                        TextView tV = new TextView(getContext());
                        tV.setText(rowHeaders[i]);
                        tableRow.addView(tV);
                        for(int j = 1; j < columnCount; j++) {
                            TextView cV = new TextView(getContext());
                            try {
                                cV.setText(content.get(contentCounter++));
                            }
                            catch (Exception e) {
                                cV.setText("Exc.Catch");
                            }
                            tableRow.addView(cV);
                        }
                    }
                    layout.addView(tableRow);
                }
                return layout;
            // case special
            case 1:
                return layout;
            // case special
            case 2:
                return layout;
            // case special
            case 3:
                return layout;
            default:
                return layout;
        }
    }

    private RelativeLayout.LayoutParams createTextViewLP(int controlId) {

        switch (controlId) {
            // Page Header
            case 0:
                RelativeLayout.LayoutParams header =
                        new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                100);
                int hM = DisplayUtilities.integerToDp(getContext(), 20);
                header.setMargins(hM, hM, hM, hM);
                return header;
            // Word Warning
            case 1:
                // TODO : implement
                return null;
            // Block Title
            case 2:
                return null;
            // SubBlock Title
            case 3:
                return null;
            // Table title
            case 4:
                return null;
            default:
                return null;
        }

    }

    private TextView manageTitleLayoutParams(TextView textView, int controlId) {

        switch (controlId) {
            // Header TextView
            case 0:
                textView.setTextSize(35);
                textView.setTypeface(LatoLight);
                textView.setTextColor(
                        getContext().getResources().getColor(R.color.header_title));
                return textView;
            // Warning TextView
            case 1:
                // TODO : implement
                return null;
            // Block TextView
            case 2:
                if (320 > width && width < 384) {
                    textView.setTextSize(22);
                }
                else if(384 > width && width < 600) {
                    textView.setTextSize(28);
                }
                else if(width > 600){
                    textView.setTextSize(42);
                }
                textView.setMinHeight(
                        DisplayUtilities.integerToDp(
                                getContext(), 80));
                textView.setTypeface(LatoLight);
                textView.setTextColor(
                        getContext().getResources().getColor(R.color.block_title));
                int bP = DisplayUtilities.integerToDp(getContext(), 10);
                textView.setPadding(0, bP, 0, bP);
                return textView;
            // SubBlock TextView
            case 3:
                if (320 > width && width < 384) {
                    textView.setTextSize(20);
                }
                else {
                    textView.setTextSize(22);
                }
                textView.setTypeface(LatoLight);
                textView.setTextColor(
                        getContext().getResources().getColor(R.color.subblock_title));
                int bSP = DisplayUtilities.integerToDp(getContext(), 10);
                textView.setPadding(0, bSP, 0, bSP);
                return textView;
            // Table TestView
            case 4:
                if (320 > width && width < 384) {
                    textView.setTextSize(18);
                }
                else {
                    textView.setTextSize(20);
                }
                textView.setTypeface(LatoLight);
                textView.setTextColor(
                        getContext().getResources().getColor(R.color.table_title));
                int tP = DisplayUtilities.integerToDp(getContext(), 10);
                textView.setPadding(0, tP, 0, tP);
                textView.setBackgroundResource(
                        R.drawable.top_border_orange);
                return textView;
            // Cell TextView
            case 5:
                textView.setGravity(Gravity.LEFT);
                textView.setTextSize(16);
                textView.setTypeface(LatoLight);
                textView.setTextColor(
                        getContext().getResources().getColor(R.color.table_cell));
                int tabP = DisplayUtilities.integerToDp(getContext(), 10);
                textView.setPadding(tabP/2, tabP, tabP, tabP);
                // TODO : implement clicklistener
                return textView;
            default:
                return textView;
        }
    }
}
