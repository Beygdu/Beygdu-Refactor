package is.example.aj.beygdu.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import is.example.aj.beygdu.Parser.Block;
import is.example.aj.beygdu.R;

/**
 * Created by arnar on 2/13/2016.
 */
public class ResultAdapter extends ArrayAdapter<Block> {

    //Views
    private TextView blockTite;
    private TextView blockWarning;

    private LayoutInflater inflater;


    public ResultAdapter(Context context, int resource, List<Block> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Block item = this.getItem(position);

        return getItemView(convertView, parent, item);
    }

    private View getItemView(View view, ViewGroup parent, Block item) {

        if(view == null) {
            view = inflater.inflate(R.layout.item_block, parent, false);
        }

        return view;
    }



}
