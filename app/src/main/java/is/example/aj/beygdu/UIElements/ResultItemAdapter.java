package is.example.aj.beygdu.UIElements;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

import is.example.aj.beygdu.R;
import is.example.aj.beygdu.Utils.DisplayUtilities;
import is.example.aj.beygdu.Utils.FontManager;

/**
 * Created by arnar on 3/3/2016.
 */
public class ResultItemAdapter extends BaseAdapter {

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_TABLE = 1;
    private static final int TYPE_COUNT = 2;

    private Context context;
    private ArrayList<ResultObject> objects;
    private int[] itemTypes;
    private int[] hiddenViews;
    private LayoutInflater inflater;

    public ResultItemAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItems(ArrayList<ResultObject> objects) {
        this.objects = objects;
        itemTypes = new int[this.objects.size()];
        for(int i = 0; i < this.objects.size(); i++) {
            if(this.objects.get(i).getType() == ResultTitle.item_Type) itemTypes[i] = TYPE_TITLE;
            else itemTypes[i] = TYPE_TABLE;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return itemTypes[position];
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public ResultObject getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewContainer container = null;
        int type = getItemViewType(position);
        if(convertView == null) {
            container = new ViewContainer();

            switch (type) {
                case TYPE_TITLE:
                    convertView = inflater.inflate(R.layout.result_title, null);
                    //convertView.setLayoutParams(getLinearLayoutParams());
                    container.textView = (TextView) convertView.findViewById(R.id.result_textview);
                    //container.textView = manageTextViewParams(container.textView, getItem(position).getLayoutId());
                    convertView.setTag(container);
                    break;
                case TYPE_TABLE:
                    convertView = inflater.inflate(R.layout.result_table, null);
                    container.linearLayout = (LinearLayout) convertView.findViewById(R.id.result_tablelayout);
                    convertView.setTag(container);
                    break;
            }
            //convertView.setTag(container);
        }
        else {
            container = (ViewContainer) convertView.getTag();
        }

        switch (type) {
            case TYPE_TITLE:
                container.textView.setText(objects.get(position).getTitle());
                return convertView;
            case TYPE_TABLE:
                createTableLayouts(container.linearLayout, (ResultTable) objects.get(position));
                //notifyDataSetChanged();
                return convertView;
            default:
                return null;
        }

    }

    private void createTableLayouts(LinearLayout view, ResultTable item) {

        if(view.getChildCount() > 0) {
            view.removeAllViews();
        }


        int rowCount = item.getRowNames().length;
        int columnCount = item.getColumnNames().length;

        Log.w("createTableLayouts", "Rows are :" + rowCount + " Cols are :" + columnCount);
        String[] rowHeaders = item.getRowNames();
        String[] columnHeaders = item.getColumnNames();

        ArrayList<String> content = item.getContent();

        LinearLayout[] tableRows = new LinearLayout[rowCount];

        for(int q = 0; q < tableRows.length; q++) {
            tableRows[q] = new LinearLayout(context);
        }

        int rowCounter = 1;
        int contentCounter = 0;

        CrapTable cp = new CrapTable(context);
        LinearLayout[] views = cp.getInstance(item.getTitle(), item.getRowNames(), item.getColumnNames(), item.getContent(), item.getLayoutId());

        Log.w("Number of layouts : ", ""+views.length);

        for(LinearLayout layout : views) {
            view.addView(layout);
        }


    }

    private TextView manageTextViewParams(TextView textView, int layoutId) {

        textView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));

        switch (layoutId) {
            // Page title
            case 0:
                textView.setTypeface(FontManager.getFont(context, FontManager.LATO_BOLD));
                textView.setTextSize(DisplayUtilities.integerToDp(context, 14));
                break;
            // Block title
            case 1:
                textView.setTypeface(FontManager.getFont(context, FontManager.LATO_SEMIBOLD));
                textView.setTextSize(DisplayUtilities.integerToDp(context, 10));
                break;
            // SubBlock title
            case 2:
                textView.setTypeface(FontManager.getFont(context, FontManager.LATO_SEMIBOLD));
                textView.setTextSize(DisplayUtilities.integerToDp(context, 8));
                break;
            // case note
            case 3:
                // TODO : implement
                break;
            default:
                // Do nothing
        }

        return textView;
    }

    private RelativeLayout.LayoutParams getLinearLayoutParams() {

        RelativeLayout.LayoutParams params;
        int lrMargin = DisplayUtilities.integerToDp(context, 2);
        int tbMargin = DisplayUtilities.integerToDp(context, 4);

        params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(lrMargin, tbMargin, lrMargin, tbMargin);
        return params;

    }


    static class ViewContainer {
        TextView textView;
        LinearLayout linearLayout;
    }
}
