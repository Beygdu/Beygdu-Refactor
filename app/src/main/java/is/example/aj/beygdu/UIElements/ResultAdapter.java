package is.example.aj.beygdu.UIElements;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import is.example.aj.beygdu.Parser.Table;
import is.example.aj.beygdu.R;
import is.example.aj.beygdu.Utils.DisplayUtilities;

/**
 * Created by arnar on 2/29/2016.
 * @deprecated
 */
public class ResultAdapter extends ArrayAdapter<ResultObject> {

    private LayoutInflater inflater;

    private Typeface LatoBold;
    private Typeface LatoSemiBold;
    private Typeface LatoLight;

    private float width;
    private float height;

    private ArrayList<ResultObject> objs;

    public ResultAdapter(Context context, int resource, List<ResultObject> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
        objs = (ArrayList<ResultObject>) objects;
    }

    @Override
    public int getItemViewType(int position) {
        return objs.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WindowManager wManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wManager.getDefaultDisplay();

        width = DisplayUtilities.getScreenWidth(display);
        height = DisplayUtilities.getScreenHeigth(display);

        // Typefaces
        LatoBold = Typeface.createFromAsset(getContext().getAssets(), "Lato-Bold.ttf");
        LatoSemiBold = Typeface.createFromAsset(getContext().getAssets(), "Lato-Semibold.ttf");
        LatoLight = Typeface.createFromAsset(getContext().getAssets(), "Lato-Light.ttf");

        ResultObject object = getItem(position);
        if(getItemViewType(position) == ResultTitle.item_Type) return getTitleView(position, convertView, parent, object);
        return getTableView(position, convertView, parent, object);
    }

    private View getTitleView(int position, View convertView, ViewGroup parent, ResultObject item) {
        ViewContainer container;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.result_title, null);

            container = new ViewContainer(item.getType());
            container.textView = (TextView) convertView.findViewById(R.id.result_textview);

            convertView.setTag(container);
        }
        else {
            container = (ViewContainer) convertView.getTag();
        }

        ResultTitle title = (ResultTitle) item;

        container.textView.setText(title.getTitle());
        // TODO : manage LayoutParams

        return convertView;
    }

    private View getTableView(int position, View convertView, ViewGroup parent, ResultObject item) {
        ViewContainer container;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.result_table, null);

            container = new ViewContainer(item.getType());
            container.linearLayout = (LinearLayout) convertView.findViewById(R.id.result_tablelayout);

            convertView.setTag(container);
        }
        else {
            container = (ViewContainer) convertView.getTag();
        }


        ResultTable table = (ResultTable) item;

        createTableLayouts(container.linearLayout, table);

        return convertView;

    }

    private static class ResultObjectContainer {
        private View view;
    }

    static class ViewContainer {

        int type;
        TextView textView;
        LinearLayout linearLayout;

        ViewContainer(int type) {
            this.type = type;
        }

    }

    private static class ResultTitleContainer extends  ResultObjectContainer {
        private View view;
    }

    private static class ResultTableContainer extends ResultObjectContainer {
        private View view;
    }

    private LinearLayout createTableLayouts(LinearLayout view, ResultTable item) {

        int rowCount = item.getRowNames().length;
        int columnCount = item.getColumnNames().length;

        Log.w("createTableLayouts", "Rows are :" + rowCount + " Cols are :" + columnCount);
        String[] rowHeaders = item.getRowNames();
        String[] columnHeaders = item.getColumnNames();

        ArrayList<String> content = item.getContent();

        LinearLayout[] tableRows = new LinearLayout[rowCount];

        for(int q = 0; q < tableRows.length; q++) {
            tableRows[q] = new LinearLayout(getContext());
        }

        int rowCounter = 1;
        int contentCounter = 0;
        switch (item.getLayoutId()) {
            // case default
            case 0:
                /*
                for(int i=0; i < tableRows.length; i++) {

                    if(i==0) {
                        tableRows[i].setOrientation(LinearLayout.HORIZONTAL);
                        TextView tV = new TextView(getContext());
                        tV.setText(item.getTitle());
                        tableRows[i].addView(tV);
                    }
                    else if(i==1) {
                        tableRows[i].setOrientation(LinearLayout.HORIZONTAL);
                        TextView[] tVs = createTextViews(columnCount);
                        for(int j = 0; j < tVs.length; j++) {
                            tVs[j].setText(columnHeaders[j]);
                            tableRows[i].addView(tVs[j]);
                        }
                    }
                    else {
                        tableRows[i].setOrientation(LinearLayout.HORIZONTAL);
                        TextView[] tVs = createTextViews(columnCount);
                        for(int j = 0; j < tVs.length; j++) {

                            if(j==0) {
                                tVs[j].setText(rowHeaders[i-1]); //Offset
                                tableRows[i].addView(tVs[j]);
                            }
                            else {
                                tVs[j].setText(content.get(contentCounter++));
                                tableRows[i].addView(tVs[j]);
                            }
//                            tableRows[i].addView(tVs[j]);

                        }

                    }
                    layout.addView(tableRows[i]);

                }
                */
                CrapTable cp = new CrapTable(getContext());
                LinearLayout[] views = cp.getInstance(item.getTitle(), item.getRowNames(), item.getColumnNames(), item.getContent(), 0);

                for(LinearLayout layout : views) {
                    view.addView(layout);
                }

                return view;

                //return layout;
            // case special
            case 1:
                return view;
            // case special
            case 2:
                return view;
            // case special
            case 3:
                return view;
            default:
               return view;
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

    private TextView[] createTextViews(int cellCount) {
        TextView[] textViews = new TextView[cellCount];
        for(int i=0; i < cellCount; i++) {
            textViews[i] = new TextView(getContext());
        }
        return textViews;
    }
}
