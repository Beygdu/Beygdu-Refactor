package is.example.aj.beygdu.UIElements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import is.example.aj.beygdu.R;

/**
 * Created by arnar on 2/29/2016.
 */
public class ResultAdapter extends ArrayAdapter<ResultObject> {

    private LayoutInflater inflater;

    public ResultAdapter(Context context, int resource, List<ResultObject> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
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
        return null;
    }

    private static class ResultTitleContainer {
        private TextView textView;
    }
}
