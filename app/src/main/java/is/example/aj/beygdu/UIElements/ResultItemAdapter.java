package is.example.aj.beygdu.UIElements;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
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

    private int titleSize;
    private int warningTextSize;
    private int blockTitleSize;
    private int subBlockTitleSize;
    private int footerTextSize;

    private int dividerHeigth;
    private int warningPadding;
    private int blockTitlePadding;
    private int subBlockTitlePadding;
    private int tablePadding;

    private Context context;
    private ArrayList<ResultObject> objects;
    private int[] itemTypes;
    private int[] hiddenViews;
    private LayoutInflater inflater;

    public ResultItemAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        titleSize = getDp(12);
        warningTextSize = getDp(5);
        blockTitleSize = getDp(10);
        subBlockTitleSize = getDp(9);
        footerTextSize = getDp(5);

        dividerHeigth = getDp(15);

        warningPadding = getDp(10);
        blockTitlePadding = getDp(5);
        subBlockTitlePadding = getDp(5);
        tablePadding = getDp(5);
    }

    /**
     * Adds ResultObjects to the adapter
     * @param objects ArrayList<ResultObject> objects
     */
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
                    container.textView = (TextView) convertView.findViewById(R.id.result_textview);
                    convertView.setTag(container);
                    break;
                case TYPE_TABLE:
                    convertView = inflater.inflate(R.layout.result_table, null);
                    container.linearLayout = (LinearLayout) convertView.findViewById(R.id.result_tablelayout);
                    convertView.setTag(container);
                    break;
            }
        }
        else {
            container = (ViewContainer) convertView.getTag();
        }

        switch (type) {
            case TYPE_TITLE:
                if(objects.get(position).getLayoutId() == 3) {
                    container.textView.setText(Html.fromHtml(objects.get(position).getTitle()));
                    container.textView = manageTextViewParams(container.textView, getItem(position).getLayoutId());
                    return convertView;
                }
                else {
                    container.textView.setText(objects.get(position).getTitle());
                    container.textView = manageTextViewParams(container.textView, getItem(position).getLayoutId());
                    return convertView;
                }
            case TYPE_TABLE:
                createTableLayouts(container.linearLayout, (ResultTable) objects.get(position));
                return convertView;
            default:
                return null;
        }

    }

    private void createTableLayouts(LinearLayout view, ResultTable item) {

        // Removes current views from the recycled layout, if it has any
        if(view.getChildCount() > 0) {
            view.removeAllViews();
        }


        int rowCount = item.getRowNames().length;

        LinearLayout[] tableRows = new LinearLayout[rowCount];

        for(int q = 0; q < tableRows.length; q++) {
            tableRows[q] = new LinearLayout(context);
        }

        CrapTable cp = new CrapTable(context);
        LinearLayout[] views = cp.getInstance(item.getTitle(), item.getRowNames(), item.getColumnNames(), item.getContent(), item.getLayoutId());


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(tablePadding, 0, tablePadding, 0);

        LinearLayout.LayoutParams bottomParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        bottomParams.setMargins(tablePadding, 0, tablePadding, dividerHeigth);

        for(int i = 0; i < views.length; i++) {
            if(i != views.length-1) {
                view.addView(views[i], params);
            }
            else {
                view.addView(views[i], bottomParams);
            }
        }

    }

    private TextView manageTextViewParams(TextView textView, int layoutId) {


        switch (layoutId) {
            // Page title
            case 0:
                textView.setTypeface(FontManager.getFont(context, FontManager.LATO_BOLD));
                textView.setTextSize(titleSize);
                textView.setTextColor(context.getResources().getColor(R.color.white));
                textView.setGravity(Gravity.CENTER);

                RelativeLayout.LayoutParams pageTitleParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                pageTitleParams.setMargins(0, 0, 0, dividerHeigth);
                textView.setLayoutParams(pageTitleParams);
                textView.setBackgroundResource(R.color.colorPrimary);
                break;
            // Block title
            case 1:
                textView.setTypeface(FontManager.getFont(context, FontManager.LATO_SEMIBOLD));
                textView.setTextSize(blockTitleSize);
                textView.setTextColor(context.getResources().getColor(R.color.dark_gray));
                textView.setGravity(Gravity.CENTER);
                RelativeLayout.LayoutParams blockParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                blockParams.setMargins(0, 0, 0, dividerHeigth);
                textView.setLayoutParams(blockParams);
                textView.setBackgroundResource(R.color.lightblue);
                break;
            // SubBlock title
            case 2:
                textView.setTypeface(FontManager.getFont(context, FontManager.LATO_LIGHT));
                textView.setTextSize(subBlockTitleSize);
                textView.setTextColor(context.getResources().getColor(R.color.dark_gray));
                textView.setGravity(Gravity.LEFT);
                RelativeLayout.LayoutParams subBlockParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                subBlockParams.setMargins(subBlockTitlePadding, 0, subBlockTitlePadding, dividerHeigth);
                textView.setLayoutParams(subBlockParams);
                textView.setBackgroundResource(R.color.lightblue);
                break;
            // case note
            case 3:
                // TODO : implement
                textView.setTextSize(warningTextSize);
                textView.setTextColor(context.getResources().getColor(R.color.black));
                RelativeLayout.LayoutParams warningParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                warningParams.setMargins(warningPadding, 0, warningPadding, dividerHeigth);
                textView.setLayoutParams(warningParams);
                textView.setBackgroundResource(R.color.light_gray);
                break;
            case 4:
                textView.setTextSize(footerTextSize);
                textView.setTextColor(context.getResources().getColor(R.color.white));
                RelativeLayout.LayoutParams footerParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                footerParams.setMargins(0, getDp(3), 0, 0);
                footerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                textView.setLayoutParams(footerParams);
                textView.setBackgroundResource(R.color.colorPrimary);
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

    /**
     * @param i pixels
     * @return i in dp
     */
    private int getDp(int i) {
        return DisplayUtilities.integerToDp(context, i);
    }

    static class ViewContainer {
        TextView textView;
        LinearLayout linearLayout;
    }
}
